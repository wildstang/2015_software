package org.wildstang.yearly.auto.steps.intake;

import org.wildstang.fw.auto.steps.AutonomousStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.IntakeWheels;

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