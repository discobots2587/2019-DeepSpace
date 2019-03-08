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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.Constants;
import frc.robot.util.Preset;

/**
 * Add your docs here.
 */
public class Lift extends Subsystem {
  private TalonSRX m_liftMaster;
  private VictorSPX m_liftSlave;

  private boolean manualWristControl;
  private double m_motorSpeed;

  public int posThreshold = 25;

  /* TODO: add more presets as we need them */
  /* TODO: check/test/correct value of TOP preset */
  private static final Preset BOTTOM = new Preset(0);
  private static final Preset TOP = new Preset(5000);
  private static final Preset[] liftPresets = new Preset[] {BOTTOM, TOP};
  private int currentPreset = 0;

  public Lift() {
    m_liftMaster = new TalonSRX(RobotMap.m_liftMasterMotor);
    m_liftSlave = new VictorSPX(RobotMap.m_liftSlaveMotor);

    //m_topSwitch = new DigitalInput(RobotMap.m_liftMasterTopLimit);
    //m_bottomSwitch = new DigitalInput(RobotMap.m_liftMasterBottomLimit);

    /* Config wrist motor and pid */
    m_liftMaster.setInverted(false);
    m_liftSlave.setInverted(true);

    /* Configure master-slave for lift motors */
    this.m_liftSlave.follow(this.m_liftMaster);
    this.m_liftSlave.setNeutralMode(NeutralMode.Brake);

    /* TODO: tune PID constants */
    m_liftMaster.configOpenloopRamp(0.5, 0);
    m_liftMaster.setNeutralMode(NeutralMode.Brake);
    //m_liftMaster.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    //m_liftMaster.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
    //m_liftMaster.overrideLimitSwitchesEnable(true);
    //m_liftMaster.config_kP(0, Constants.kWristKP, 0);
    //m_liftMaster.config_kD(0, Constants.kWristKD, 0);
    //m_liftMaster.config_kI(0, Constants.kWristKI, 0);
    m_liftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    //m_liftMaster.configMotionCruiseVelocity(Constants.kWristCruiseVel, 0);
    //m_liftMaster.configMotionAcceleration(Constants.kWristAcceleration, 0);
    m_liftMaster.configContinuousCurrentLimit(40, 0);
    m_liftMaster.configPeakCurrentLimit(60, 0);
    m_liftMaster.configPeakCurrentDuration(100, 0);
    m_liftMaster.enableCurrentLimit(true);

    this.resetSensors();
    this.manualWristControl = true;
    this.m_motorSpeed = 0.0;
  }

  @Override
  public void initDefaultCommand() {
  }

  public void goTo(double pos) {
    m_liftMaster.set(ControlMode.MotionMagic, pos);
  }

  public void goTo(Preset preset) {
    m_liftMaster.set(ControlMode.MotionMagic, preset.getPos());
  }

  public void setMotor(double value) {
    if (value > Constants.kMaxLiftSpeed) {
      value = Constants.kMaxLiftSpeed;
    } else if (value < -Constants.kMaxLiftSpeed) {
      value = -Constants.kMaxLiftSpeed;
    }

    this.m_motorSpeed = value;
    m_liftMaster.set(ControlMode.PercentOutput, this.m_motorSpeed);
  }

  public double getMotorSpeed() {
    return this.m_motorSpeed;
  }

  public void stop() {
    this.m_motorSpeed = 0;
    m_liftMaster.set(ControlMode.PercentOutput, this.m_motorSpeed);
  }

  public void resetSensors() {
    m_liftMaster.getSensorCollection().setQuadraturePosition(0, 0);
  }

  /* TODO: check this is the right getting function */
  public double getPos() {
    return m_liftMaster.getSensorCollection().getQuadraturePosition();
  }

  public boolean getManualWristControl() {
    return this.manualWristControl;
  }
  public void toggleWristControl() {
    this.manualWristControl = !this.manualWristControl;
  }

  public boolean atBottom() {
    return false;
    // return Math.abs(m_liftMaster.getSensorCollection().getQuadraturePosition() - BOTTOM.getPos()) <= posThreshold; 
  }

  public boolean atTop() {
    return false;
    // return Math.abs(m_liftMaster.getSensorCollection().getQuadraturePosition() - TOP.getPos()) <= posThreshold; 
  }
}
