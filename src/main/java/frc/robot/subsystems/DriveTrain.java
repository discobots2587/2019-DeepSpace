/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.hal.HAL;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.RobotMap;
import frc.robot.util.Constants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.CurvatureDrive;

/**
 * Contains objects and capabilities related to the drivetrain
 */
public class DriveTrain extends Subsystem {
  private TalonSRX m_leftMaster;
  private TalonSRX m_rightMaster;
  private VictorSPX m_leftSlave;
  private VictorSPX m_rightSlave;
  private Solenoid m_shifter;

  private boolean rampingUsed;
  private double[] lastInputs;

  public static final double kDefaultQuickStopThreshold = 0.2;
  public static final double kDefaultQuickStopAlpha = 0.1;

  private double m_quickStopThreshold = kDefaultQuickStopThreshold; 
  private double m_quickStopAlpha = kDefaultQuickStopAlpha;
  private double m_quickStopAccumulator;

  private boolean isHatchSide;
  private boolean isLowGear;
  private boolean isArcadeDrive;

  public DriveTrain() {
    this.m_leftMaster = new TalonSRX(RobotMap.m_leftMasterMotor);
    this.m_rightMaster = new TalonSRX(RobotMap.m_rightMasterMotor);
    this.m_leftSlave = new VictorSPX(RobotMap.m_leftSlaveMotor);
    this.m_rightSlave = new VictorSPX(RobotMap.m_rightSlaveMotor);

    this.m_shifter = new Solenoid(RobotMap.m_shifter);
    this.setLowGear();

    /* Setup ramping */
    this.lastInputs = new double[2];
    this.rampingUsed = true;

    /* Setup which side is considered "front": Hatch or Cargo side */
    this.isHatchSide = false;
    this.toggleDriveDirection(); // default to the Hatch side

    /* Configure master-slave for left and right motors */
    this.m_leftMaster.setNeutralMode(NeutralMode.Brake);
    this.m_leftSlave.follow(this.m_leftMaster);
    this.m_leftSlave.setNeutralMode(NeutralMode.Brake);
    this.m_rightMaster.setNeutralMode(NeutralMode.Brake);
    this.m_rightSlave.follow(this.m_rightMaster);
    this.m_rightSlave.setNeutralMode(NeutralMode.Brake);

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
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
  }


  /* switch between arcade drive and curvature drive */
  public void toggleDriveMode() {
    this.isArcadeDrive = !this.isArcadeDrive;
    if(this.isArcadeDrive) {
      setDefaultCommand(new ArcadeDrive());
    } else {
      setDefaultCommand(new CurvatureDrive());
    }
  }

  /* OI input-handling helper functions */
  /**
   * Returns 0.0 if the given value is within the specified range around zero. The remaining range
   * between the deadband and 1.0 is scaled from 0.0 to 1.0.
   *
   * @param value value to clip
   */
  public double applyDeadband(double input) {
    if (Math.abs(input) > Constants.kDeadband) {
      if (input > 0.0) {
        return (input - Constants.kDeadband) / (1.0 - Constants.kDeadband);
      } else {
        return (input + Constants.kDeadband) / (1.0 - Constants.kDeadband);
      }
    } else {
      return 0.0;
    }
  }

  public boolean getIsArcadeDrive() {
    return this.isArcadeDrive;
  }

  public void toggleisArcadeDrive() {
    this.isArcadeDrive = !this.isArcadeDrive;
  }

  public boolean getRampingUsed() {
    return this.rampingUsed;
  }

  public void setRampingUsed(boolean used) {
    this.rampingUsed = used;
    this.resetLastInputs();
  }

  public void resetLastInputs() {
    this.lastInputs[0] = 0;
    this.lastInputs[1] = 1;
  }

  public double applySquaredRamping(double input) {
    return input * Math.abs(input);
  }

  public double[] applyLowPassRamping (double[] inputs){
    /* Adjust Constants.kDriveLowPassFilter to make drive accel/decel at desired rate */
    double[] adjustedInputs = new double[2];

    for (int i = 0; i < 2; i++){
      adjustedInputs[i] = (inputs[i] * (1-Constants.kDriveLowPassFilter)) + (Constants.kDriveLowPassFilter * lastInputs[i]);
    }
    
    return adjustedInputs;
  }

  public void arcadeDrive(double throttle, double turn) {
    double leftMotorOutput;
    double rightMotorOutput;
    double maxInput;

    throttle = applyDeadband(throttle);
    turn = applyDeadband(turn);

    if (this.rampingUsed) {
      double[] rampedInput = new double[]{throttle, turn};
      double[] lowpassRampedInput = applyLowPassRamping(rampedInput);
      throttle = lowpassRampedInput[0];
      turn = Constants.kRampingAdjust * applySquaredRamping(rampedInput[1]);
    }

    /*
     * Arcade drive assuming we never hit the boundaries for the motor are as follows:
     * - leftMotorOutput = throttle + turn;
     * - rightMotorOutput = throttle - turn;
     *
     * Examples:
     * 1) full throttle
     * - leftMotorOutput = 1.0 + 0 = 1.0
     * - rightMotorOutput = 1.0 - 0 = 1.0
     *
     * 2) full left with 50% throttle
     * - leftMotorOutput = 0.5 + -1.0 = -0.5
     * - rightMotorOutput = 0.5 - 1.0 = 1.5
     *
     * 3) full right with 100% throttle
     * - leftMotorOutput = 1.0 + 1.0 = 2.0
     * - rightMotorOutput = 1.0 - 1.0 = 0
     *
     * Since we have limits at [-1.0, 1.0], we need to handle this when we can possible be >1.0 or <1.0
     * NOTE: We copy the sign for "throttle" b/c we check throttle sign check is done in the outer-most if-statements
     */
    maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(turn)), throttle);

    if (throttle >= 0.0) {
      if (turn >= 0.0) { // robot turn right
        leftMotorOutput = maxInput; // posThrottle + posTurn can be >1.0
        rightMotorOutput = throttle - turn;
      } else { // robot turn left
        leftMotorOutput = throttle + turn;
        rightMotorOutput = maxInput; // posThrottle - negTurn = posThrottle + posTurn can be >1.0
      }
    } else { // turn like a car does in reverse
      if (turn >= 0.0) { // robot turn right
        leftMotorOutput = throttle + turn;
        rightMotorOutput = maxInput; // negThrottle - posTurn = negThrottle + negTurn can be <-1.0
      } else { // robot turn left
        leftMotorOutput = maxInput; // negThrottle + negTurn can be <-1.0
        rightMotorOutput = throttle - turn;
      }
    }

    /* Since we inverted the motor, we need to change what is left side and right side */
    if (this.isHatchSide) {
      double saveLeftMotorOutput = leftMotorOutput;
      leftMotorOutput = rightMotorOutput;
      rightMotorOutput = saveLeftMotorOutput;
    }

    if (Math.abs(turn) < Constants.kPrecisionDrivingThreshold && Math.abs(throttle) < Constants.kPrecisionDrivingThreshold) {
      turn *= Constants.kPrecisionTurningPercent;
    }

    this.m_leftMaster.set(ControlMode.PercentOutput, leftMotorOutput);
    this.m_rightMaster.set(ControlMode.PercentOutput, rightMotorOutput);

    if (this.rampingUsed) {
      this.lastInputs[0] = throttle;
      this.lastInputs[1] = turn;
    }
  }

  /*
   * Ensures input is no larger than MAX_VALUE and no smaller than MIN_VALUE
   */
  public double limit(double input) {
    double limitInput = input; 

    if (input > 1) {
      limitInput = 1;
    } else if (input < -1) {
      limitInput = -1;
    }

    return limitInput;
  }

  public void curvatureDrive(double throttle, double turn, boolean quickTurn) {

    throttle = applyDeadband(throttle);
    turn = applyDeadband(turn);

    double angularPower;
    boolean overPower;

    if (this.rampingUsed) {
      double[] rampedInput = applyLowPassRamping(new double[]{throttle, turn});

      throttle = rampedInput[0];
      turn = rampedInput[1];
    }

    if(quickTurn){
      if(Math.abs(throttle) < m_quickStopThreshold){
        m_quickStopAccumulator = (1 - m_quickStopAlpha) * m_quickStopAccumulator + m_quickStopAlpha * limit(turn) * 2;
      }
      overPower = true;
      angularPower = turn; 
    } else { 
      overPower = false; 
      angularPower = Math.abs(throttle) * turn - m_quickStopAccumulator;

      if(m_quickStopAccumulator > 1){
        m_quickStopAccumulator -= 1;
      } else if(m_quickStopAccumulator < -1){
        m_quickStopAccumulator += 1;
      } else {
        m_quickStopAccumulator = 0.0;
      }
    }

    double leftMotorOutput = throttle + turn; 
    double rightMotorOutput = throttle - turn;

    if(overPower){
      if(leftMotorOutput > 1.0){
        rightMotorOutput -= leftMotorOutput - 1.0;
        leftMotorOutput = 1.0;
      } else if(rightMotorOutput > 1.0) {
        leftMotorOutput -= rightMotorOutput - 1.0;
        rightMotorOutput = 1.0;
      } else if(leftMotorOutput < -1.0) {
          rightMotorOutput -= leftMotorOutput + 1.0;
          leftMotorOutput = -1.0;
      } else if(rightMotorOutput < -1.0) {
        leftMotorOutput -= rightMotorOutput + 1.0;
        rightMotorOutput = -1.0;
      }
    }

    double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));

    if(maxMagnitude > 1.0){
      leftMotorOutput /= maxMagnitude;
      rightMotorOutput /= maxMagnitude;
    }

    this.m_leftMaster.set(ControlMode.PercentOutput,leftMotorOutput);
    this.m_rightMaster.set(ControlMode.PercentOutput,rightMotorOutput);
        
    if (this.rampingUsed) {
      this.lastInputs[0] = throttle;
      this.lastInputs[1] = turn;
    }
  }

  public void tankDrive(double leftPower, double rightPower) {
    leftPower = applyDeadband(leftPower);
    rightPower = applyDeadband(rightPower);

    if (this.rampingUsed) {
      double[] rampedInput = applyLowPassRamping(new double[]{leftPower, rightPower});

      leftPower = rampedInput[0];
      rightPower = rampedInput[1];
    }

    /* Since we inverted the motor, we need to change what is left side and right side */
    if (this.isHatchSide) {
      double saveLeftPower = leftPower;
      leftPower = rightPower;
      rightPower = saveLeftPower;
    }

    this.m_leftMaster.set(ControlMode.PercentOutput, leftPower);
    this.m_rightMaster.set(ControlMode.PercentOutput, rightPower);

    if (this.rampingUsed) {
      this.lastInputs[0] = leftPower;
      this.lastInputs[1] = rightPower;
    }
  }

  public void stop() {
    this.m_leftMaster.set(ControlMode.PercentOutput, 0);
    this.m_rightMaster.set(ControlMode.PercentOutput, 0);

    this.resetLastInputs();
  }

  /* Drive direction functions */
  public boolean isFrontToHatch(){
    return this.isHatchSide;
  }

  public void toggleDriveDirection(){
    this.isHatchSide = !this.isHatchSide;

    /* On either side, positive throttle makes the robot go "forward" */
    if (this.isHatchSide) {
      /* Invert only left side so Hatch side is front */
      this.m_leftMaster.setInverted(false);
      this.m_leftSlave.setInverted(false);
      this.m_rightMaster.setInverted(true);
      this.m_rightSlave.setInverted(true);
    } else {
      /* Invert only right side so Cargo side is front */
      this.m_leftMaster.setInverted(true);
      this.m_leftSlave.setInverted(true);
      this.m_rightMaster.setInverted(false);
      this.m_rightSlave.setInverted(false);
    }

    this.resetLastInputs();
  }

  /* Shifting functions */
  public Solenoid getShifter() {
    return m_shifter;
  }

  public void setHighGear() {
    this.m_shifter.set(true);
    this.isLowGear = false;
  }

  public void setLowGear() { 
    this.m_shifter.set(false);
    this.isLowGear = true;
  }
  public void toggleGearShift(){
    if (isLowGear) {
      setHighGear();
    } else {
      setLowGear();
    }
  }

  public String getSide(){
    if (this.isHatchSide){
      return "Hatch Side";
    }
    else {
      return "Cargo Side";
    }
  }
}
