package org.wildstang.outputmanager.base;

import java.util.HashMap;
import java.util.Map;

import org.wildstang.outputmanager.outputs.WsDoubleSolenoid;
import org.wildstang.outputmanager.outputs.WsDriveSpeed;
import org.wildstang.outputmanager.outputs.WsSolenoid;
import org.wildstang.outputmanager.outputs.WsTalon;
import org.wildstang.outputmanager.outputs.WsVictor;
import org.wildstang.outputmanager.outputs.no.NoOutput;

import edu.wpi.first.wpilibj.Compressor;

/**
 *
 * @author Nathan
 */
public class OutputManager {

	private static OutputManager instance = null;
	private static Map<Integer, IOutput> outputs = new HashMap<Integer, IOutput>();

	/**
	 * Method to obtain the instance of the OutputManager singleton.
	 *
	 * @return the instance of the OutputManager.
	 */
	public static OutputManager getInstance() {
		if (OutputManager.instance == null) {
			OutputManager.instance = new OutputManager();
		}
		return OutputManager.instance;
	}

	/**
	 * Method to cause all output elements to update.
	 */
	public void init() {
	}

	public void update() {
		for (Integer key : outputs.keySet()) {
			IOutput out = outputs.get(key);
			if (out != null) {
				out.update();
			}
		}
	}

	/**
	 * Method to notify all output elements that a config change has occurred and config values need to be re-read.
	 */
	public void notifyConfigChange() {
		for (Integer key : outputs.keySet()) {
			IOutput out = outputs.get(key);
			if (out != null) {
				out.notifyConfigChange();
			}
		}
	}

	/**
	 * Gets an output element based on a key.
	 *
	 * @param index
	 *            A string representation of the output element.
	 *
	 * @return The output element.
	 */
	public IOutput getOutput(int index) {
		IOutput out = outputs.get(index);
		return out == null ? (IOutput) outputs.get(UNKNOWN_INDEX) : out;
	}

	// Key Values - Need to update for each new output element.
	public static final int UNKNOWN_INDEX = 0;
	public static final int RIGHT_DRIVE_SPEED_INDEX = 1;
	public static final int LEFT_DRIVE_SPEED_INDEX = 2;
	public static final int STRAFE_DRIVE_SPEED_INDEX = 3;
	public static final int SHIFTER_INDEX = 4;
	public static final int LIFT_A_INDEX = 5;
	public static final int LIFT_B_INDEX = 6;
	public static final int INTAKE_WHEEL_LEFT_INDEX = 7;
	public static final int INTAKE_WHEEL_RIGHT_INDEX = 12;
	public static final int INTAKE_PISTONS_INDEX = 8;
	public static final int PAWL_RELEASE_INDEX = 9;
	public static final int TOP_CONTAINMENT_INDEX = 10;
	public static final int BIN_GRABBER_INDEX = 13;
	
	/**
	 * Constructor for OutputManager.
	 *
	 * All new output elements need to be added in the constructor as well as having a key value added above.
	 */

	protected OutputManager() {
		outputs.put(UNKNOWN_INDEX, new NoOutput());

		// MOTORS
		outputs.put(RIGHT_DRIVE_SPEED_INDEX, new WsDriveSpeed("Right Drive Speed", 2, 3));
		outputs.put(LEFT_DRIVE_SPEED_INDEX, new WsDriveSpeed("Left Drive Speed", 0, 1));
		outputs.put(STRAFE_DRIVE_SPEED_INDEX, new WsDriveSpeed("Strafe Drive Speed", 4, 5));
		outputs.put(LIFT_A_INDEX, new WsVictor("Lift A", 6));
		outputs.put(LIFT_B_INDEX, new WsVictor("Lift B", 7));
		outputs.put(INTAKE_WHEEL_RIGHT_INDEX, new WsVictor("Right Intake Wheels", 9));
		outputs.put(INTAKE_WHEEL_LEFT_INDEX, new WsVictor("Left Intake Wheel", 8));

		// SOLENOIDS
		outputs.put(SHIFTER_INDEX, new WsDoubleSolenoid("Shifter", 0, 1));
		outputs.put(PAWL_RELEASE_INDEX, new WsSolenoid("Pawl Release", 2));
		outputs.put(INTAKE_PISTONS_INDEX, new WsSolenoid("Intake Pistons", 4));
		outputs.put(TOP_CONTAINMENT_INDEX, new WsSolenoid("Top Containment", 6));
		outputs.put(BIN_GRABBER_INDEX, new WsDoubleSolenoid("Bin Grabbers", 3, 5));
	}
}
