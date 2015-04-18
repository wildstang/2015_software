/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.crio;

import org.wildstang.autonomous.AutonomousManager;
import org.wildstang.configmanager.ConfigManager;
import org.wildstang.configmanager.ConfigManagerException;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.logger.Logger;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subsystems.base.SubsystemContainer;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.FlipAxis;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Alex
 */
public class FrameworkAbstraction {

	private static long lastCycleTime = 0;
	private static int session;
	private static Image frame;

	public static void robotInit(String fileName) {
		try {
			ConfigManager.getInstance().setConfigFileName(fileName);
			ConfigManager.getInstance().readConfig();
		} catch (ConfigManagerException wscfe) {
			System.out.println(wscfe.toString());
		}

		//LogManager.getInstance();
		InputManager.getInstance();
		OutputManager.getInstance();
		SubsystemContainer.getInstance().init();
		Logger.getLogger().readConfig();
		AutonomousManager.getInstance();
		//sets up the USB camera for streaming to the smartdashboard
		//this is unneeded if using an Ethernet camera (or no camera)
		try
		{
	        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	        // the camera name (ex "cam0") can be found through the roborio web interface
	        session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	        CameraServer.getInstance().setQuality(25);
	        NIVision.IMAQdxConfigureGrab(session);

	        NIVision.IMAQdxStartAcquisition(session);
		}
		catch(Exception e){}
	}

	public static void disabledInit() {
		AutonomousManager.getInstance().clear();
		try {
			ConfigManager.getInstance().readConfig();
		} catch (ConfigManagerException e) {
			System.out.println(e.getMessage());
		}

		SubsystemContainer.getInstance().init();
		Logger.getLogger().readConfig();
		//LogManager.getInstance().endLog();
	}

	public static void disabledPeriodic() {
		InputManager.getInstance().updateOiData();
		//LogManager.getInstance().queueCurrentLogsForSending();
	}

	public static void autonomousInit() {
		SubsystemContainer.getInstance().init();
		Logger.getLogger().readConfig();
		AutonomousManager.getInstance().startCurrentProgram();
		//LogManager.getInstance().startLog();
	}

	public static void autonomousPeriodic() {
		InputManager.getInstance().updateOiDataAutonomous();
		InputManager.getInstance().updateSensorData();
		AutonomousManager.getInstance().update();
		SubsystemContainer.getInstance().update();
		OutputManager.getInstance().update();
		//LogManager.getInstance().queueCurrentLogsForSending();
	}

	public static void teleopInit() {
		SubsystemContainer.getInstance().init();
		Logger.getLogger().readConfig();
		//LogManager.getInstance().startLog();
	}

	public static void teleopPeriodic() {
		long cycleStartTime = System.currentTimeMillis();
		System.out.println("Cycle separation time: " + (cycleStartTime - lastCycleTime));
		InputManager.getInstance().updateOiData();
		InputManager.getInstance().updateSensorData();
		SubsystemContainer.getInstance().update();
		OutputManager.getInstance().update();
		//LogManager.getInstance().queueCurrentLogsForSending();

        NIVision.IMAQdxGrab(session, frame, 1);
        CameraServer.getInstance().setImage(frame);
       
		long cycleEndTime = System.currentTimeMillis();
		long cycleLength = cycleEndTime - cycleStartTime;
		System.out.println("Cycle time: " + cycleLength);
		lastCycleTime = cycleEndTime;
	}

}
