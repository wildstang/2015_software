package org.wildstang.yearly.subsystems;

import org.wildstang.fw.inputmanager.InputManager;
import org.wildstang.fw.inputs.joystick.DriverJoystick;
import org.wildstang.fw.inputs.joystick.JoystickAxisEnum;
import org.wildstang.fw.inputs.joystick.JoystickButtonEnum;
import org.wildstang.fw.inputs.joystick.ManipulatorJoystick;
import org.wildstang.fw.logger.LogManager;
import org.wildstang.fw.subject.IObserver;
import org.wildstang.fw.subject.Subject;
import org.wildstang.fw.subsystemmanager.Subsystem;
import org.wildstang.yearly.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Monitor extends Subsystem implements IObserver {

	PowerDistributionPanel pdp;

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
			logManager.addLog("Current " + i, current);
		}

		double totalCurrent = pdp.getTotalCurrent();
		logManager.addLog("Total Current", totalCurrent);
		SmartDashboard.putNumber("Current", totalCurrent);

		double voltage = pdp.getVoltage();
		logManager.addLog("Voltage", voltage);
		SmartDashboard.putNumber("Voltage", voltage);

		double pdpTemp = pdp.getTemperature();
		logManager.addLog("Temperature", pdpTemp);
		SmartDashboard.putNumber("Temperature", pdpTemp);

		DriverJoystick driverJoystick = ((DriverJoystick) InputManager.getInstance().getOiInput(Robot.DRIVER_JOYSTICK));
		ManipulatorJoystick manipulatorJoystick = ((ManipulatorJoystick) InputManager.getInstance().getOiInput(Robot.MANIPULATOR_JOYSTICK));

		boolean isRobotEnabled = DriverStation.getInstance().isEnabled();
		boolean isRobotTeleop = DriverStation.getInstance().isOperatorControl();
		boolean isRobotAuton = DriverStation.getInstance().isAutonomous();
		
		// Log joystick values
		logManager.addLog(JoystickAxisEnum.DRIVER_THROTTLE.toString(), ((Double) driverJoystick.getSubject(JoystickAxisEnum.DRIVER_THROTTLE).getValueAsObject()));
		logManager.addLog(JoystickAxisEnum.DRIVER_HEADING.toString(), ((Double) driverJoystick.getSubject(JoystickAxisEnum.DRIVER_HEADING).getValueAsObject()));
		logManager.addLog(JoystickAxisEnum.DRIVER_STRAFE.toString(), ((Double) driverJoystick.getSubject(JoystickAxisEnum.DRIVER_STRAFE).getValueAsObject()));
		logManager.addLog(JoystickAxisEnum.MANIPULATOR_LIFT.toString(), ((Double) manipulatorJoystick.getSubject(JoystickAxisEnum.MANIPULATOR_LIFT).getValueAsObject()));
		logManager.addLog("Enabled", isRobotEnabled);
		logManager.addLog("Teleop", isRobotTeleop);
		logManager.addLog("Auto", isRobotAuton);
		
		// Log button presses
		for (int i = 0; i < 12; i++) {
			logManager.addLog("Driver Button " + (i + 1), ((Boolean) driverJoystick.getSubject(JoystickButtonEnum.getEnumFromIndex(true, i)).getValueAsObject()));
		}

		for (int i = 0; i < 12; i++) {
			logManager.addLog("Manipulator Button " + (i + 1), ((Boolean) manipulatorJoystick.getSubject(JoystickButtonEnum.getEnumFromIndex(false, i)).getValueAsObject()));
		}

		Runtime rt = Runtime.getRuntime();
		logManager.addLog("memory in use", new Double(rt.totalMemory() - rt.freeMemory()));
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// TODO Auto-generated method stub

	}

}
