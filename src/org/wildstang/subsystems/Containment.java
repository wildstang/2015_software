package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Containment extends Subsystem implements IObserver {

	boolean engaged = false;
	boolean manipulatorRequestedToggle = false;
	boolean newStateRequested = false;
	boolean requestedState = false;
	boolean overrideEnabled = false;

	public Containment(String name) {
		super(name);
		// Toggle containment
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_6);
		// Containment override
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_8);
	}

	@Override
	public void init() {
		engaged = false;
		manipulatorRequestedToggle = false;
		newStateRequested = false;
		requestedState = false;
		overrideEnabled = false;
	}

	@Override
	public void update() {
		if (overrideEnabled && manipulatorRequestedToggle) {
			// If override is pressed and a toggle was requested by the manipulator, toggle the containment state.
			engaged = !engaged;
		} else if (!overrideEnabled && newStateRequested) {
			// If override is not pressed and requestContainmentEngaged/Disengaged() was called, accept the requested
			// state.
			engaged = requestedState;
		}

		// Reset temporary variables
		manipulatorRequestedToggle = false;
		newStateRequested = false;

		getOutput(OutputManager.TOP_CONTAINMENT_INDEX).set(new Boolean(engaged));
		SmartDashboard.putBoolean("Containment Engaged", engaged);
		LogManager.getInstance().addLog("Containment Engaged", engaged);
	}

	/**
	 * Requests the containment to engage. Based on the state of the override button, this request may or may not be
	 * honored.
	 */
	public void requestContainmentEngaged() {
		this.newStateRequested = true;
		this.requestedState = true;
	}

	/**
	 * Requests the containment to disengage. Based on the state of the override button, this request may or may not be
	 * honored.
	 */
	public void requestContainmentDisengaged() {
		this.newStateRequested = true;
		this.requestedState = false;
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_6) {
			if (((BooleanSubject) subjectThatCaused).getValue() == true) {
				// Watch for a false-to-true transition to catch a button press
				manipulatorRequestedToggle = true;
			}
		} else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_8) {
			overrideEnabled = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}
	
	public void override()
	{
		manipulatorRequestedToggle = true;
		overrideEnabled = true;
	}
}