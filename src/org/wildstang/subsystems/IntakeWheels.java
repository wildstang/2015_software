package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.DoubleSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeWheels extends Subsystem implements IObserver {
	double intakeWheelsValue = 0.0;
	boolean intakePistonsOut = false;

	public IntakeWheels(String name) {
		super(name);
	}

	public void init() {
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_5);
	}

	public void update() {
		intakeWheelsValue = -((Double) (getJoystickValue(JoystickAxisEnum.MANIPULATOR_RIGHT_JOYSTICK_Y))).doubleValue();

		getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(intakeWheelsValue));
		getOutput(OutputManager.INTAKE_PISTONS_INDEX).set(new Boolean(intakePistonsOut));

		LogManager.getInstance().addObject("Intake Wheels", intakeWheelsValue);
		LogManager.getInstance().addObject("Intake Pistons", intakePistonsOut);
		SmartDashboard.putNumber("Intake Wheels Speed", intakeWheelsValue);
		SmartDashboard.putBoolean("Intake Pistons Out", intakePistonsOut);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {

		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_5) {
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				intakePistonsOut = !intakePistonsOut;
			}
		}
	}

	public void setPistons(boolean state) {
		intakePistonsOut = state;
	}

	public void setWheels(boolean in, boolean out) {
		if (in && !out) {
			intakeWheelsValue = 1;
		} else if (out && !in) {
			intakeWheelsValue = -1;
		} else {
			intakeWheelsValue = 0;
		}
	}

}
