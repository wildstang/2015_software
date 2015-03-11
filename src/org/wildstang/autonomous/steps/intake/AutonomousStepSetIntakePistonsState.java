package org.wildstang.autonomous.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Intake;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetIntakePistonsState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetIntakePistonsState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((Intake) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.INTAKE_WHEELS_INDEX)).setPistons(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake pistons state " + (open ? "Open" : "Closed");
	}

}