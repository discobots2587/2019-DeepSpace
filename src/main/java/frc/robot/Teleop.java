package frc.robot;

import frc.robot.commands.TankDrive;


import edu.wpi.first.wpilibj.command.Scheduler;

public class Teleop {

	public static void init() {
		// Make sure to disable autonomous
		/*
		if(Autonomous.autonCommand != null) {
			Autonomous.autonCommand.cancel();
		}
		
		//choose the drive based off of the dashboard, almost always arcade drive
		Robot.driveCommand = Dashboard.driveChooser.getSelected();
		
		// Instead of a try/catch loop, we can just check if its null
		if(Robot.driveCommand != null) {
			Robot.driveCommand.start();
		} else {
			Debugger.getInstance().log("Drive selector failed, using Arcade Drive","DASH");
			new ArcadeDrive().start();
		}
		*/
		
		//disable rampband
		if(Robot.driveCommand != null) {
			Robot.driveCommand.start();
		}
		if(Robot.drive.leftDrive != null) {
//			Robot.drive.leftDrive.setRampband(2.0);
//			Robot.drive.rightDrive.setRampband(2.0);
			Robot.drive.leftDrive.setRampband(0.5);
			Robot.drive.rightDrive.setRampband(0.5);
		}
		
		//activate manual arm
		//new ArmManual().start();
		
		Robot.drive.teleopInit();
		//Robot.arm.init();
	}
	
	public static void periodic() {
		//Robot.drive.pigeon.getRawGyro(Robot.drive.gyro_xyz);
		//Robot.drive.pigeon.getAccelerometerAngles(Robot.drive.accel_xyz);
		Scheduler.getInstance().run();
	}
}