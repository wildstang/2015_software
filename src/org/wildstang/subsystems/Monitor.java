package org.wildstang.subsystems;

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
		/*for (int i = 0; i < 16; i = i + 1)
		{
			double current = pdp.getCurrent(i);
			SmartDashboard.putDouble("PDP output #" + i, current);
		}*/
		
		double totalCurrent = pdp.getTotalCurrent();
		SmartDashboard.putDouble("Total Current", totalCurrent);
		
		double voltage = pdp.getVoltage();
		SmartDashboard.putDouble("Average Voltage", voltage);
		
		double pdpTemp = pdp.getTemperature();
		SmartDashboard.putDouble("Temperature", pdpTemp);
		
		
	}

}
