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

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class USBCamera extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private CameraServer camera;
  private CameraServer camera2;
  private UsbCamera m_camera;
 

  public USBCamera(){
    camera = CameraServer.getInstance();
    camera2 = CameraServer.getInstance();
  }

  public void displayCapture(CameraServer cameraServer, boolean terminate){
    m_camera = cameraServer.startAutomaticCapture();
    m_camera.setResolution(160, 120);
    m_camera.setFPS(30);
    m_camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
    if (terminate)
      throw new ArithmeticException("Switching camera input");
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new MySpecialCommand());
  }
  /*
  Saving the original setups

  camera.setResolution(160, 120);
  camera.setFPS(5);
  camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

  camera2.setResolution(160, 120);
  camera2.setFPS(5);
  camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
  */
}
