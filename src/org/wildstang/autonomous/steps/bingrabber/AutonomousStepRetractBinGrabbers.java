package org.wildstang.autonomous.steps.bingrabber;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.BinGrabber;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

public class AutonomousStepRetractBinGrabbers extends AutonomousStep {

	public void initialize() {
		((BinGrabber) SubsystemManager.getInstance().getSubsystem(Robot.BIN_GRABBER)).retractBinGrabbers();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Retract bin grabbers";
	}
}
