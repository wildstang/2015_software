package org.wildstang.outputmanager.base;

import java.util.ArrayList;
import java.util.List;

import org.wildstang.outputmanager.outputs.WsDoubleSolenoid;
import org.wildstang.outputmanager.outputs.WsDriveSpeed;
import org.wildstang.outputmanager.outputs.no.NoOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class OutputManager {

	private static OutputManager instance = null;
	private static List<IOutput> outputs = new ArrayList<>();

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
		for (IOutput out : outputs) {
			if (out != null) {
				out.update();
			}
		}
	}

	/**
	 * Method to notify all output elements that a config change has occurred
	 * and config values need to be re-read.
	 */
	public void notifyConfigChange() {
		for (IOutput out : outputs) {
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
		if (index >= 0 && index < outputs.size()) {
			return (IOutput) outputs.get(index);
		}
		return (IOutput) outputs.get(UNKNOWN_INDEX);
	}

	// Key Values - Need to update for each new output element.
	public static final int UNKNOWN_INDEX = 0;
	public static final int RIGHT_DRIVE_SPEED_INDEX = 1;
	public static final int LEFT_DRIVE_SPEED_INDEX = 2;
	public static final int SHIFTER_INDEX = 3;
	public static final int HOOKS_SOLENOID_INDEX = 5;
	

	/**
	 * Constructor for OutputManager.
	 *
	 * All new output elements need to be added in the constructor as well as
	 * having a key value added above.
	 */

	protected OutputManager() {
		// Add the facade data elements
		outputs.add(UNKNOWN_INDEX, new NoOutput());
		outputs.add(RIGHT_DRIVE_SPEED_INDEX, new WsDriveSpeed("Right Drive Speed", 2, 3));
		outputs.add(LEFT_DRIVE_SPEED_INDEX, new WsDriveSpeed("Left Drive Speed", 0, 1));
		outputs.add(SHIFTER_INDEX, new WsDoubleSolenoid("Shifter", 0, 1));
		outputs.add(HOOKS_SOLENOID_INDEX, new WsDoubleSolenoid("Hooks", 2, 3));
	}
}
