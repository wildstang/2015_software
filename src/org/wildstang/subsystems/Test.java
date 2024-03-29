package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Test extends Subsystem {

	BuiltInAccelerometer accelerometer;

	public Test() {
		super("Test");
	}

	@Override
	public void init() {
		accelerometer = new BuiltInAccelerometer();
	}

	@Override
	public void update() {
		int numAxes = 6;
		for (int i = 0; i < numAxes; i++) {
			JoystickAxisEnum joyEnum = new JoystickAxisEnum(false, i, "Axis" + i);
			double axisValue = ((Double) InputManager.getInstance().getOiInput(InputManager.MANIPULATOR_JOYSTICK_INDEX).get(joyEnum)).doubleValue();
			SmartDashboard.putNumber("JostickAxis" + i, axisValue);
		}

		SmartDashboard.putNumber("Accel X", accelerometer.getX());
		SmartDashboard.putNumber("Accel Y", accelerometer.getY());
		SmartDashboard.putNumber("Accel Z", accelerometer.getZ());

		LogManager.getInstance().addLog("Accel X", accelerometer.getX());
		LogManager.getInstance().addLog("Accel Y", accelerometer.getY());
		LogManager.getInstance().addLog("Accel Z", accelerometer.getZ());

		// int distance = ((Integer)
		// InputManager.getInstance().getSensorInput(InputManager.LIDAR_INDEX).get()).intValue();
		// SmartDashboard.putNumber("LIDAR distance", distance);
	}

}
