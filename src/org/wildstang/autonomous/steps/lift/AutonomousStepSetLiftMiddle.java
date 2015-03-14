package org.wildstang.autonomous.steps.lift;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Lift;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetLiftMiddle extends AutonomousStep {

	public void initialize() {
		((Lift) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.LIFT_INDEX)).setFour();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Lift to Four";
	}

}