package org.wildstang.fw.outputmanager;

import java.util.HashMap;
import java.util.Map;

import org.wildstang.fw.outputs.NoOutput;
import org.wildstang.fw.outputs.WsDoubleSolenoid;
import org.wildstang.fw.outputs.WsDriveSpeed;
import org.wildstang.fw.outputs.WsSolenoid;
import org.wildstang.fw.outputs.WsTalon;
import org.wildstang.fw.outputs.WsVictor;
import org.wildstang.yearly.robot.Robot;

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
		return out == null ? (IOutput) outputs.get(Robot.UNKNOWN_INDEX) : out;
	}

	
	
	public void addOutput(int index, IOutput output) {
		outputs.put(index, output);
	}
	
	/**
	 * Constructor for OutputManager.
	 *
	 * All new output elements need to be added in the constructor as well as having a key value added above.
	 */

	protected OutputManager() {
		
	}
}
