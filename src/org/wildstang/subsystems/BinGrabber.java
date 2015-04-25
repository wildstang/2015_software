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

public class BinGrabber extends Subsystem implements IObserver {
	boolean deployed;

	public BinGrabber(String name) {
		super(name);
	}

	@Override
	public void init() {
		deployed = false;
		// Select button toggles bin grabbers state
		registerForJoystickButtonNotification(JoystickButtonEnum.DRIVER_BUTTON_9);
	}

	@Override
	public void update() {
		int state;
		if (deployed) {
			state = DoubleSolenoid.Value.kReverse_val;
		} else {
			state = DoubleSolenoid.Value.kForward_val;
		}
		getOutput(OutputManager.BIN_GRABBER_INDEX).set(new Integer(state));
		SmartDashboard.putBoolean("Bin Grabber deployed", deployed);
		LogManager.getInstance().addLog("Bin Grabber deployed", deployed);
	}

	public void deployBinGrabbers() {
		deployed = true;
	}

	public void retractBinGrabbers() {
		deployed = false;
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.DRIVER_BUTTON_9) {
			// Toggle button
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				deployed = !deployed;
			}
		}
	}

}
