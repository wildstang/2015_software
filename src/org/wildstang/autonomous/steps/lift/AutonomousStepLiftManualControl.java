package org.wildstang.autonomous.steps.lift;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.manipulator.ManipulatorJoystick;
import org.wildstang.subjects.base.DoubleSubject;
import org.wildstang.subsystems.Lift;
import org.wildstang.subsystems.base.SubsystemContainer;

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
		liftJoystickSubject = (DoubleSubject) ((ManipulatorJoystick) InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX)).getSubject(JoystickAxisEnum.MANIPULATOR_LIFT);
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
