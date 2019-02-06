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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;
import frc.robot.lib.RampedMotor;
import frc.robot.util.Constants;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ArcadeDrive;

/**
 * Contains objects and capabilities related to the drivetrain
 */
public class DriveTrain extends Subsystem {
  private TalonSRX m_frontLeft;
  private TalonSRX m_frontRight;
  private TalonSRX m_backLeft;
  private TalonSRX m_backRight;

  private double[] lastInputs;

  public DriveTrain() {
    this.m_frontLeft = new TalonSRX(RobotMap.m_motorFrontLeft);
    this.m_frontRight = new TalonSRX(RobotMap.m_motorFrontRight);
    this.m_backLeft = new TalonSRX(RobotMap.m_motorBackLeft);
    this.m_backRight = new TalonSRX(RobotMap.m_motorBackRight);

    /* Invert motors since they are wired backwards */
    this.m_frontLeft.setInverted(true);
    this.m_frontRight.setInverted(true);
    this.m_backLeft.setInverted(true);
    this.m_backRight.setInverted(true);

    /* Configure master-slave for left and right motors */
    this.m_backLeft.follow(this.m_frontLeft);
    this.m_backRight.follow(this.m_frontRight);
    this.m_backLeft.setNeutralMode(NeutralMode.Brake);
    this.m_backRight.setNeutralMode(NeutralMode.Brake);

    /* Setup control */
    this.m_frontLeft.configOpenloopRamp(0.4, 10);
    this.m_frontRight.configOpenloopRamp(0.4, 10);
    this.m_frontLeft.setSensorPhase(true);
    this.m_frontRight.setSensorPhase(true);

    /* Configure PID */
    //this.m_frontLeft.config_kP(0, 1.0, 10);
    //this.m_frontLeft.config_kI(0, 1.0, 10);
    //this.m_frontLeft.config_kD(0, 1.0, 10);
    //this.m_frontLeft.config_kF(0, 1.0, 10);
    //this.m_frontRight.config_kP(0, 1.0, 10);
    //this.m_frontRight.config_kI(0, 1.0, 10);
    //this.m_frontRight.config_kD(0, 1.0, 10);
    //this.m_frontRight.config_kF(0, 1.0, 10);

    /* Configure motion profiling */
    //this.m_frontLeft.selectProfileSlot(0, 0);
    //this.m_frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
    //this.m_frontLeft.configMotionProfileTrajectoryPeriod(0, 10);
    //this.m_frontRight.selectProfileSlot(0, 0);
    //this.m_frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
    //this.m_frontRight.configMotionProfileTrajectoryPeriod(0, 10);

    /* Configure current limiting to prevent motor burnout */
    this.m_frontLeft.configContinuousCurrentLimit(40, 0);
    this.m_frontLeft.configPeakCurrentLimit(60, 0);
    this.m_frontLeft.configPeakCurrentDuration(100, 0);
    this.m_frontLeft.enableCurrentLimit(true);
    this.m_frontRight.configContinuousCurrentLimit(40, 0);
    this.m_frontRight.configPeakCurrentLimit(60, 0);
    this.m_frontRight.configPeakCurrentDuration(100, 0);
    this.m_frontRight.enableCurrentLimit(true);

    lastInputs = new double[2];
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
    
    double deadzoneLY = applyDeadzone(xSpeed);
    double deadzoneRX = applyDeadzone(zRotation);
    double[] rampedInput = applyLowPassRamping(new double[]{deadzoneLY, deadzoneRX});

    // double rampedLY = adjustForSquareRamping(deadbandLY);
    // double rampedRX = adjustForSquareRamping(deadbandRX);

    this.arcadeDrive(rampedInput[0], rampedInput[1]);

    lastInputs[0] = xSpeed;
    lastInputs[1] = zRotation;
  }

  public void arcadeDrive(double throttle, double turn) { //contrary to the documentation, but that is ok
    double leftMotorSpeed;
    double rightMotorSpeed;

    if (throttle > 0.0) {
      if (turn > 0.0) {
        leftMotorSpeed = throttle - turn;
        rightMotorSpeed = Math.max(throttle, turn);
      } else {
        leftMotorSpeed = Math.max(throttle, -turn);
        rightMotorSpeed = throttle + turn;
      }
    } else {
      if (turn > 0.0) {
        leftMotorSpeed = -Math.max(-throttle, turn);
        rightMotorSpeed = throttle + turn;
      } else {
        leftMotorSpeed = throttle - turn;
        rightMotorSpeed = -Math.max(-throttle, -turn);
      }
    }

    this.tankDrive(-leftMotorSpeed, -rightMotorSpeed);
	}

  public void rampedTankDrive(double leftSide, double rightSide) {
    /* TODO: Investigate why gamepad.getRX() and gamepad.getRY() don't work */
    /* NOTE: gamepad.getRawAxis(4) = gamepad.getRX() expected function */
    double deadbandLY = applyDeadzone(leftSide);
    double deadbandRY = applyDeadzone(rightSide);
    double[] rampedInput = applyLowPassRamping(new double[]{deadbandLY, deadbandRY});

    // double rampedLY = adjustForSquareRamping(deadbandLY);
    // double rampedRX = adjustForSquareRamping(deadbandRY);

    this.tankDrive(rampedInput[0], rampedInput[1]);
  }
  public void tankDrive(double left, double right) {
    this.m_frontLeft.set(ControlMode.PercentOutput, left);
    this.m_frontRight.set(ControlMode.PercentOutput, right);
  }

  public void stop() {
    this.m_frontLeft.set(ControlMode.PercentOutput, 0);
    this.m_frontRight.set(ControlMode.PercentOutput, 0);
  }
}
