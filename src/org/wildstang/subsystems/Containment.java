package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
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
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_11);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_12);
	}

	public void update() {
		int containmentVal;
		int flapVal;
		
		if (containmentOpen) {
			containmentVal = DoubleSolenoid.Value.kForward_val;
		} else {
			containmentVal = DoubleSolenoid.Value.kReverse_val;
		}
		
		if (flapOpen) {
			flapVal = DoubleSolenoid.Value.kForward_val;
		} else {
			flapVal = DoubleSolenoid.Value.kReverse_val;
		}
		
		getOutput(OutputManager.CONTAINMENT_PISTON_INDEX).set(new Integer(containmentVal));
		getOutput(OutputManager.CONTAINMENT_FLAP_PISTON_INDEX).set(new Integer(flapVal));
		SmartDashboard.putBoolean("Containment", containmentOpen);
		SmartDashboard.putBoolean("Containment Flap", flapOpen);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_11) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			// If button was just pressed, toggle the state
			if (buttonValue) {
				containmentOpen = !containmentOpen;
			}
		} else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_12) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			// If button was jsut pressed, toggle the state
			if (buttonValue) {
				flapOpen = !flapOpen;
			}
		}
	}
}
