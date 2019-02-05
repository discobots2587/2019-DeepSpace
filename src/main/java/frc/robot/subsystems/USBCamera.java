/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class USBCamera extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private UsbCamera m_camera1;
  private UsbCamera m_camera2;
  private VideoSink server;
  public volatile int whichCamera;
  


  public USBCamera(){
    m_camera1 = CameraServer.getInstance().startAutomaticCapture();
    m_camera1.setResolution(160, 120);
    m_camera1.setFPS(24);
    m_camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

    m_camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    m_camera2.setResolution(160, 120);
    m_camera2.setFPS(24);
    m_camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

    server = CameraServer.getInstance().getServer();

    whichCamera = 1;
  }
  
  public void cameraInit(){
    new Thread(()-> {
      Robot.m_camera.setCamera(Robot.m_camera.getCamera(1));
    }).start();
  }

  public VideoSink getServer(){
    return server;
  }

  public void setCamera(UsbCamera camera){
    server.setSource(camera);
  }

  public UsbCamera getCamera(int cameraNumber){
    if (cameraNumber == 1)
      return m_camera1;
    else if (cameraNumber == 2)
      return m_camera2;

    return m_camera1;
  }

  public void toggleCameras(){
    if(whichCamera == 1){
      setCamera(m_camera2);
      whichCamera = 2;
    } else{
      setCamera(m_camera1);
      whichCamera = 1;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new MySpecialCommand());
  }
}
