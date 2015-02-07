package org.wildstang.subsystems.base;

import java.util.HashMap;
import java.util.Map;

import org.wildstang.subsystems.AutoMovementControl;
import org.wildstang.subsystems.Chute;
import org.wildstang.subsystems.Containment;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.Hooks;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.LED;
import org.wildstang.subsystems.Lift;
import org.wildstang.subsystems.Monitor;
import org.wildstang.subsystems.Test;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class SubsystemContainer {

	private static SubsystemContainer instance = null;
	private static Map<Integer, Subsystem> subsystems = new HashMap<Integer, Subsystem>();

	public static SubsystemContainer getInstance() {
		if (instance == null) {
			instance = new SubsystemContainer();
		}
		return instance;
	}

	public void init() {
		SmartDashboard.putString("subsystems-init", "Initing subsystems!");
		for (Map.Entry<Integer, Subsystem> entry : subsystems.entrySet()) {
			Subsystem sys = entry.getValue();
			if (sys != null) {
				SmartDashboard.putString("subsystems-init", "Ayyy, subsystem not null, inited!");
				sys.init();
			} else {
				SmartDashboard.putString("subsystems-init", "Damn, it's null.");
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
		SmartDashboard.putString("subsystems-update", "Updating subsystems!");
		for (Map.Entry<Integer, Subsystem> entry : subsystems.entrySet()) {
			Subsystem sys = entry.getValue();
			if (sys != null) {
				SmartDashboard.putString("subsystems-update", "Ayyy, subsystem not null, updated!");
				sys.update();
			} else {
				SmartDashboard.putString("subsystems-update", "Damn, it's null.");
			}
		}
	}

	/**
	 * Notifies all subsystems a config change has occurred and config params
	 * should be re-read.
	 */
	public void notifyConfigChange() {
		for (Map.Entry<Integer, Subsystem> entry : subsystems.entrySet()) {
			Subsystem sys = entry.getValue();
			if (sys != null) {
				sys.notifyConfigChange();
			}
		}
	}

	// Subsystem keys - must add a new key for each subsystem.
	public static final String DRIVE_BASE = "DriveBase";
	public static final String WS_COMPRESSOR = "WsCompressor";
	public static final String LED = "LED";
	public static final String AUTO_MOVEMENT_CONTROLLER = "AutoMovementController";

	public static final int DRIVE_BASE_INDEX = 0;
	public static final int WS_COMPRESSOR_INDEX = 1;
	public static final int LED_INDEX = 2;
	public static final int AUTO_MOVEMENT_CONTROLLER_INDEX = 3;
	public static final int CHUTE_INDEX = 4;
	public static final int MONITOR_INDEX = 5;
	public static final int LIFT_INDEX = 6;
	public static final int TEST_INDEX = 7;
	public static final int INDEX_WHEELS_INDEX = 8;
	public static final int HOOKS_SOLENOID_INDEX = 9;
	public static final int CONTAINMENT_INDEX = 10;

	/**
	 * Constructor for the subsystem container.
	 *
	 * Each new subsystem must be added here. This is where they are
	 * instantiated as well as placed in the subsystem container.
	 */
	protected SubsystemContainer() {
		subsystems.put(DRIVE_BASE_INDEX, new DriveBase(DRIVE_BASE));
		subsystems.put(LED_INDEX, new LED(LED));
		subsystems.put(AUTO_MOVEMENT_CONTROLLER_INDEX, new AutoMovementControl(AUTO_MOVEMENT_CONTROLLER));
		subsystems.put(TEST_INDEX, new Test());
		subsystems.put(LIFT_INDEX, new Lift("Lift"));
		subsystems.put(MONITOR_INDEX, new Monitor("Monitor"));
		subsystems.put(CHUTE_INDEX, new Chute("Chute"));
		subsystems.put(INDEX_WHEELS_INDEX, new IntakeWheels("Intake"));
		subsystems.put(HOOKS_SOLENOID_INDEX, new Hooks("Hooks"));
		subsystems.put(CONTAINMENT_INDEX, new Containment("Containment"));
	}
}
