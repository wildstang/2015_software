package org.wildstang.yearly.auto.steps.lift;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.Lift;

public class AutonomousStepSetLiftTop extends AutonomousStep {

	public void initialize() {
		((Lift) SubsystemManager.getInstance().getSubsystem(Robot.LIFT)).setTop();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Lift Up";
	}

}
