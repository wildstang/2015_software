package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.IntakeWheels;

public class AutonomousStepSpinIntakeLeft extends AutonomousStep {

	public void initialize() {
		((IntakeWheels) SubsystemManager.getInstance().getSubsystem(Robot.INTAKE_WHEELS)).setWheels(-1.0, 1.0);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Spin Intake Left";
	}

}