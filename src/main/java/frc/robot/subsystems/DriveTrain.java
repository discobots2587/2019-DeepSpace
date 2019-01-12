/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.util.Constants;
import frc.robot.RobotMap;
import frc.robot.lib.RampedMotor;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive; //west coast / tank
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  public Spark m_left;
  public Spark m_right;
  public RampedMotor leftDrive;
  public RampedMotor rightDrive;
  public DifferentialDrive drive;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    m_left = new Spark(RobotMap.leftDrive);
    leftDrive = new RampedMotor(m_left, Constants.kRampband);
		leftDrive.setInverted(true);
    m_right = new Spark(RobotMap.rightDrive);  
		rightDrive = new RampedMotor(m_right,Constants.kRampband);
    rightDrive.setInverted(true);
    drive.setExpiration(0.5);
    
    drive = new DifferentialDrive(leftDrive, rightDrive);
  

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  
  }
  public void teleopInit() {


  }
  public void tankDrive(double left, double right) {
		drive.tankDrive(left, right, true); //forward = positive; decrease sensitivity at low speed is TRUE
	}

}
