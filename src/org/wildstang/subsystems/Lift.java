package org.wildstang.subsystems;

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
		getOutput(OutputManager.WINCH_INDEX).set(new Double(0.0));
		getOutput(OutputManager.WINCH_INDEX).update();
	}
	
	public void update()
	{
		double speed = ((Double) (getJoystickValue(false, JoystickAxisEnum.MANIPULATOR_DPAD_Y))).doubleValue();
		getOutput(OutputManager.WINCH_INDEX).set(new Double(speed));
	}

}
