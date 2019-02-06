/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  /* PWM ports */
  public static int m_rightDrive      = 0;
  public static int m_rightIntake     = 1;
  public static int m_leftIntake      = 2;
  public static int m_arm             = 3;
  public static int m_leftDrive       = 9;
  
  /* CAN ports */
  public static int m_talonSrxPigeon  = 1; // CAN id

  /*
   * The PCM is capable of powering either 12V or 24V solenoids, but all
   * solenoids connected to a single PCM must be the same voltage.
   */
  /* PCM (12V) ports */
  public static int m_pcm12v          = 0; // CAN id
  /* TODO: Add solenoids from other subsystems */

  /* PCM (24V) ports */
  public static int m_pcm24v          = 1; // CAN id
  public static int gripIn            = 0;
	public static int gripOut           = 1;

  /* TODO: Add solenoids from other subsystems */

  /* DIO ports */
  public static int m_rightEncoderA   = 0;
  public static int m_rightEncoderB   = 1;
  public static int m_leftEncoderA    = 2;
  public static int m_leftEncoderB    = 3;
  /* TODO: Add digital sensors (ultrasonic/limit switch) from other subsystems */

  /* Analog ports */
  public static int m_armPot          = 3;
  /* TODO: Add analog sensors (pressure/potentiometer) from other subsystems */
}
