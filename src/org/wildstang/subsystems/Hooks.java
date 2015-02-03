package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hooks extends Subsystem implements IObserver {
	boolean hooksEnabled;

	public Hooks(String name) {
		super(name);
	}

	public void init() {
		hooksEnabled = false;
		// Open Hooks Button (I think these are trigger buttons)
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_6);
		// Close Hooks Button
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_4);
	}

	public void update() {
		int hooksValue = 0;
		if (hooksEnabled == true) {
			hooksValue = DoubleSolenoid.Value.kReverse_val;
		} else {
			hooksValue = DoubleSolenoid.Value.kForward_val;
		}
		(getOutput(OutputManager.HOOKS_SOLENOID_INDEX)).set(new Integer(hooksValue));
		SmartDashboard.putBoolean("Hook State", hooksEnabled);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_4) {
			boolean buttonState = ((BooleanSubject) subjectThatCaused).getValue();
			if (buttonState) {
				hooksEnabled = false;
			}
		} else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_6) {
			boolean buttonState = ((BooleanSubject) subjectThatCaused).getValue();
			if (buttonState) {
				hooksEnabled = true;
			}
		}
	}

	public void setHooksState(boolean state) {
		hooksEnabled = state;
	}

}
