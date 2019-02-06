/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.util.Constants;

public class ArmPID extends PIDSubsystem {

  public double scaleFactor = 50; 

  public Spark m_arm = new Spark(RobotMap.m_arm);

  public AnalogPotentiometer armPoten = new AnalogPotentiometer(RobotMap.m_armPot, scaleFactor);

  public double zeroPoint = 0;

  private int index    = 0;
  public double output = 0; 
  public double target = 0;

  public ArmPID() {
    
    // Intert a subsystem name and PID values here
    super("ArmPID", Constants.kArmPID_P, Constants.kArmPID_I, Constants.kArmPID_D);
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.

    this.getPIDController().setOutputRange(-0.85, 0.85);
    setAbsoluteTolerance(0.01 * scaleFactor);
    m_arm.setInverted(true);

    this.m_arm = new Spark(RobotMap.m_arm);
  }

  public void init() {
    this.index = 2;
    zeroPoint = armPoten.get();
    this.setPos(index); 

    this.disable();
  }

  public double getPos() {
    return armPoten.get() - zeroPoint;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setPos(int pos) {
    switch(pos) {
      case 0: 
        this.setSetpoint(zeroPoint + 0.25 * scaleFactor);
        this.target = (zeroPoint + 0.25 * scaleFactor); 
        break;
      case 1:
        this.setSetpoint(zeroPoint + 0.55 * scaleFactor);
        this.target = (zeroPoint + 0.55 * scaleFactor);
        break;
      case 2: 
        this.setSetpoint(zeroPoint + 0.02 * scaleFactor);
        this.target = (zeroPoint + 0.02 * scaleFactor);
        break;
      default: 
        this.setSetpoint(0);
        break;
    }
    this.index = pos; 
  }

  public void raise() {
    index = Math.min(++index, 2); 
    setPos(index); 
  }

  public void lower() {
    index = Math.max(--index, 0);
    setPos(index); 
  }

  @Override
  protected double returnPIDInput() {
    return armPoten.get();
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
  }

  @Override
  protected void usePIDOutput(double output) {
    this.set(output);
    this.output = output; 
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
  }

  public void set(double output) {
    m_arm.set(output); 
    this.output = output; 
  }

  public int getIndex() {
    return this.index;
  }

  public void setIndex(int input) {
    this.index = input; 
    this.setPos(this.index); 
  }

  private Spark ArmPID; 

  public void ArmPIDRaise(){
    m_arm.set(Constants.kMaxArmSpeed);
  }

  public void ArmPIDLower(){
    m_arm.set(-Constants.kMaxArmSpeed);
  }

  public void ArmPIDStop(){

  }
}