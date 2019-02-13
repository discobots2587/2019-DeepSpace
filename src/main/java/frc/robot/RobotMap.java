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

  /* CAN ports */
  public static int m_pcm12v           = 0;
  public static int m_pcm24v           = 1;
  public static int m_talonSrxPigeon   = 2;

  public static int m_motorFrontLeft   = 5;
  public static int m_motorBackLeft    = 6;
  public static int m_motorFrontRight  = 7;
  public static int m_motorBackRight   = 8;

  public static int m_rollerMotor      = 10;
  public static int m_wristMotor       = 11;

  /*
   * The PCM is capable of powering either 12V or 24V solenoids, but all
   * solenoids connected to a single PCM must be the same voltage.
   */
  /* PCM (12V) ports */
  public static int m_beakExtend       = 0;
  public static int m_beakRetract      = 1;
  public static int m_launcherExtend   = 2;
  public static int m_launcherRetract  = 3;

  /* PCM (24V) ports */

  /* DIO ports */
  public static int m_rightEncoderA    = 0;
  public static int m_rightEncoderB    = 1;
  public static int m_leftEncoderA     = 2;
  public static int m_leftEncoderB     = 3;
  public static int m_cargoRollerLimit = 4;
  public static int m_wristTopLimit    = 5;
  public static int m_wristBottomLimit = 6;

  /* Analog ports */
  public static int m_cargoRollerIR    = 3;
}
