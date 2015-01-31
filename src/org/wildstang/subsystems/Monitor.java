package org.wildstang.subsystems;

import org.wildstang.logger.sender.LogManager;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Monitor extends Subsystem
{
	
	PowerDistributionPanel pdp;
	/**
	 * @author Noah
	 *  Allows the code to use the pdp.getCurrent and 
	 *  the input.getAverageVoltage command.
	 */
	
	public Monitor(String name)
	{
		super(name);
	}

	public void init ()
	{
		pdp = new PowerDistributionPanel();
	}
	
	public void update ()
	{
		for(int i = 0; i < 16; i++)
		{
			double current = pdp.getCurrent(i);
			LogManager.getInstance().getObject(i).updateObject(new Double(current));
		}
		
		double totalCurrent = pdp.getTotalCurrent();
		LogManager.getInstance().getObject(LogManager.CURRENT_INDEX).updateObject(new Double(totalCurrent));
		SmartDashboard.putNumber("Current", totalCurrent);
		
		double voltage = pdp.getVoltage();
		LogManager.getInstance().getObject(LogManager.VOLTAGE_INDEX).updateObject(new Double(voltage));
		SmartDashboard.putNumber("Voltage", voltage);
		
		double pdpTemp = pdp.getTemperature();
		LogManager.getInstance().getObject(LogManager.TEMPERATURE_INDEX).updateObject(new Double(pdpTemp));
		SmartDashboard.putNumber("Temperature", pdpTemp);
		
		
	}

}
