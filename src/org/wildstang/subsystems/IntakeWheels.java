package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeWheels extends Subsystem implements IObserver {
	boolean intakeWheelsOn = false;

	public IntakeWheels(String name) {
		super(name);
	}

	public void init() {
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_3);
	}

	public void update() {
		double intakeWheelsValue;
		if (intakeWheelsOn) {
			intakeWheelsValue = 1;
		} else {
			intakeWheelsValue = 0;
		}
		getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(intakeWheelsValue));

		SmartDashboard.putBoolean("Intake Wheels", intakeWheelsOn);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_3) {
			intakeWheelsOn = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}

}
