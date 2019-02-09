package frc.robot.util;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class PigeonHandler{
 private PigeonIMU m_pigeon;
 private double[] m_ypr;

 public PigeonHandler(int id){
    this.m_pigeon = new PigeonIMU(id);
    this.m_ypr = new double[3];
 }
 
 public PigeonHandler(TalonSRX talon){
    this.m_pigeon = new PigeonIMU(talon);
    this.m_ypr = new double[3];
 }

 public double[] getYRP(){
    m_pigeon.getYawPitchRoll(m_ypr);
    return m_ypr;
  }

  public double getYaw(){
    m_pigeon.getYawPitchRoll(m_ypr);
    return m_ypr[0];
  }

  public double getPitch(){
    m_pigeon.getYawPitchRoll(m_ypr);
    return m_ypr[1];
  }

  public double getRoll(){
    m_pigeon.getYawPitchRoll(m_ypr);
    return m_ypr[2];
  }

}