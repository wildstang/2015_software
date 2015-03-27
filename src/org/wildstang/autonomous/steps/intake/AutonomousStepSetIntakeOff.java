package org.wildstang.autonomous.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetIntakeOff extends AutonomousStep {

	public void initialize() {
		((IntakeWheels) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.INTAKE_WHEELS_INDEX)).setWheels(0.0);
		((IntakeWheels) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.INTAKE_WHEELS_INDEX)).endAuto();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake off";
	}

}