package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.BinGrabber;
import org.wildstang.yearly.subsystems.IntakeWheels;

public class AutonomousStepDeployBinGrabbers extends AutonomousStep {

	public void initialize() {
		((BinGrabber) SubsystemManager.getInstance().getSubsystem(Robot.BIN_GRABBER)).deployBinGrabbers();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Release bin grabbers";
	}
}
