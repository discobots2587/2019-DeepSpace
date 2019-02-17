/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class USBCamera extends Subsystem {
  private UsbCamera m_camera1;
  private UsbCamera m_camera2;
  private VideoSink m_server;
  private int activeCameraID;

  public USBCamera() {
    m_camera1 = CameraServer.getInstance().startAutomaticCapture();
    m_camera1.setResolution(160, 120);
    m_camera1.setFPS(24);
    m_camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

    m_camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    m_camera2.setResolution(160, 120);
    m_camera2.setFPS(24);
    m_camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

    m_server = CameraServer.getInstance().getServer();
    this.setCamera(m_camera1);
  }

  @Override
  public void initDefaultCommand() {
  }
  
  public void cameraInit() {
    new Thread(()-> {
      this.setCamera(this.getCamera(1));
    }).start();
  }

  public VideoSink getServer() {
    return m_server;
  }

  public void setCamera(UsbCamera camera) {
    this.m_server.setSource(camera);
    this.activeCameraID = getCameraID(camera);
  }

  public int getCameraID(UsbCamera camera){
    String name = camera.getName();
    if (name.equals("USB Camera 0")){
      return 1;
    }
    else if (name.equals("USB Camera 1")){
      return 2;
    }
    return 0;
  }

  public UsbCamera getCamera(int cameraNumber) {
    if (cameraNumber == 1)
      return this.m_camera1;
    else if (cameraNumber == 2)
      return this.m_camera2;

    return this.m_camera1;
  }
  
  public int getActiveCameraID(){
    return this.activeCameraID;
  }
  
  public void toggleCameras() {
    if (activeCameraID == 1) {
      this.setCamera(this.m_camera2);
    } else {
      this.setCamera(this.m_camera1);
    }
  }
}
