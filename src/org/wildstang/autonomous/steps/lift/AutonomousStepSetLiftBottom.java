package org.wildstang.autonomous.steps.lift;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Lift;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

public class AutonomousStepSetLiftBottom extends AutonomousStep {

	public void initialize() {
		((Lift) SubsystemManager.getInstance().getSubsystem(Robot.LIFT)).setBottom();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Lift Down";
	}

}