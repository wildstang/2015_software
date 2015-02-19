package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Containment extends Subsystem implements IObserver {
	boolean containmentOpen;
	boolean flapOpen;

	public Containment(String name) {
		super(name);
	}

	public void init() {
		containmentOpen = false;
		flapOpen = false;
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_5);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_6);
	}

	public void update() {
		boolean containmentVal;
		int flapVal;

		if (containmentOpen) {
			containmentVal = true;
		} else {
			containmentVal = false;
		}

		if (flapOpen) {
			flapVal = DoubleSolenoid.Value.kForward_val;
		} else {
			flapVal = DoubleSolenoid.Value.kReverse_val;
		}

		// TODO THIS DOESN:T WORK
		getOutput(OutputManager.CONTAINMENT_DOORS_INDEX).set(new Integer(flapVal));
		getOutput(OutputManager.CONTAINMENT_FRONT_PISTON_INDEX).set(new Boolean(containmentVal));
		SmartDashboard.putBoolean("Containment Front", containmentOpen);
		SmartDashboard.putBoolean("Containment Flaps", flapOpen);
		LogManager.getInstance().addObject("Containment Doors", containmentOpen);
		LogManager.getInstance().addObject("Containment Flaps", flapOpen);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_5) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			// If button was just pressed, toggle the state
			if (buttonValue) {
				containmentOpen = !containmentOpen;
			}
		} else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_6) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			// If button was just pressed, toggle the state
			if (buttonValue) {
				flapOpen = !flapOpen;
			}
		}
	}

	public void setContainmentState(boolean state) {
		containmentOpen = state;
	}

	public void setContainmentFlapState(boolean state) {
		flapOpen = state;
	}
}
