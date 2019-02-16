/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.StopWrist;
import frc.robot.util.Constants;
import frc.robot.util.Preset;

/**
 * Add your docs here.
 */
public class Wrist extends Subsystem {
  /* Initialize Talon motor to rotate wrist */
  private TalonSRX m_wrist;

  /* Initialize top and bottom limit switches */
  private DigitalInput m_topSwitch;
  private DigitalInput m_bottomSwitch;

  public int posThreshold = 25;

  /* TODO: add more presets as we need them */
  /* TODO: check/test/correct value of TOP preset */
  private static final Preset BOTTOM = new Preset(0);
  private static final Preset TOP = new Preset(5000);
  private static final Preset[] wristPresets = new Preset[] {BOTTOM, TOP};
  private int currentPreset = 0;

  private boolean manualWristControl;
  private double m_motorSpeed;

  public Wrist() {
    m_wrist = new TalonSRX(RobotMap.m_wristMotor);

    //m_topSwitch = new DigitalInput(RobotMap.m_wristTopLimit);
    //m_bottomSwitch = new DigitalInput(RobotMap.m_wristBottomLimit);

    /* Config wrist motor and pid */
    m_wrist.setInverted(true);
    /* TODO: tune PID constants */
    m_wrist.configOpenloopRamp(0.5, 0);
    m_wrist.setNeutralMode(NeutralMode.Brake);
    //m_wrist.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    //m_wrist.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    //m_wrist.overrideLimitSwitchesEnable(true);
    //m_wrist.config_kP(0, Constants.kWristKP, 0);
    //m_wrist.config_kD(0, Constants.kWristKD, 0);
    //m_wrist.config_kI(0, Constants.kWristKI, 0);
    m_wrist.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    //m_wrist.configMotionCruiseVelocity(Constants.kWristCruiseVel, 0);
    //m_wrist.configMotionAcceleration(Constants.kWristAcceleration, 0);
    m_wrist.configContinuousCurrentLimit(40, 0);
    m_wrist.configPeakCurrentLimit(60, 0);
    m_wrist.configPeakCurrentDuration(100, 0);
    m_wrist.enableCurrentLimit(true);

    this.resetSensors();
    this.manualWristControl = true;
    this.m_motorSpeed = 0.0;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new StopWrist());
  }

  public void goTo(double pos) {
    //m_wrist.set(ControlMode.MotionMagic, pos);
  }

  public void goTo(Preset preset) {
    this.goTo(preset.getPos());
  }

  public boolean atBottom() {
    return false;
    //return m_wrist.getSensorCollection().isRevLimitSwitchClosed();
  }

  public boolean atTop() {
    return false;
    //return m_wrist.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public void setMotor(double value) {
    if (value > Constants.kMaxWristSpeed) {
      value = Constants.kMaxWristSpeed;
    } else if (value < -Constants.kMaxWristSpeed) {
      value = -Constants.kMaxWristSpeed;
    }

    this.m_motorSpeed = value;

    m_wrist.set(ControlMode.PercentOutput, this.m_motorSpeed);
  }

  public double getMotorSpeed() {
    return this.m_motorSpeed;
  }

  public void stop() {
   //m_wrist.set(ControlMode.PercentOutput, 0);
  }

  /* TODO: check this is the right setting function */
  public void resetSensors() {
    m_wrist.getSensorCollection().setQuadraturePosition(0, 0);
  }

  /* TODO: check this is the right getting function */
  public double getPos() {
    return m_wrist.getSensorCollection().getQuadraturePosition();
  }

  public Preset nextPreset() {
    if (currentPreset == wristPresets.length - 1) {
      return wristPresets[currentPreset];
    } else {
      currentPreset += 1;
      return wristPresets[currentPreset];
    }
  }

  public Preset previousPreset() {
    if (currentPreset == 0) {
      return wristPresets[currentPreset];
    } else {
      currentPreset -= 1;
      return wristPresets[currentPreset];
    }
  }

  public boolean getManualWristControl() {
    return this.manualWristControl;
  }
  public void toggleWristControl() {
    this.manualWristControl = !this.manualWristControl;
  }
}