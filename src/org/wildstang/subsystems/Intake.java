package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem implements IObserver {
	boolean intakeWheelsIn = false;
	boolean intakeWheelsOut = false;
	boolean intakePistonsOut = false;

	public Intake(String name) {
		super(name);
	}

	public void init() {
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_1);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_3);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_2);
	}

	public void update() {
		double intakeWheelsValue;
		boolean intakePistonsValue;

		if (intakeWheelsIn && !intakeWheelsOut) {
			intakeWheelsValue = 1;
		} else if (intakeWheelsOut && !intakeWheelsIn) {
			intakeWheelsValue = -1;
		} else {
			intakeWheelsValue = 0;
		}

		if (intakePistonsOut) {
			intakePistonsValue = true;
		} else {
			intakePistonsValue = false;
		}
		getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(intakeWheelsValue));
		getOutput(OutputManager.INTAKE_PISTONS_INDEX).set(new Boolean(intakePistonsValue));

		LogManager.getInstance().addObject("Intake Wheels", intakeWheelsValue);
		LogManager.getInstance().addObject("Intake Pistons", intakePistonsValue);
		SmartDashboard.putBoolean("Intake Wheels In", intakeWheelsIn);
		SmartDashboard.putBoolean("Intake Wheels Out", intakeWheelsOut);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_3) {
			intakeWheelsIn = ((BooleanSubject) subjectThatCaused).getValue();
		}
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_1) {
			intakeWheelsOut = ((BooleanSubject) subjectThatCaused).getValue();
		}
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_2) {
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				intakePistonsOut = !intakePistonsOut;
			}
		}
	}

	public void setPistons(boolean state) {
		intakePistonsOut = state;
	}

	public void setWheels(boolean in, boolean out) {
		if (in) {
			intakeWheelsIn = true;
			intakeWheelsOut = false;
		} else if (out) {
			intakeWheelsOut = true;
			intakeWheelsIn = false;
		} else {
			intakeWheelsOut = false;
			intakeWheelsIn = false;
		}
	}

}
