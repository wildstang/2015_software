package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoystickTest extends Subsystem {

	public JoystickTest() {
		super("JoystickTest");
	}

	@Override
	public void init() {

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
	}

}
