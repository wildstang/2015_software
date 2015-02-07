package org.wildstang.subsystems;

import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BinGrabber extends Subsystem
{
	boolean deployed;
	boolean open;
	
	public BinGrabber(String name)
	{
		super(name);
	}

	@Override
	public void init()
	{
		deployed = false;
		open = false;
	}

	@Override
	public void update()
	{
		int deployedVal;
		int openVal;
		
		if(deployed)
		{
			deployedVal = DoubleSolenoid.Value.kReverse_val;
			if(open)
			{
				openVal = DoubleSolenoid.Value.kReverse_val;
			}
			else
			{
				openVal = DoubleSolenoid.Value.kForward_val;
			}
		}
		else
		{
			deployedVal = DoubleSolenoid.Value.kForward_val;
			openVal = DoubleSolenoid.Value.kForward_val;
		}

		getOutput(OutputManager.BIN_GRABBER_DELPOY_INDEX).set(new Integer(deployedVal));
		getOutput(OutputManager.BIN_GRABBER_INDEX).set(new Integer(openVal));

		SmartDashboard.putBoolean("Bin Grabber Deployed", deployed);
		SmartDashboard.putBoolean("Bin Grabber", open);
		LogManager.getInstance().addObject("Bin Grabber Deployed", deployed);
		LogManager.getInstance().addObject("Bin Grabber", open);
	}
	
	public void setDeployed(boolean newVal)
	{
		deployed = newVal;
	}
	
	public void setOpen(boolean newVal)
	{
		open = newVal;
	}

}
