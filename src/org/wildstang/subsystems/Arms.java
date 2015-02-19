package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arms extends Subsystem implements IObserver {
	boolean deployed = false;

	public Arms(String name) {
		super(name);
		
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_4);
	}

	@Override
	public void init() {
		deployed = false;
	}

	@Override
	public void update() {
		boolean armValue;
		if (deployed) {
			armValue = true;
		} else {
			armValue = false;
		}
		getOutput(OutputManager.ARMS_INDEX).set(new Boolean(armValue));
	}

	public void setArms(boolean newVal) {
		deployed = newVal;
	}
	
	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if(subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_4) {
			if(((BooleanSubject) subjectThatCaused).getValue()) {
				deployed = !deployed;
			}
		}
	}

}
