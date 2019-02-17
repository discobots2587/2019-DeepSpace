package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class Dashboard {
	private static final double SHORT_DELAY = 0.1;
	private static final double LONG_DELAY = 0.5;

	private static double shortTime = 0.0;
	private static double longTime = 0.0;

	public static void init() {
	}

	public static void autoInit() {
	}

	//Short & Long dashboard update based of off 3847's dashboard
	public static void update() {
		if((Timer.getFPGATimestamp() - shortTime) > SHORT_DELAY) {
			shortTime = Timer.getFPGATimestamp();
			updateShort();
		}
		if((Timer.getFPGATimestamp() - longTime) > LONG_DELAY) {
			longTime = Timer.getFPGATimestamp();
			updateLong();
		}
	}

	public static void updateShort() {
	}

	public static void updateLong() {
		SmartDashboard.putNumber("Roller Motor Current",Robot.m_pdp.getCurrent(RobotMap.m_rollerMotor));
		SmartDashboard.putNumber("Roller Motor Power", Robot.m_cargoIntake.getRollerPercent());
	}
}
