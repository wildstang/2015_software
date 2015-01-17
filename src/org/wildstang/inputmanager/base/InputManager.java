package org.wildstang.inputmanager.base;

import java.util.HashMap;
import java.util.Map;

import org.wildstang.inputmanager.inputs.WsAnalogInput;
import org.wildstang.inputmanager.inputs.WsDigitalInput;
import org.wildstang.inputmanager.inputs.driverstation.WsDSAnalogInput;
import org.wildstang.inputmanager.inputs.driverstation.WsDSDigitalInput;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.inputmanager.inputs.joystick.driver.DriverJoystick;
import org.wildstang.inputmanager.inputs.joystick.manipulator.ManipulatorJoystick;
import org.wildstang.inputmanager.inputs.no.NoInput;
import org.wildstang.logger.Logger;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;

/**
 *
 * @author Nathan
 */
public class InputManager {

	private static InputManager instance = null;
	private static Map<Integer, IInput> oiInputs = new HashMap<>();
	private static Map<Integer, IInput> sensorInputs = new HashMap<>();

	/**
	 * Method to get the instance of this singleton object.
	 *
	 * @return The instance of InputManager
	 */
	public static InputManager getInstance() {
		if (instance == null) {
			instance = new InputManager();
		}
		return instance;
	}

	/**
	 * Method to trigger updates of all the sensor data input containers
	 */
	public void updateSensorData() {
		for (Integer key : sensorInputs.keySet()) {
			IInput in = sensorInputs.get(key);
			if (in != null) {
				in.update();
			}
		}
	}

	/**
	 * Method to trigger updates of all the oi data input containers.
	 */
	public void updateOiData() {
		for (Integer key : oiInputs.keySet()) {
			IInput in = oiInputs.get(key);
			if (in != null) {
				in.update();
			}
		}
	}

	public void updateOiDataAutonomous() {
		for (Integer key : oiInputs.keySet()) {
			IInput oiIn = oiInputs.get(key);
			if (oiIn == null) {
				continue;
			}
			if (!(oiIn instanceof DriverJoystick || oiIn instanceof ManipulatorJoystick)) {
				oiIn.pullData();
			}
			oiIn.update();
		}
	}

	/**
	 * Method to notify all input containers that a config update occurred.
	 *
	 * Used by the ConfigManager when the config is re-read.
	 */
	public void notifyConfigChange() {
		for (Integer key : sensorInputs.keySet()) {
			IInput in = sensorInputs.get(key);
			if (in != null) {
				in.notifyConfigChange();
			}
		}
		for (Integer key : oiInputs.keySet()) {
			IInput in = oiInputs.get(key);
			if (in != null) {
				in.notifyConfigChange();
			}
		}
	}

	/**
	 * Gets an OI container, based on a key.
	 *
	 * @param key
	 *            The key that represents the OI input container
	 * @return A WsInputInterface.
	 */
	public IInput getOiInput(int index) {
		IInput in = oiInputs.get(index);		
		return in == null ? (IInput) oiInputs.get(UNKNOWN_INDEX) : in;
	}

	/**
	 * Gets a sensor container, based on a key.
	 *
	 * @param key
	 *            The key that represents the sensor input container
	 * @return A WsInputInterface.
	 */
	public IInput getSensorInput(int index) {
		IInput in = sensorInputs.get(index);		
		return in == null ? (IInput) sensorInputs.get(UNKNOWN_INDEX) : in;
	}

	final public void attachJoystickButton(IInputEnum button, IObserver observer) {
		if (button instanceof JoystickButtonEnum) {
			Subject subject = InputManager.getInstance().getOiInput(((JoystickButtonEnum) button).isDriver() ? InputManager.DRIVER_JOYSTICK_INDEX : InputManager.MANIPULATOR_JOYSTICK_INDEX)
					.getSubject(button);
			subject.attach(observer);
		} else {
			Logger.getLogger().debug(this.getClass().getName(), "attachJoystickButton", "Oops! Check that the inputs implement the required interfaces.");
		}
	}

	/**
	 * Keys to represent OI Inputs
	 */
	public static final int UNKNOWN_INDEX = 0;
	public static final int DRIVER_JOYSTICK_INDEX = 1;
	public static final int MANIPULATOR_JOYSTICK_INDEX = 2;
	public static final int AUTO_PROGRAM_SELECTOR_INDEX = 3;
	public static final int LOCK_IN_SWITCH_INDEX = 4;
	public static final int START_POSITION_SELECTOR_INDEX = 5;
	// Sensor Inputs
	public static final int PRESSURE_TRANSDUCER_INDEX = 1;
	public static final int FRONT_ARM_POT_INDEX = 2;
	public static final int BACK_ARM_POT_INDEX = 3;
	public static final int TENSION_LIMIT_SWITCH_INDEX = 4;
	public static final int BALL_DETECT_SWITCH_INDEX = 5;
	public static final int LATCH_POSITION_SWITCH_INDEX = 6;
	public static final int CATAPULT_DOWN_SWITCH_INDEX = 7;
	public static final int FRONT_ARM_CALIBRATION_SWITCH_INDEX = 8;
	public static final int BACK_ARM_CALIBRATION_SWITCH_INDEX = 9;

	/**
	 * Constructor for the InputManager.
	 *
	 * Each new data element to be added to the facade must be added here and
	 * have keys added above.
	 */
	protected InputManager() {
		// Add the facade data elements
		sensorInputs.put(UNKNOWN_INDEX, new NoInput());
		sensorInputs.put(PRESSURE_TRANSDUCER_INDEX, new WsAnalogInput(4));
		sensorInputs.put(FRONT_ARM_POT_INDEX, new WsAnalogInput(2));
		sensorInputs.put(BACK_ARM_POT_INDEX, new WsAnalogInput(3));
		sensorInputs.put(TENSION_LIMIT_SWITCH_INDEX, new WsDigitalInput(4));
		sensorInputs.put(BALL_DETECT_SWITCH_INDEX, new WsDigitalInput(10));
		sensorInputs.put(LATCH_POSITION_SWITCH_INDEX, new WsDigitalInput(6));
		sensorInputs.put(CATAPULT_DOWN_SWITCH_INDEX, new WsDigitalInput(7));
		sensorInputs.put(FRONT_ARM_CALIBRATION_SWITCH_INDEX, new WsDigitalInput(8));
		sensorInputs.put(BACK_ARM_CALIBRATION_SWITCH_INDEX, new WsDigitalInput(9));

		oiInputs.put(UNKNOWN_INDEX, new NoInput());
		oiInputs.put(DRIVER_JOYSTICK_INDEX, new DriverJoystick());
		oiInputs.put(MANIPULATOR_JOYSTICK_INDEX, new ManipulatorJoystick());
		oiInputs.put(AUTO_PROGRAM_SELECTOR_INDEX, new WsDSAnalogInput(1));
		oiInputs.put(LOCK_IN_SWITCH_INDEX, new WsDSDigitalInput(1));
		oiInputs.put(START_POSITION_SELECTOR_INDEX, new WsDSAnalogInput(2));

	}
}
