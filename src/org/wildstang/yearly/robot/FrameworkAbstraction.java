/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.robot;

import org.wildstang.fw.auto.AutonomousManager;
import org.wildstang.fw.configmanager.ConfigManager;
import org.wildstang.fw.configmanager.ConfigManagerException;
import org.wildstang.fw.inputmanager.InputManager;
import org.wildstang.fw.logger.LogManager;
import org.wildstang.fw.logger.Logger;
import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.subsystemmanager.SubsystemManager;

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

		//Instantiate all the singletons.
		LogManager.getInstance();
		InputManager.getInstance();
		OutputManager.getInstance();
		SubsystemManager.getInstance().init();
		SubsystemManager.getInstance().setManagers(InputManager.getInstance(), OutputManager.getInstance());
		Logger.getLogger().readConfig();
		AutonomousManager.getInstance();
		Robot.getInstance();
		
		//sets up the USB camera for streaming to the smartdashboard
		//this is unneeded if using an Ethernet camera (or no camera)
		/*try
		{
	        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	        // the camera name (ex "cam0") can be found through the roborio web interface
	        session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	        CameraServer.getInstance().setQuality(1);
	        NIVision.IMAQdxConfigureGrab(session);

	        NIVision.IMAQdxStartAcquisition(session);
		}
		catch(Exception e){}*/
	}

	public static void disabledInit() {
		AutonomousManager.getInstance().clear();
		try {
			ConfigManager.getInstance().readConfig();
		} catch (ConfigManagerException e) {
			System.out.println(e.getMessage());
		}

		SubsystemManager.getInstance().init();
		Logger.getLogger().readConfig();
		LogManager.getInstance().endLog();
	}

	public static void disabledPeriodic() {
		InputManager.getInstance().updateOiData();
		LogManager.getInstance().queueCurrentLogsForSending();
	}

	public static void autonomousInit() {
		SubsystemManager.getInstance().init();
		AutonomousManager.getInstance().startCurrentProgram();
		Logger.getLogger().readConfig();
		LogManager.getInstance().startLog();
	}

	public static void autonomousPeriodic() {
		InputManager.getInstance().updateOiDataAutonomous();
		InputManager.getInstance().updateSensorData();
		AutonomousManager.getInstance().update();
		SubsystemManager.getInstance().update();
		OutputManager.getInstance().update();
		LogManager.getInstance().queueCurrentLogsForSending();
	}

	public static void teleopInit() {
		SubsystemManager.getInstance().init();
		Logger.getLogger().readConfig();
		LogManager.getInstance().startLog();
	}

	public static void teleopPeriodic() {
		long cycleStartTime = System.currentTimeMillis();
		System.out.println("Cycle separation time: " + (cycleStartTime - lastCycleTime));
		InputManager.getInstance().updateOiData();
		InputManager.getInstance().updateSensorData();
		SubsystemManager.getInstance().update();
		OutputManager.getInstance().update();
		LogManager.getInstance().queueCurrentLogsForSending();

		/*try
		{
	        NIVision.IMAQdxGrab(session, frame, 1);
	        CameraServer.getInstance().setImage(frame);
		}
		catch(Exception e){}*/
       
		long cycleEndTime = System.currentTimeMillis();
		long cycleLength = cycleEndTime - cycleStartTime;
		System.out.println("Cycle time: " + cycleLength);
		lastCycleTime = cycleEndTime;
	}

}
