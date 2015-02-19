package org.wildstang.autonomous.steps.bingrabber;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.BinGrabber;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetBinGrabberState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetBinGrabberState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((BinGrabber) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.BIN_GRABBER_INDEX)).setOpen(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set bin grabber state " + (open ? "Open" : "Closed");
	}

}