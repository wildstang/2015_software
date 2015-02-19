package org.wildstang.autonomous.steps.containment;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Containment;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetContainmentState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetContainmentState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((Containment) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.HOOKS_SOLENOID_INDEX)).setContainmentState(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set containment " + (open ? "Open" : "Closed");
	}

}