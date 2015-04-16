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
	boolean enabled;

	public BinGrabber(String name) {
		super(name);
	}

	@Override
	public void init() {
		enabled = false;
		registerForJoystickButtonNotification(JoystickButtonEnum.DRIVER_BUTTON_9);
	}

	@Override
	public void update() {
		int state;
		if (enabled) {
			state = DoubleSolenoid.Value.kReverse_val;
		} else {

			state = DoubleSolenoid.Value.kForward_val;
		}
		getOutput(OutputManager.BIN_GRABBER_INDEX).set(new Integer(state));
		SmartDashboard.putBoolean("Bin Grabber", enabled);
		LogManager.getInstance().addLog("Bin Grabber", enabled);
	}

	public void releaseBinGrabber() {
		enabled = true;
	}

	public void retractBinGrabber() {
		enabled = false;
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.getType() == JoystickButtonEnum.DRIVER_BUTTON_9) {
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				enabled = !enabled;
			}
		}
	}

}
