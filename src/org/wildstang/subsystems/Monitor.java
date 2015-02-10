package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.inputmanager.inputs.joystick.driver.DriverJoystick;
import org.wildstang.inputmanager.inputs.joystick.manipulator.ManipulatorJoystick;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Monitor extends Subsystem implements IObserver
{

	PowerDistributionPanel pdp;

	/**
	 * @author Noah Allows the code to use the pdp.getCurrent and the
	 *         input.getAverageVoltage command.
	 */

	public Monitor(String name) {
		super(name);
	}

	public void init() {
		pdp = new PowerDistributionPanel();
	}

	public void update() {
		for (int i = 0; i < 16; i++) {
			double current = pdp.getCurrent(i);
			LogManager.getInstance().addObject("Current " + i, current);
		}

		double totalCurrent = pdp.getTotalCurrent();
		LogManager.getInstance().addObject("Total Current", totalCurrent);
		SmartDashboard.putNumber("Current", totalCurrent);

		double voltage = pdp.getVoltage();
		LogManager.getInstance().addObject("Voltage", voltage);
		SmartDashboard.putNumber("Voltage", voltage);

		double pdpTemp = pdp.getTemperature();
		LogManager.getInstance().addObject("Temperature", pdpTemp);
		SmartDashboard.putNumber("Temperature", pdpTemp);

		for(int i = 0; i < 12; i++)
		{
			LogManager.getInstance().addObject("Driver Joystick Button " + (i + 1), ((Boolean) ((DriverJoystick) InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX)).getSubject(JoystickButtonEnum.getEnumFromIndex(true, i)).getValueAsObject()));
		}
		for(int i = 0; i < 12; i++)
		{
			LogManager.getInstance().addObject("Manipulator Joystick Button " + (i + 1), ((Boolean) ((ManipulatorJoystick) InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX)).getSubject(JoystickButtonEnum.getEnumFromIndex(true, i)).getValueAsObject()));
		}

	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub
		
	}

}
