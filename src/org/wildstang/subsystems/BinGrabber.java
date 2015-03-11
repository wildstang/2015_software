package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BinGrabber extends Subsystem implements IObserver {
	boolean deployed;
	boolean open;

	public BinGrabber(String name) {
		super(name);
		
		registerForJoystickButtonNotification(JoystickButtonEnum.DRIVER_BUTTON_1);
		registerForJoystickButtonNotification(JoystickButtonEnum.DRIVER_BUTTON_2);

	}

	@Override
	public void init() {
		deployed = false;
		open = false;
	}

	@Override
	public void update() {
		boolean deployedVal;
		boolean openVal;

		if (deployed) {
			deployedVal = true;
			if (open) {
				openVal = true;
			} else {
				openVal = false;
			}
		} else {
			deployedVal = false;
			openVal = false;
		}

		//getOutput(OutputManager.BIN_GRABBER_DEPLOY_INDEX).set(new Boolean(deployedVal));
		//getOutput(OutputManager.BIN_GRABBER_INDEX).set(new Boolean(openVal));

		SmartDashboard.putBoolean("Bin Grabber Deployed", deployed);
		SmartDashboard.putBoolean("Bin Grabber", open);
		LogManager.getInstance().addObject("Bin Grabber Deployed", deployed);
		LogManager.getInstance().addObject("Bin Grabber", open);
	}

	public void setDeployed(boolean newVal) {
		deployed = newVal;
	}

	public void setOpen(boolean newVal) {
		open = newVal;
	}
	
	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.DRIVER_BUTTON_1) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			// If button was just pressed, toggle the state
			if (buttonValue) {
				deployed = !deployed;
			}
		} else if (subjectThatCaused.getType() == JoystickButtonEnum.DRIVER_BUTTON_2) {
			boolean buttonValue = ((BooleanSubject) subjectThatCaused).getValue();
			// If button was just pressed, toggle the state
			if (buttonValue) {
				open = !open;
			}
		}
	}

}
