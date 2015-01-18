package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subsystems.base.Subsystem;

public class Lift extends Subsystem
{	
	public Lift(String name)
	{
		super(name);
	}
	
	public void init()
	{
		OutputManager.getInstance().getOutput(OutputManager.WINCH_INDEX).set(new Double(0.0));
		OutputManager.getInstance().getOutput(OutputManager.WINCH_INDEX).update();
	}
	
	public void update()
	{
		double speed = ((Double) ((InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX))).get(JoystickAxisEnum.DRIVER_THROTTLE)).doubleValue();
		OutputManager.getInstance().getOutput(OutputManager.WINCH_INDEX).set(new Double(speed));
	}

}
