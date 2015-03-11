package org.wildstang.autonomous.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Intake;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetIntakeIn extends AutonomousStep {

	public void initialize() {
		((Intake) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.HOOKS_SOLENOID_INDEX)).setWheels(true, false);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake in";
	}

}