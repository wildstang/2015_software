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
		int intakePistonsValue;
		if (intakeWheelsOn) {
			intakeWheelsValue = 1;
			intakePistonsValue = DoubleSolenoid.Value.kReverse_val;
		} else {
			intakeWheelsValue = 0;
			intakePistonsValue = DoubleSolenoid.Value.kForward_val;
		}
		getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(intakeWheelsValue));
		getOutput(OutputManager.INTAKE_PISTONS_INDEX).set(new Integer(intakePistonsValue));

		LogManager.getInstance().addObject("Intake Wheels", intakeWheelsValue);
		LogManager.getInstance().addObject("Intake Pistons", intakePistonsValue);
		SmartDashboard.putBoolean("Intake Wheels", intakeWheelsOn);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_3) {
			intakeWheelsOn = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}

}
