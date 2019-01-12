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
    //PWM ports
    public static int rightDrive  = 0;
    public static int leftDrive   = 9;
    
    //CAN ports
    //public static int talonsrx_pigeon = 1; //CAN id
    
    //PCM (12V) ports
    /*
    public static int pcm12v    = 0; //CAN id
    public static int launcher1 = 0;
    public static int launcher2 = 1;
    public static int launcher3 = 3;
    */

    /*public static int claw1 = 3;
    public static int claw2 = 4;
    public static int shifter1 = 6;
    public static int shifter2 = 7;
    
    public static int pcm24v = 0;*/
    
    //PCM (24V) ports
    /*
    public static int pcm24v   = 1; //CAN id
    public static int claw1    = 0;
    public static int claw2    = 1;
    public static int shifter1 = 6;
    public static int shifter2 = 7;
    
    //DIO ports
    public static int right_encoderA    = 0;
    public static int right_encoderB    = 1;
    public static int left_encoderA     = 2;
    public static int left_encoderB     = 3;
    
    public static int ultrasonic1       = 4;
    public static int ultrasonic2       = 5;
    public static int arm_switch_top    = 8;
    public static int arm_switch_bottom = 9;
    
    //Analog ports
    public static int lowPressureSensor = 2;
    public static int potentiometer1    = 3;
    */
}
