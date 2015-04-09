package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

public class BinGrabber extends Subsystem implements IObserver
{
	boolean enabled;
	
	public BinGrabber(String name)
	{
		super(name);
	}

	@Override
	public void init()
	{
		enabled = false;
		registerForJoystickButtonNotification(JoystickButtonEnum.DRIVER_BUTTON_4);
	}

	@Override
	public void update()
	{
		getOutput(OutputManager.BIN_GRABBER_INDEX).set(new Boolean(enabled));
	}
	
	public void releaseBinGrabber()
	{
		enabled = true;
	}
	
	public void retractBinGrabber()
	{
		enabled = false;
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused)
	{
		if (subjectThatCaused.getType() == JoystickButtonEnum.DRIVER_BUTTON_4)
		{
			enabled = !enabled;
		}
	}

}
