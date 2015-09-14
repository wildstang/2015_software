package org.wildstang.yearly.subsystems;

import org.wildstang.fw.inputs.joystick.JoystickButtonEnum;
import org.wildstang.fw.logger.LogManager;
import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.subject.BooleanSubject;
import org.wildstang.fw.subject.IObserver;
import org.wildstang.fw.subject.Subject;
import org.wildstang.fw.subsystemmanager.Subsystem;
import org.wildstang.yearly.robot.Robot;

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
		getOutput(Robot.BIN_GRABBER).set(new Integer(state));
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
