package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.IntegerSubject;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

public class BackGrabber extends Subsystem implements IObserver {
	
	boolean backGrabberEngaged;
	int selectedHallEffectSensor = 600;
	int wantedHallEffectSensor = 1;
	
	public BackGrabber(String name) {
		super(name);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_8);
	}

	@Override
	public void init() {
		backGrabberEngaged = false;	
	}

	@Override
	public void update() {
		if(selectedHallEffectSensor == wantedHallEffectSensor && backGrabberEngaged)
		{
			getOutput(OutputManager.BACK_GRABBER_INDEX).set(new Boolean(true));
		}
		else if(!backGrabberEngaged)
		{
			getOutput(OutputManager.BACK_GRABBER_INDEX).set(new Boolean(false));
		}
		
		LogManager.getInstance().addObject("Back Grabber", backGrabberEngaged);
	}
	
	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_8) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			if (buttonValue) {
				backGrabberEngaged = !backGrabberEngaged;
			}
		}
		if (subjectThatCaused.equals(getSensorInput(InputManager.HALL_EFFECT_INDEX).getSubject())) {
			// Update from the arduino for the hall effect
			selectedHallEffectSensor = ((IntegerSubject) subjectThatCaused).getValue();
		}
	}
}
