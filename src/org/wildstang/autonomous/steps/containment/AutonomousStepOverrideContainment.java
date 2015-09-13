package org.wildstang.autonomous.steps.containment;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.BinGrabber;
import org.wildstang.subsystems.Containment;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

public class AutonomousStepOverrideContainment extends AutonomousStep {

	
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
