package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.fw.auto.steps.AutoStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.BinGrabber;
import org.wildstang.yearly.subsystems.IntakeWheels;

public class StepDeployBinGrabbers extends AutoStep {

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