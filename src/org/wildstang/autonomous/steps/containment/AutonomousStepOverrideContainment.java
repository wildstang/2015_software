package org.wildstang.autonomous.steps.containment;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.BinGrabber;
import org.wildstang.subsystems.Containment;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepOverrideContainment extends AutonomousStep {

	boolean engaged;
	
	public AutonomousStepOverrideContainment(boolean engaged)
	{
		this.engaged = engaged;
	}
	
	public void initialize() {
		((Containment) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.TOP_CONTAINMENT_INDEX)).override(engaged);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Overriding containment";
	}
}
