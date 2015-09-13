package org.wildstang.autonomous.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

public class AutonomousStepSetIntakeIn extends AutonomousStep {

	public void initialize() {
		((IntakeWheels) SubsystemManager.getInstance().getSubsystem(Robot.INTAKE_WHEELS)).setWheels(-1.0);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake in";
	}

}