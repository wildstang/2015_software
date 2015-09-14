package org.wildstang.subsystemmanager;

import java.util.HashMap;
import java.util.Map;

import org.wildstang.yearly.subsystems.BinGrabber;
import org.wildstang.yearly.subsystems.Containment;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.IntakeWheels;
import org.wildstang.yearly.subsystems.Lift;
import org.wildstang.yearly.subsystems.Monitor;
import org.wildstang.yearly.subsystems.Test;
import org.wildstang.outputmanager.OutputManager;
import org.wildstang.inputmanager.InputManager;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class SubsystemManager {

	private static SubsystemManager instance = null;
	private static Map<Integer, Subsystem> subsystems = new HashMap<Integer, Subsystem>();
	public InputManager inputManager = null;
	public OutputManager outputManager = null;

	public static SubsystemManager getInstance() {
		if (instance == null) {
			instance = new SubsystemManager();
		}
		return instance;
	}

	public void init() {
		for (Map.Entry<Integer, Subsystem> entry : subsystems.entrySet()) {
			Subsystem sys = entry.getValue();
			if (sys != null) {
				sys.init();
			}
		}
	}

	/**
	 * Retrieves a subsystem based on a key value.
	 *
	 * @param key
	 *            The key representing the subsystem.
	 * @return A subsystem.
	 */
	public Subsystem getSubsystem(int index) {
		Subsystem sys = (Subsystem) subsystems.get(index);
		return sys == null ? (Subsystem) null : sys;
	}

	/**
	 * Triggers all subsystems to be updated.
	 */
	public void update() {
		for (Map.Entry<Integer, Subsystem> entry : subsystems.entrySet()) {
			Subsystem sys = entry.getValue();
			if (sys != null) {
				sys.update();
			}
		}
	}

	/**
	 * Notifies all subsystems a config change has occurred and config params should be re-read.
	 */
	public void notifyConfigChange() {
		for (Map.Entry<Integer, Subsystem> entry : subsystems.entrySet()) {
			Subsystem sys = entry.getValue();
			if (sys != null) {
				sys.notifyConfigChange();
			}
		}
	}

	
	
	public void addSubsystem(int index, Subsystem ssystem) {
		subsystems.put(index, ssystem);
	}
	
	public void setManagers(InputManager im, OutputManager om) {
		inputManager = im;
		outputManager = om;
	}

	/*
	 * Constructor for the subsystem container.
	 * 
	 * Each new subsystem must be added here. This is where they are instantiated as well as placed in the subsystem
	 * container.
	 */
	protected SubsystemManager() {
		
	}
}
