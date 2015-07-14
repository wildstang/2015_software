package org.wildstang.autonomous.steps.bingrabber;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.BinGrabber;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepRetractBinGrabbers extends AutonomousStep {

	public void initialize() {
		((BinGrabber) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.BIN_GRABBER_INDEX)).retractBinGrabbers();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Retract bin grabbers";
	}
}
