package org.wildstang.subsystems;

import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arms extends Subsystem
{
	boolean deployed;
	
	public Arms(String name)
	{
		super(name);
	}

	@Override
	public void init()
	{
		deployed = false;
	}

	@Override
	public void update()
	{
		int armValue;
		if(deployed)
		{
			armValue = DoubleSolenoid.Value.kReverse_val;
		}
		else
		{
			armValue = DoubleSolenoid.Value.kForward_val;
		}
		getOutput(OutputManager.ARMS_INDEX).set(new Integer(armValue));
	}
	
	public void setArms(boolean newVal)
	{
		deployed = newVal;
	}

}
