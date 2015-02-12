package org.wildstang.autonomous.steps.lift;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Lift;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetPawlState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetPawlState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((Lift) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.LIFT_INDEX)).setPawl(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set pawl state " + (open ? "Open" : "Closed");
	}

}