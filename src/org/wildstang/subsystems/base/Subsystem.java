/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.subsystems.base;

import org.wildstang.inputmanager.base.IInput;
import org.wildstang.inputmanager.base.IInputEnum;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.logger.Logger;
import org.wildstang.outputmanager.base.IOutput;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.IObserver;

/**
 *
 * @author Nathan
 */
public class Subsystem {

	static String subSystemName;

	/**
	 * Constructor for a subsystem.
	 *
	 * @param name
	 *            The name of the subsystem.
	 */
	public Subsystem(String name) {
		subSystemName = name;
	}

	public void init() {
	}

	/**
	 * Gets the name of the subsystem.
	 *
	 * @return The name of the subsystem.
	 */
	public String getName() {
		return subSystemName;
	}

	/**
	 * Method to allow the subsystem to update.
	 *
	 * Must be overridden when extending the base class.
	 */
	public void update() {
		// Override when extending base class.
	}

	/**
	 * Method to notify the subsystem of a config change.
	 *
	 * Override this method when extending the base class, if config params are required.
	 */
	public void notifyConfigChange() {
		// Override when extending base class if config is needed.
	}

	public void registerForJoystickButtonNotification(IInputEnum button) {
		try {
			InputManager.getInstance().attachJoystickButton(button, (IObserver) this);
		} catch (ClassCastException e) {
			Logger.getLogger().debug(this.getClass().getName(), "registerForJoystickButtonNotification", "This class must implement IObserver!");
		}
	}

	public void registerForSensorNotification(int sensorIndex) {
		try {
			InputManager.getInstance().getSensorInput(sensorIndex).getSubject().attach((IObserver) this);
		} catch (Exception e) {
			Logger.getLogger().debug(this.getClass().getName(), "registerForSensorNotification", "This class must implement IObserver!");
		}
	}

	public Double getJoystickValue(boolean driver, IInputEnum key) {
		Double returnval;
		if (driver) {
			returnval = ((Double) ((InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX))).get(key)).doubleValue();
		} else {
			returnval = ((Double) ((InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX))).get(key)).doubleValue();
		}
		return returnval;
	}

	public IOutput getOutput(int index) {
		return OutputManager.getInstance().getOutput(index);
	}

	public IInput getSensorInput(int index) {
		return InputManager.getInstance().getSensorInput(index);
	}
}
