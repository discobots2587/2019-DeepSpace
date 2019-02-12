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
  //public static int m_pcm24v           = 1; Not used for now
  public static int m_talonSrxPigeon   = 2;

  public static int m_leftMasterMotor  = 12; // TalonSRX PDP 12
  public static int m_leftSlaveMotor   = 13; // VictorSPX PDP 13
  public static int m_rightMasterMotor = 3;  // TalonSRX PDP 3
  public static int m_rightSlaveMotor  = 2;  // VictorSPX PDP 2

  public static int m_rollerMotor      = 10; // TBD
  public static int m_wristMotor       = 8;  // TalonSRX PDP 8

  /*
   * The PCM is capable of powering either 12V or 24V solenoids, but all
   * solenoids connected to a single PCM must be the same voltage.
   */
  /* PCM (12V) ports */
  public static int m_beakExtend       = 0;
  public static int m_beakRetract      = 1;
  public static int m_launcher         = 2;

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
