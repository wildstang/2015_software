package org.wildstang.autonomous.steps.bingrabber;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.BinGrabber;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetBinGrabberDeployedState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetBinGrabberDeployedState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((BinGrabber) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.BIN_GRABBER_INDEX)).setDeployed(open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set bin grabber deployed " + (open ? "Open" : "Closed");
	}

}