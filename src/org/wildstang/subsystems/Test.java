package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
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
			JoystickAxisEnum joyEnum = new JoystickAxisEnum(true, i, "Axis" + i);
			double axisValue = ((Double) InputManager.getInstance()
					.getOiInput(InputManager.DRIVER_JOYSTICK_INDEX)
					.get(joyEnum)).doubleValue();
			SmartDashboard.putNumber("JostickAxis" + i, axisValue);
		}
		
		SmartDashboard.putNumber("Accel X", accelerometer.getX());
		SmartDashboard.putNumber("Accel Y", accelerometer.getY());
		SmartDashboard.putNumber("Accel Z", accelerometer.getZ());
	}

}
