package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

public class BackGrabber extends Subsystem implements IObserver {
	
	boolean backGrabberEngaged;
	
	public BackGrabber(String name) {
		super(name);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_8);
	}
	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_8) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			if (buttonValue) {
				backGrabberEngaged = !backGrabberEngaged;
			}
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	backGrabberEngaged = false;	
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		getOutput(OutputManager.BACK_GRABBER_INDEX).set(new Boolean(backGrabberEngaged));
		
		
	}

}
