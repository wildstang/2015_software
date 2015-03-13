package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;

public class AutonomousStepStrafe extends AutonomousStep
{

	private double throttle;
	private boolean left, right;

	public AutonomousStepStrafe(double throttle, boolean left, boolean right)
	{
		this.throttle = throttle;
		this.left = left;
		this.right = right;
	}

	public void initialize()
	{
		finished = true;
		if(left)
		{
			InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX).set(JoystickAxisEnum.DRIVER_STRAFE, new Double(Math.max(Math.min(throttle, 1.0), -1.0)));
		}
		else if(right)
		{
			InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX).set(JoystickAxisEnum.DRIVER_STRAFE, new Double(Math.max(Math.min(throttle, 1.0), -1.0)));
		}
		else
		{
			InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX).set(JoystickAxisEnum.DRIVER_STRAFE, new Double(Math.max(Math.min(throttle, 1.0), -1.0)));
		}
	}

	public void update(){}

	public String toString()
	{
		return "Strafing";
	}
}

