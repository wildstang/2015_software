package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hooks extends Subsystem implements IObserver
{
	boolean currentState;
	
	public Hooks(String name)
	{
		super(name);
	}

	public void init()
	{
		currentState = false;
		// Open Hooks Button (I think these are trigger buttons)
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_6);
		// Close Hooks Button
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_4);
	}
	
	public void update()
	{
        int wingsValue = 0;
        if(currentState == true){
            wingsValue = DoubleSolenoid.Value.kReverse_val;
        }
        else {
            wingsValue = DoubleSolenoid.Value.kForward_val;
        }
        (getOutput(OutputManager.HOOKS_SOLENOID_INDEX)).set(new Integer(wingsValue));
        SmartDashboard.putBoolean("Hook State", currentState);
	}
	
	@Override
	public void acceptNotification(Subject subjectThatCaused)
	{
		if(subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_4)
		{
			currentState = false;
		}
		else if(subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_6)
		{
			currentState = true;
		}
	}
	
	public void setHooksState(boolean state)
	{
		currentState = state;
	}

}
