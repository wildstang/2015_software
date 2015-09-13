/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

/**
 *
 * @author Joey
 */
public class AutonomousStepQuickTurn extends AutonomousStep {

	private double value, angle;
	private boolean shouldFinish = false;

	public AutonomousStepQuickTurn(double relativeAngle) {
		this.value = relativeAngle;
	}

	public void initialize() {

		angle = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getGyroAngle() + value;
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).setThrottleValue(0);
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideHeadingValue(value < 0 ? 0.6 : -0.6);

		InputManager.getInstance().getOiInput(Robot.DRIVER_JOYSTICK).set(JoystickAxisEnum.DRIVER_THROTTLE, new Double(0.0));
		InputManager.getInstance().getOiInput(Robot.DRIVER_JOYSTICK).set(JoystickAxisEnum.DRIVER_HEADING, new Double(value < 0 ? 0.6 : -0.6));

	}

	public void update() {
		if (shouldFinish) {
			finished = true;
			((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideHeadingValue(0.0);
			return;
		}
		double gyroAngle = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getGyroAngle();
		if (value < 0) {
			if (angle > gyroAngle) {
				((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideHeadingValue(0.0);
				InputManager.getInstance().getOiInput(Robot.DRIVER_JOYSTICK).set(JoystickAxisEnum.DRIVER_HEADING, new Double(0.0));
				shouldFinish = true;
			}
		} else {
			if (angle < gyroAngle) {
				((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideHeadingValue(0.0);
				InputManager.getInstance().getOiInput(Robot.DRIVER_JOYSTICK).set(JoystickAxisEnum.DRIVER_HEADING, new Double(0.0));
				shouldFinish = true;
			}
		}
	}

	public String toString() {
		return "Turning using quickturn with a relative angle";
	}
}
