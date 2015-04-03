package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Containment extends Subsystem implements IObserver {

	boolean containmentEngaged;
	boolean containmentToggleRequested;
	boolean containmentOverride;

	public Containment(String name) {
		super(name);
		// Toggle containment
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_6);
		// Containment override
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_8);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		containmentEngaged = false;
		containmentOverride = false;
		containmentToggleRequested = false;
	}

	@Override
	public void update() {
		// If override is pressed and a toggle was requested, toggle the containment state
		if(containmentOverride && containmentToggleRequested) {
			containmentEngaged = !containmentEngaged;
		}
		containmentToggleRequested = false;
		// Closed Bin Grips means open piston
		getOutput(OutputManager.TOP_CONTAINMENT_INDEX).set(new Boolean(containmentEngaged));
		SmartDashboard.putBoolean("Containment Engaged", containmentEngaged);
	}
	
	public void setContainmentEngaged(boolean engaged) {
		this.containmentEngaged = engaged;
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_6) {
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				containmentToggleRequested = true;
			}
		} else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_8) {
			containmentOverride = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}
}