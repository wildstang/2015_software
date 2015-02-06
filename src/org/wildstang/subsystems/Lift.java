package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

public class Lift extends Subsystem implements IObserver {
	boolean atBottom;
	boolean atTop;

	public Lift(String name) {
		super(name);

		registerForSensorNotification(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX);
		registerForSensorNotification(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX);
	}

	public void init() {
		atBottom = false;
		atTop = false;
	}

	public void update() {
		double winchJoystickValue = ((Double) (getJoystickValue(JoystickAxisEnum.MANIPULATOR_DPAD_Y))).doubleValue();
		double winchMotorSpeed = 0;
		if ((atBottom && winchJoystickValue < 0) || (atTop && winchJoystickValue > 0)) {
			// Prevent driving past the top/bottom of the lift
			winchMotorSpeed = 0;
		} else {
			winchMotorSpeed = winchJoystickValue;
		}
		getOutput(OutputManager.LIFT_A_INDEX).set(new Double(winchMotorSpeed));
		getOutput(OutputManager.LIFT_B_INDEX).set(new Double(winchMotorSpeed));

	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.equals(getSensorInput(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX).getSubject())) {
			atBottom = ((BooleanSubject) subjectThatCaused).getValue();
		} else if (subjectThatCaused.equals(getSensorInput(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX).getSubject())) {
			atTop = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}

}
