package frc.robot.util;

//Constants - useful numbers put into a place that makes them easy to change (no magic numbers!)
public class Constants {

	//kRampingAdjust - multiply ramped value by this constant
	public static double kRampingAdjust = 0.5;
	
	//kDeadband - driving ignores this value and lower for the controller inputs
	public static double kDeadband = 0.03;
	
	//kRampband - used only in auton, this is the most the drive motor can change by (2.0 or higher makes this negligible)
	public static double kRampband = 0.05;

	//kDriveExpiration - timeout with regards to motor safety
	public static double kDriveTimeout = 0.5;
	public static boolean kSafetyEnabled = true;

	//kDriveLowPassFilter - constant for ramping drive with a low pass filter
	public static double kDriveLowPassFilter = 0.25;
	
	//kScaleWait and kLaunchWait - the delay for launching with the switch versus launching with the scale
	public static long kScaleWait = 150;
	public static long kSwitchWait = 50;
	
	//kIntakeWait - the delay between setting the intake to open and launching the cube
	public static long kIntakeWait = 250;
	
	//kLaunchCooldown - waits this period of time (milliseconds) after launching 
	public static long kLaunchCooldown = 1000;
	
	//kInchPerTick - the distance (in inches) per drive encoder tick
	//public static double kInchPerTick = (20.0)/(1900.0)*(100.0/140.0)*(112.0/150.0);
	public static double kInchPerTick = 0.01043502;
	public static double kTickPerInch = 1.0/(kInchPerTick);
	
	//kEncoderTuringFactor - the number to divide the encoder diff. by in order to get accurate encoder turns
	public static double kEncoderTurningFactor = 1.0;
	
	//kIntakeSpeed - the speed that the intake motors operate at (both forward and backward)
	public static double kIntakeSpeed = 0.75;

	//kMaxRollerPercent - maximum percent speed for the rollers
	public static double kMaxRollerPercent = 1.0;

	//kWristDP, kWristKD, kWristKI - PID constants for Wrist subsystem
	public static double kWristKP = 1.0;
	public static double kWristKD = 0.5;
	public static double kWristKI = 0.1;

	//kWristCruiseVel - cruise velocity of wrist motor in encoder units per 100 ms
	public static int kWristCruiseVel = 50;

	//kWristAcceleration - max acceleration of wrist motor in encoder units per 100 ms per 1 s
	public static int kWristAcceleration = 10;

	//kHatchDelay - the time that the hatch launcher solenoids wait before retracting
	public static double kHatchDelay = 3.0;

	//kRollerIRThreshold - threshold IR value when the cargo is in the rollers (from 0-4096)
	public static int kRollerIRThreshold = 2000;

	//kMaxWristSpeed - maximum speed of the wrist
	public static double kMaxWristSpeed = 0.90;

	//kMaxWristSpeed - maximum speed of the wrist
	public static double kMaxLiftSpeed = 0.50;

	//kLiftHoldSpeed - speed to hold lift in place
	public static double kLiftHoldSpeed = 0.10;

	//kRollerCurrentThreshold - current output from PDP to roller motor when cargo is held (in amperes)
	public static double kRollerCurrentThreshold = 20.0; // full stall is ~35.0 - no ball ~3.5 - initial possession ~14

	//kRollerHoldPercent - amount of power to send to roller motor to hold cargo
	public static double kRollerHoldPercent = 0.10;

	//kCargoEjectTime - the amount of time the rollers need to spin out to eject cargo (in seconds)
	public static double kCargoEjectTime = 0.1;

	//kCargoEjectSpinBackTime - the amount of time the rollers need to spin in to give cargo a boost (?) (in seconds)
	public static double kCargoEjectSpinBackTime = 0.3;

	//kWristPosThreshold - various encoder position thresholds to adjust wrist motor power
	public static int kMinWristPosThreshold = 3800;
	public static int kMidWristPosThreshold = kMinWristPosThreshold / 2;
	public static int kMaxWristPosThreshold = 0; // default when wrist 90 degrees

	//kLiftPosThreshold - various encoder position thresholds to adjust lift motor power
	public static int kMinLiftPosThreshold = 1000; // default when lift at bottom (want to kill motors when close to bottom and use gravity to let it slowly fall)
	public static int kMaxLiftPosThreshold = 19000; // TODO: Calibrate to a bit higher than L3 rocket! Assume encoder values increase when raising
	public static int kMidLiftPosThreshold = kMaxLiftPosThreshold / 2;
	public static int kLiftPosThresholdOffset = kMaxLiftPosThreshold / 4; // TODO: Calibrate towards the ends of lift positions
	public static int kLiftPosScoreCargoShip = 7000; // TODO: Calibrate
	public static int kLiftPosScoreL1Rocket = 0;  // TODO: Calibrate
	public static int kLiftPosScoreL2Rocket = 8000;  // TODO: Calibrate
	public static int kliftPosScoreL3Rocket = 16000;  // TODO: Calibrate

	//kAxisButtonSensitivity - when axis button input surpass this number, the input from the axis button will be likea normal button
	public static double kAxisButtonSensitivity = 0.4;
 }