package org.wildstang.yearly.auto.steps.lift;

import org.wildstang.fw.auto.steps.AutonomousStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.BinGrabber;
import org.wildstang.yearly.subsystems.Containment;

public class StepOverrideContainment extends AutonomousStep {

	
	public void initialize() {
		((Containment) SubsystemManager.getInstance().getSubsystem(Robot.TOP_CONTAINMENT)).override();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Overriding containment";
	}
}
