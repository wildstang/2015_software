package org.wildstang.fw.inputmanager;

import java.util.HashMap;
import java.util.Map;

import org.wildstang.fw.inputs.NoInput;
import org.wildstang.fw.inputs.WsAnalogInput;
import org.wildstang.fw.inputs.WsDigitalInput;
import org.wildstang.fw.inputs.WsHallEffectInput;
import org.wildstang.fw.inputs.WsLIDAR;
import org.wildstang.fw.inputs.driverstation.WsDSAnalogInput;
import org.wildstang.fw.inputs.driverstation.WsDSDigitalInput;
import org.wildstang.fw.inputs.joystick.DriverJoystick;
import org.wildstang.fw.inputs.joystick.JoystickButtonEnum;
import org.wildstang.fw.inputs.joystick.ManipulatorJoystick;
import org.wildstang.fw.logger.Logger;
import org.wildstang.fw.subject.IObserver;
import org.wildstang.fw.subject.Subject;
import org.wildstang.yearly.robot.Robot;

import edu.wpi.first.wpilibj.I2C.Port;

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
		for (Map.Entry<Integer, IInput> entry : sensorInputs.entrySet()) {
			IInput in = entry.getValue();
			if (in != null) {
				in.pullData();
				in.update();
			}
		}
	}

	/**
	 * Method to trigger updates of all the oi data input containers.
	 */
	public void updateOiData() {
		for (Map.Entry<Integer, IInput> entry : oiInputs.entrySet()) {
			IInput in = entry.getValue();
			if (in != null) {
				in.pullData();
				in.update();
			}
		}
	}

	public void updateOiDataAutonomous() {
		for (Map.Entry<Integer, IInput> entry : oiInputs.entrySet()) {
			IInput oiIn = entry.getValue();
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
		for (Map.Entry<Integer, IInput> entry : sensorInputs.entrySet()) {
			IInput in = entry.getValue();
			if (in != null) {
				in.notifyConfigChange();
			}
		}
		for (Map.Entry<Integer, IInput> entry : oiInputs.entrySet()) {
			IInput in = entry.getValue();
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
		return in == null ? (IInput) oiInputs.get(Robot.UNKNOWN_INDEX) : in;
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
		return in == null ? (IInput) sensorInputs.get(Robot.UNKNOWN_INDEX) : in;
	}

	final public void attachJoystickButton(IInputEnum button, IObserver observer) {
		if (button instanceof JoystickButtonEnum) {
			Subject subject = InputManager.getInstance().getOiInput(((JoystickButtonEnum) button).isDriver() ? Robot.DRIVER_JOYSTICK : Robot.MANIPULATOR_JOYSTICK)
					.getSubject(button);
			subject.attach(observer);
		} else {
			Logger.getLogger().debug(this.getClass().getName(), "attachJoystickButton", "Oops! Check that the inputs implement the required interfaces.");
		}
	}

	public void addSensorInput(int index, IInput input) {
		sensorInputs.put(index, input);
	}
	
	public void addOiInput(int index, IInput input) {
		oiInputs.put(index, input);
	}
	
	/**
	 * Constructor for the InputManager.
	 *
	 * Each new data element to be added to the facade must be added here and have keys added above.
	 */
	protected InputManager() {

	}
}
