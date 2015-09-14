package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.fw.auto.steps.AutonomousStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.IntakeWheels;

public class AutonomousStepIntakeEndAuto extends AutonomousStep {

	public void initialize() {
		((IntakeWheels) SubsystemManager.getInstance().getSubsystem(Robot.INTAKE_WHEELS)).endAuto();
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake in";
	}

}