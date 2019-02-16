/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.RobotMap;
import frc.robot.lib.RampedMotor;
import frc.robot.lib.RampingController;
import frc.robot.util.Constants;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ArcadeDrive;

/**
 * Contains objects and capabilities related to the drivetrain
 */
public class DriveTrain extends Subsystem {
  private TalonSRX m_leftMaster;
  private TalonSRX m_rightMaster;
  private VictorSPX m_leftSlave;
  private VictorSPX m_rightSlave;

  private boolean rampingUsed;
  private boolean isReversed;

  private double[] lastInputs;

  private RampingController m_ramping;

  private Solenoid m_shifter;

  public DriveTrain() {
    this.m_leftMaster = new TalonSRX(RobotMap.m_leftMasterMotor);
    this.m_rightMaster = new TalonSRX(RobotMap.m_rightMasterMotor);
    this.m_leftSlave = new VictorSPX(RobotMap.m_leftSlaveMotor);
    this.m_rightSlave = new VictorSPX(RobotMap.m_rightSlaveMotor);

    /* Invert only Left side so Hatch side is front */
    this.m_leftMaster.setInverted(false);
    this.m_leftSlave.setInverted(false);
    this.m_rightMaster.setInverted(true);
    this.m_rightSlave.setInverted(true);

    /* Configure master-slave for left and right motors */
    this.m_leftSlave.follow(this.m_leftMaster);
    this.m_leftSlave.setNeutralMode(NeutralMode.Coast);
    this.m_rightSlave.follow(this.m_rightMaster);
    this.m_rightSlave.setNeutralMode(NeutralMode.Coast);

    /* Setup control */
    this.m_leftMaster.configOpenloopRamp(0.4, 10);
    this.m_leftMaster.setSensorPhase(true);
    this.m_rightMaster.configOpenloopRamp(0.4, 10);
    this.m_rightMaster.setSensorPhase(true);

    /* Configure PID */
    //this.m_leftMaster.config_kP(0, 1.0, 10);
    //this.m_leftMaster.config_kI(0, 1.0, 10);
    //this.m_leftMaster.config_kD(0, 1.0, 10);
    //this.m_leftMaster.config_kF(0, 1.0, 10);
    //this.m_rightMaster.config_kP(0, 1.0, 10);
    //this.m_rightMaster.config_kI(0, 1.0, 10);
    //this.m_rightMaster.config_kD(0, 1.0, 10);
    //this.m_rightMaster.config_kF(0, 1.0, 10);

    /* Configure motion profiling */
    //this.m_leftMaster.selectProfileSlot(0, 0);
    //this.m_leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
    //this.m_leftMaster.configMotionProfileTrajectoryPeriod(0, 10);
    //this.m_rightMaster.selectProfileSlot(0, 0);
    //this.m_rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
    //this.m_rightMaster.configMotionProfileTrajectoryPeriod(0, 10);

    /* Configure current limiting to prevent motor burnout */
    this.m_leftMaster.configContinuousCurrentLimit(40, 0);
    this.m_leftMaster.configPeakCurrentLimit(60, 0);
    this.m_leftMaster.configPeakCurrentDuration(100, 0);
    this.m_leftMaster.enableCurrentLimit(true);
    this.m_rightMaster.configContinuousCurrentLimit(40, 0);
    this.m_rightMaster.configPeakCurrentLimit(60, 0);
    this.m_rightMaster.configPeakCurrentDuration(100, 0);
    this.m_rightMaster.enableCurrentLimit(true);

    lastInputs = new double[2];

    m_ramping = new RampingController(new double[] {0.5, 0.75}, x -> 0.5*x, x -> x * x, Math::sqrt);
    this.rampingUsed = true;
    this.isReversed = false; //defualt to the hatch side

    /* Initiliaze solenoid in on position */
    this.m_shifter = new Solenoid(RobotMap.m_shifter);
    setLowGear();
  }

  @Override
  public void initDefaultCommand() {
    /* TODO: Make this extensible to use any kind of drive (e.g., arcade/curvature) */
    setDefaultCommand(new ArcadeDrive());
  }

  public void teleopInit() {
  }

  public double applyDeadzone (double input){
    double adjustedInput;

    if (Math.abs(input) <= Constants.kDeadband)
      adjustedInput = 0;
    else
      adjustedInput = input;

    return adjustedInput;
  }

  public double applySquaredRamping (double input){
    double adjustedInput;

    adjustedInput = input * input;

    return adjustedInput;
  }

  public double[] applyLowPassRamping (double[] inputs){
    /* TODO: Adjust Constants.kDriveLowPassFilter to make drive accel/decel at desired rate*/
    double[] adjustedInputs = new double[2];

    for (int i = 0; i < 2; i++){
      adjustedInputs[i] = (inputs[i] * (1-Constants.kDriveLowPassFilter)) + (Constants.kDriveLowPassFilter * lastInputs[i]);
    }
    
    return adjustedInputs;
  }


  public void rampedArcadeDrive(double xSpeed, double zRotation) {
    
    double deadzoneSpeed = applyDeadzone(xSpeed);
    double deadzoneTurn = applyDeadzone(zRotation);
    double[] rampedInput = applyLowPassRamping(new double[]{deadzoneSpeed, deadzoneTurn});

    // double rampedLY = adjustForSquareRamping(deadzoneSpeed);
    // double rampedRX = adjustForSquareRamping(deadzoneTurn);

    this.arcadeDrive(rampedInput[0], rampedInput[1]);

    lastInputs[0] = xSpeed;
    lastInputs[1] = zRotation;
  }

  public void arcadeDrive(double rawThrottle, double rawTurn) { //contrary to the documentation, but that is ok
    double leftMotorSpeed;
    double rightMotorSpeed;
    double deadzonedThrottle;
    double deadzonedTurn;

    deadzonedThrottle = applyDeadzone(rawThrottle);
    deadzonedTurn = applyDeadzone(rawTurn);

    if (this.isReversed){
      deadzonedThrottle = -deadzonedThrottle;
    }

    if (deadzonedThrottle > 0.0) {
      if (deadzonedTurn > 0.0) {
        rightMotorSpeed = deadzonedThrottle - deadzonedTurn;
        leftMotorSpeed = Math.max(deadzonedThrottle, deadzonedTurn);
      } else {
        rightMotorSpeed = Math.max(deadzonedThrottle, -deadzonedTurn);
        leftMotorSpeed = deadzonedThrottle + deadzonedTurn;
      }
    } else {
      if (deadzonedTurn > 0.0) {
        leftMotorSpeed = -Math.max(-deadzonedThrottle, deadzonedTurn);
        rightMotorSpeed = deadzonedThrottle + deadzonedTurn;
      } else {
        leftMotorSpeed = deadzonedThrottle - deadzonedTurn;
        rightMotorSpeed = -Math.max(-deadzonedThrottle, -deadzonedTurn);
      }
    }

    this.m_leftMaster.set(ControlMode.PercentOutput, leftMotorSpeed);
    this.m_rightMaster.set(ControlMode.PercentOutput, rightMotorSpeed);
  }

  public void toggleDriveDirection(){
    if (isReversed){
      this.isReversed = false;
    } else{
      this.isReversed = true;
    }
  }

  public void rampedTankDrive(double leftSide, double rightSide) {
    double deadzoneLY = applyDeadzone(leftSide);
    double deadzoneRY = applyDeadzone(rightSide);
    double[] rampedInput = applyLowPassRamping(new double[]{deadzoneLY, deadzoneRY});

    // double rampedLY = adjustForSquareRamping(deadzoneLY);
    // double rampedRX = adjustForSquareRamping(deadzoneRY);

    this.tankDrive(rampedInput[0], rampedInput[1]);
  }
  public void tankDrive(double left, double right) {
    double deadzoneLY = applyDeadzone(left);
    double deadzoneRY = applyDeadzone(right);

    this.m_leftMaster.set(ControlMode.PercentOutput, deadzoneLY);
    this.m_rightMaster.set(ControlMode.PercentOutput, deadzoneRY);
  }
 
  public Solenoid getShifter() {
    return m_shifter;
  }

  public void setHighGear() {
    m_shifter.set(true);
  }

  public void setLowGear() { 
    m_shifter.set(false);
  }

  public void setRampingUsed(Boolean used) {
    this.rampingUsed = used;
  }

  public Boolean getRampingUsed() {
    return this.rampingUsed;
  }

  public void stop() {
    this.m_leftMaster.set(ControlMode.PercentOutput, 0);
    this.m_rightMaster.set(ControlMode.PercentOutput, 0);
  }

  public boolean isFrontToHatch(){
    return !isReversed; //when isReversed is true, the front is the cargo side
  }
}
