package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

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
