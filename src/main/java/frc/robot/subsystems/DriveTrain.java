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

  public DriveTrain() {
    this.m_left = new Spark(RobotMap.m_leftDrive);
    this.m_leftDrive = new RampedMotor(m_left, Constants.kRampband);
    this.m_leftDrive.setInverted(true); // since motors are wired backwards
    this.m_right = new Spark(RobotMap.m_rightDrive);
    this.m_rightDrive = new RampedMotor(m_right,Constants.kRampband);
    this.m_rightDrive.setInverted(true); // since motors are wired backwards

    this.m_drive = new DifferentialDrive(m_leftDrive, m_rightDrive);
    this.m_drive.setExpiration(Constants.kDriveTimeout);
    this.m_drive.setSafetyEnabled(Constants.kSafetyEnabled);
  }

  @Override
  public void initDefaultCommand() {
    /* TODO: Make this extensible to use any kind of drive (e.g., arcade/curvature) */
    setDefaultCommand(new ArcadeDrive());
  }

  public void teleopInit() {
  }

  public void arcadeDrive(Gamepad gp) {
    this.m_drive.arcadeDrive(gp.getLY(), gp.getRX());
  }

  public void arcadeDrive(double xSpeed, double zRotation) { //contrary to the documentation, but that is ok
		m_drive.arcadeDrive(xSpeed, zRotation, true); //forward, clockwise = positive; decrease sensitivity at low speed is TRUE
	}

  public void tankDrive(Gamepad gp) {
    /* TODO: Investigate why gamepad.getRX() and gamepad.getRY() don't work */
    /* NOTE: gamepad.getRawAxis(4) = gamepad.getRX() expected function */
    this.m_drive.tankDrive(gp.getLY(), gp.getRawAxis(5));
  }
  public void tankDrive(double left, double right) {
    this.m_drive.tankDrive(left, right, true); //forward = positive; decrease sensitivity at low speed is TRUE
  }

  public void stop() {
    this.m_drive.stopMotor();
  }
}
