/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.subsystems.base;

import org.wildstang.inputmanager.base.IInput;
import org.wildstang.inputmanager.base.IInputEnum;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.logger.Logger;
import org.wildstang.outputmanager.base.IOutput;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.IObserver;

/**
 *
 * @author Nathan
 */
public abstract class Subsystem {

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

	public abstract void init();

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
	public abstract void update();

	/**
	 * Method to notify the subsystem of a config change.
	 *
	 * Override this method when extending the base class, if config params are required.
	 */
	public void notifyConfigChange() {
		// Do nothing by default
	}

	protected void registerForJoystickButtonNotification(IInputEnum button) {
		try {
			InputManager.getInstance().attachJoystickButton(button, (IObserver) this);
		} catch (ClassCastException e) {
			Logger.getLogger().debug(this.getClass().getName(), "registerForJoystickButtonNotification", "This class must implement IObserver!");
		}
	}

	protected void registerForSensorNotification(int sensorIndex) {
		try {
			InputManager.getInstance().getSensorInput(sensorIndex).getSubject().attach((IObserver) this);
		} catch (Exception e) {
			Logger.getLogger().debug(this.getClass().getName(), "registerForSensorNotification", "This class must implement IObserver!");
		}
	}

	protected Double getJoystickValue(IInputEnum key) {
		if (!(key instanceof JoystickAxisEnum)) {
			throw new ClassCastException("Input enum must be an instance of JoystickAxisEnum!");
		}

		JoystickAxisEnum axis = (JoystickAxisEnum) key;
		if (axis.isDriver()) {
			return ((Double) ((InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX))).get(key)).doubleValue();
		} else {
			return ((Double) ((InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX))).get(key)).doubleValue();
		}
	}

	protected IOutput getOutput(int index) {
		return OutputManager.getInstance().getOutput(index);
	}

	protected IInput getSensorInput(int index) {
		return InputManager.getInstance().getSensorInput(index);
	}
}
