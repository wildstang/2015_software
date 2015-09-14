package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.AutoSerialStepGroup;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftMiddle;

public class PickupTote extends AutoProgram {

	@Override
	protected void defineSteps() {
		AutoSerialStepGroup pickup = new AutoSerialStepGroup("Pickup");
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
