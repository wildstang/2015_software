package org.wildstang.autonomous.steps.arms;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Arms;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetArmsState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetArmsState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((Arms) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.ARMS_INDEX)).setArms(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set arms state " + (open ? "Open" : "Closed");
	}

}