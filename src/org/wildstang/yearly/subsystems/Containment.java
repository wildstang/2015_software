package org.wildstang.yearly.subsystems;

import org.wildstang.fw.inputs.joystick.JoystickButtonEnum;
import org.wildstang.fw.logger.LogManager;
import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.subject.BooleanSubject;
import org.wildstang.fw.subject.IObserver;
import org.wildstang.fw.subject.Subject;
import org.wildstang.fw.subsystemmanager.Subsystem;
import org.wildstang.yearly.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Containment extends Subsystem implements IObserver {

	boolean engaged;
	boolean manipulatorRequestedToggle;
	boolean newStateRequested;
	boolean requestedState;
	boolean overrideEnabled;

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

		getOutput(Robot.TOP_CONTAINMENT).set(new Boolean(engaged));
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
