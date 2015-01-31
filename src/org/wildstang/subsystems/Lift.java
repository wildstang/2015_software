package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

public class Lift extends Subsystem implements IObserver
{	
	boolean atBottom;
	boolean atTop;
	
	public Lift(String name)
	{
		super(name);
		atBottom = false;
		atTop = false;
		//temp limit switch will probably be hall effect sensor
		registerForSensorNotification(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX);
	}
	
	public void init()
	{
		getOutput(OutputManager.WINCH_INDEX).set(new Double(0.0));
		getOutput(OutputManager.WINCH_INDEX).update();
	}
	
	public void update()
	{
		double speed = ((Double) (getJoystickValue(false, JoystickAxisEnum.MANIPULATOR_DPAD_Y))).doubleValue();
		if(!atBottom && !atTop)
		{
			getOutput(OutputManager.WINCH_INDEX).set(new Double(speed));
		}
		else
		{
			getOutput(OutputManager.WINCH_INDEX).set(new Double(0.0));
		}
		getOutput(OutputManager.WINCH_INDEX).update();
		
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused)
	{
		if(subjectThatCaused.equals(InputManager.getInstance().getSensorInput(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX).getSubject()))
		{
            atBottom = ((BooleanSubject) subjectThatCaused).getValue();
		}
		if(subjectThatCaused.equals(InputManager.getInstance().getSensorInput(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX).getSubject()))
		{
            atTop = ((BooleanSubject) subjectThatCaused).getValue();
		}
	}

}
