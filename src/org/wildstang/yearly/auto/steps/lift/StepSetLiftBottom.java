package org.wildstang.yearly.auto.steps.lift;

import org.wildstang.fw.auto.steps.AutoStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.Lift;

public class StepSetLiftBottom extends AutoStep {

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