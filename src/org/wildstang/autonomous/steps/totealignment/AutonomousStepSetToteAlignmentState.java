package org.wildstang.autonomous.steps.totealignment;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.ToteAlignment;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetToteAlignmentState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetToteAlignmentState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((ToteAlignment) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.TOTE_ALIGNMENT_INDEX)).setAlignmentState(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set tote alignment state " + (open ? "Open" : "Closed");
	}

}