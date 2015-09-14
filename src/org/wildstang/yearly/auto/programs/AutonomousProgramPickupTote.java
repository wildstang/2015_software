package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftMiddle;

public class AutonomousProgramPickupTote extends AutonomousProgram {

	@Override
	protected void defineSteps() {
		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(new AutonomousStepSetLiftMiddle());
		pickup.addStep(new AutonomousStepSetLiftBottom());
		pickup.addStep(new AutonomousStepSetLiftMiddle());

		addStep(pickup);
	}

	@Override
	public String toString() {
		return "Pickup Tote";
	}

}
