package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

public class AutonomousStepStrafe extends AutonomousStep {

	private double strafe;

	// Left is negative, right is positive
	public AutonomousStepStrafe(double strafe) {
		this.strafe = strafe;
	}

	public void initialize() {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideStrafeValue(strafe);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Strafing";
	}
}
