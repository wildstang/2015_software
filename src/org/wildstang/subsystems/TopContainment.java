package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TopContainment extends Subsystem implements IObserver {

	boolean binGripsEngaged;

	public TopContainment(String name) {
		super(name);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_6);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		binGripsEngaged = false;
	}

	@Override
	public void update() {
		// Closed Bin Grips means open piston	
		getOutput(OutputManager.TOP_CONTAINMENT_INDEX).set(new Boolean(binGripsEngaged));
		SmartDashboard.putBoolean("Bin Grippers", binGripsEngaged);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub
		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_6) {
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				binGripsEngaged = !binGripsEngaged;
			}
		}
	}
}