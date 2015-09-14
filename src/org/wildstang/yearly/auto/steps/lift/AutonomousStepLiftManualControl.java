package org.wildstang.yearly.auto.steps.lift;

import org.wildstang.fw.auto.steps.AutonomousStep;
import org.wildstang.fw.inputmanager.InputManager;
import org.wildstang.fw.inputs.joystick.JoystickAxisEnum;
import org.wildstang.fw.inputs.joystick.ManipulatorJoystick;
import org.wildstang.fw.subject.DoubleSubject;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.Lift;

public class AutonomousStepLiftManualControl extends AutonomousStep {
	
	private double speed;
	private long startTime;
	private long time;
	private DoubleSubject liftJoystickSubject;
	
	public AutonomousStepLiftManualControl(double speed, long time) {
		this.speed = speed;
		this.time = time;
	}

	public void initialize() {
		liftJoystickSubject = (DoubleSubject) ((ManipulatorJoystick) InputManager.getInstance().getOiInput(Robot.MANIPULATOR_JOYSTICK)).getSubject(JoystickAxisEnum.MANIPULATOR_LIFT);
		liftJoystickSubject.setValue(new Double(speed));
		startTime = System.currentTimeMillis();
	}

	public void update() {
		if(System.currentTimeMillis() - startTime > time) {
			// stop lift
			liftJoystickSubject.setValue(new Double(0.0));
			finished = true;
		}
	}

	public String toString() {
		return "Lift Manual Control";
	}

}
