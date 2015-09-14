/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.wildstang.yearly.robot;

import org.wildstang.fw.auto.AutonomousManager;
import org.wildstang.fw.configmanager.ConfigManager;
import org.wildstang.fw.configmanager.ConfigManagerException;
import org.wildstang.fw.inputmanager.InputManager;
import org.wildstang.fw.logger.LogManager;
import org.wildstang.fw.logger.Logger;
import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.profiling.ProfilingTimer;
import org.wildstang.fw.subsystemmanager.SubsystemManager;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.IterativeRobot;

//import edu.wpi.first.wpilibj.Watchdog;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class RobotTemplate extends IterativeRobot {

	private static long lastCycleTime = 0;
	private static int session;
	private static Image frame;
	/**
	 * This function is run when the robot is first started up and should be used for any initialization code.
	 */
	public void robotInit() {
		startupTimer.startTimingSection();
		try {
			ConfigManager.getInstance().setConfigFileName("/ws_config.txt");
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
		
		Logger.getLogger().always(this.getClass().getName(), "robotInit", "Startup Completed");
		startupTimer.endTimingSection();

	}

	ProfilingTimer durationTimer = new ProfilingTimer("Periodic method duration", 50);
	ProfilingTimer periodTimer = new ProfilingTimer("Periodic method period", 50);
	ProfilingTimer startupTimer = new ProfilingTimer("Startup duration", 1);
	ProfilingTimer initTimer = new ProfilingTimer("Init duration", 1);

	public void disabledInit() {
		initTimer.startTimingSection();
		AutonomousManager.getInstance().clear();
		try {
			ConfigManager.getInstance().readConfig();
		} catch (ConfigManagerException e) {
			System.out.println(e.getMessage());
		}

		SubsystemManager.getInstance().init();
		Logger.getLogger().readConfig();
		LogManager.getInstance().endLog();
		initTimer.endTimingSection();
		Logger.getLogger().always(this.getClass().getName(), "disabledInit", "Disabled Init Complete");

	}

	public void disabledPeriodic() {
		InputManager.getInstance().updateOiData();
		LogManager.getInstance().queueCurrentLogsForSending();
	}

	public void autonomousInit() {
		SubsystemManager.getInstance().init();
		AutonomousManager.getInstance().startCurrentProgram();
		Logger.getLogger().readConfig();
		LogManager.getInstance().startLog();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		InputManager.getInstance().updateOiDataAutonomous();
		InputManager.getInstance().updateSensorData();
		AutonomousManager.getInstance().update();
		SubsystemManager.getInstance().update();
		OutputManager.getInstance().update();
		LogManager.getInstance().queueCurrentLogsForSending();
		// Watchdog.getInstance().feed();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopInit() {
		SubsystemManager.getInstance().init();
		Logger.getLogger().readConfig();
		LogManager.getInstance().startLog();
		periodTimer.startTimingSection();
	}

	public void teleopPeriodic() {
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
		// Watchdog.getInstance().feed();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		// Watchdog.getInstance().feed();
	}
}
