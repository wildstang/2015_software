package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToteAlignment extends Subsystem implements IObserver
{
	boolean pressed;
	
	public ToteAlignment(String name)
	{
		super(name);
	}

	@Override
	public void init()
	{
		pressed = false;
	}

	@Override
	public void update()
	{
		OutputManager.getInstance().getOutput(OutputManager.TOTE_ALIGNMENT_INDEX).set(new Boolean(pressed));
		
		LogManager.getInstance().addObject("Tote Alignment", pressed);
		SmartDashboard.putBoolean("Tote Alignment", pressed);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused)
	{
		if(subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_5)
		{
			pressed = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}
}
