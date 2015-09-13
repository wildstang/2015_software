package org.wildstang.autonomous.steps.intake;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.IntakeWheels;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

public class AutonomousStepSetIntakePistonsState extends AutonomousStep {
	protected boolean open;

	public AutonomousStepSetIntakePistonsState(boolean open) {
		this.open = open;
	}

	public void initialize() {
		((IntakeWheels) SubsystemManager.getInstance().getSubsystem(Robot.INTAKE_WHEELS)).setPistons(!open);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set intake pistons state " + (open ? "Open" : "Closed");
	}

}