package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftMiddle;

public class PickupTote extends AutonomousProgram {

	@Override
	protected void defineSteps() {
		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(new StepSetLiftMiddle());
		pickup.addStep(new StepSetLiftBottom());
		pickup.addStep(new StepSetLiftMiddle());

		addStep(pickup);
	}

	@Override
	public String toString() {
		return "Pickup Tote";
	}

}
