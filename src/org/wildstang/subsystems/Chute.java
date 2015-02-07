package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

public class Chute extends Subsystem implements IObserver {

	boolean chuteIntake;

	public Chute(String name) {
		super(name);
	}

	public void init() {
		chuteIntake = false;

		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_7);
	}

	public void update() {

		double chuteMotorValue;
		if (chuteIntake) {
			chuteMotorValue = 1;
		} else {
			chuteMotorValue = 0;
		}
		getOutput(OutputManager.CHUTE_INDEX).set(new Double(chuteMotorValue));
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_7) {
			chuteIntake = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}
}