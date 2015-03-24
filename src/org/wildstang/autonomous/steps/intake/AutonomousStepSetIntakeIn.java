package org.wildstang.autonomous.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetIntakeIn extends AutonomousStep {

	public void initialize() {
		((IntakeWheels) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.INTAKE_WHEELS_INDEX)).setWheels(1.0);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake in";
	}

}