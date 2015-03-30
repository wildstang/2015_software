package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;

public class AutonomousStepStrafe extends AutonomousStep {

	private double throttle;
	private boolean left, right;

	public AutonomousStepStrafe(double throttle, boolean left, boolean right) {
		this.throttle = throttle;
		this.left = left;
		this.right = right;
	}

	public void initialize() {
		if (left) {
			InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX).set(JoystickAxisEnum.DRIVER_STRAFE, -Math.abs(throttle));
		} else if (right) {
			InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX).set(JoystickAxisEnum.DRIVER_STRAFE, Math.abs(throttle));
		} else {
			InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX).set(JoystickAxisEnum.DRIVER_STRAFE, 0);
		}
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Strafing";
	}
}
