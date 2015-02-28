package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.inputmanager.inputs.joystick.driver.DriverJoystick;
import org.wildstang.inputmanager.inputs.joystick.manipulator.ManipulatorJoystick;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Monitor extends Subsystem implements IObserver {

	PowerDistributionPanel pdp;

	/**
	 * @author Noah Allows the code to use the pdp.getCurrent and the input.getAverageVoltage command.
	 */

	public Monitor(String name) {
		super(name);
	}

	public void init() {
		pdp = new PowerDistributionPanel();
	}

	public void update() {

		LogManager logManager = LogManager.getInstance();
		for (int i = 0; i < 16; i++) {
			double current = pdp.getCurrent(i);
			logManager.addObject("Current " + i, current);
		}

		double totalCurrent = pdp.getTotalCurrent();
		logManager.addObject("Total Current", totalCurrent);
		SmartDashboard.putNumber("Current", totalCurrent);

		double voltage = pdp.getVoltage();
		logManager.addObject("Voltage", voltage);
		SmartDashboard.putNumber("Voltage", voltage);

		double pdpTemp = pdp.getTemperature();
		logManager.addObject("Temperature", pdpTemp);
		SmartDashboard.putNumber("Temperature", pdpTemp);

		DriverJoystick driverJoystick = ((DriverJoystick) InputManager.getInstance().getOiInput(InputManager.DRIVER_JOYSTICK_INDEX));
		// Log joystick values
		logManager.addObject(JoystickAxisEnum.DRIVER_THROTTLE.toString(), ((Double) driverJoystick.getSubject(JoystickAxisEnum.DRIVER_THROTTLE).getValueAsObject()));

		// Log button presses
		for (int i = 0; i < 12; i++) {
			logManager.addObject("Driver Button " + (i + 1), ((Boolean) driverJoystick.getSubject(JoystickButtonEnum.getEnumFromIndex(true, i)).getValueAsObject()));
		}

		ManipulatorJoystick manipulatorJoystick = ((ManipulatorJoystick) InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX));
		for (int i = 0; i < 12; i++) {
			logManager.addObject("Manipulator Button " + (i + 1), ((Boolean) manipulatorJoystick.getSubject(JoystickButtonEnum.getEnumFromIndex(false, i)).getValueAsObject()));
		}
		
		Runtime rt = Runtime.getRuntime();
		logManager.addObject("memory in use", new Double(rt.totalMemory() - rt.freeMemory()));
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub

	}

}
