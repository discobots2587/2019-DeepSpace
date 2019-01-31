/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.RobotMap;
import frc.robot.lib.Gamepad;
import frc.robot.lib.RampedMotor;
import frc.robot.lib.RampingController;
import frc.robot.util.Constants;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ArcadeDrive;

/**
 * Contains objects and capabilities related to the drivetrain
 */
public class DriveTrain extends Subsystem {
  private Spark m_left;
  private Spark m_right;
  private RampedMotor m_leftDrive;
  private RampedMotor m_rightDrive;
  private DifferentialDrive m_drive;

  private Boolean rampingUsed;

  private double[] lastInputs;

  private RampingController m_ramping;

  public DriveTrain() {
    this.m_left = new Spark(RobotMap.m_leftDrive);
    this.m_left.setInverted(true); // since motors are wired backwards
    this.m_right = new Spark(RobotMap.m_rightDrive);
    this.m_right.setInverted(true); // since motors are wired backwards

    this.m_drive = new DifferentialDrive(m_left, m_right);
    this.m_drive.setExpiration(Constants.kDriveTimeout);
    this.m_drive.setSafetyEnabled(Constants.kSafetyEnabled);

    lastInputs = new double[2];

    m_ramping = new RampingController(new double[] {0.5, 0.75}, x -> 0.5*x, x -> x * x, Math::sqrt);
    this.rampingUsed = true;
  }

  @Override
  public void initDefaultCommand() {
    /* TODO: Make this extensible to use any kind of drive (e.g., arcade/curvature) */
    setDefaultCommand(new TankDrive());
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

    this.m_drive.arcadeDrive(rampedInput[0], rampedInput[1]);

    lastInputs[0] = xSpeed;
    lastInputs[1] = zRotation;
  }

  public void arcadeDrive(double xSpeed, double zRotation) { //contrary to the documentation, but that is ok
    double deadzoneSpeed = applyDeadzone(xSpeed);
    double deadzoneTurn = applyDeadzone(zRotation);

    m_drive.arcadeDrive(deadzoneSpeed, deadzoneTurn, true); //forward, clockwise = positive; decrease sensitivity at low speed is TRUE
  }

  public void rampedTankDrive(double leftSide, double rightSide) {
    double deadzoneLY = applyDeadzone(leftSide);
    double deadzoneRY = applyDeadzone(rightSide);
    double[] rampedInput = applyLowPassRamping(new double[]{deadzoneLY, deadzoneRY});

    // double rampedLY = adjustForSquareRamping(deadzoneLY);
    // double rampedRX = adjustForSquareRamping(deadzoneRY);

    this.m_drive.tankDrive(rampedInput[0], rampedInput[1]);
  }
  public void tankDrive(double left, double right) {
    double deadzoneLY = applyDeadzone(left);
    double deadzoneRY = applyDeadzone(right);

    this.m_drive.tankDrive(deadzoneLY, deadzoneRY, true); //forward = positive; decrease sensitivity at low speed is TRUE
  }

  public void setRampingUsed(Boolean used) {
    this.rampingUsed = used;
  }

  public Boolean getRampingUsed() {
    return this.rampingUsed;
  }

  public void stop() {
    this.m_drive.stopMotor();
  }
}
