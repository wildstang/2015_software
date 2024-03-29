package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepWaitForDriveDistance extends AutonomousStep {

	public AutonomousStepWaitForDriveDistance() {
	}

	public void initialize() {
	}

	public void update() {
		double distanceRemaining = ((DriveBase) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.DRIVE_BASE_INDEX)).getDistanceRemaining();
		double velocity = ((DriveBase) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.DRIVE_BASE_INDEX)).getVelocity();
		if ((distanceRemaining < 0.01) && (distanceRemaining > -0.01)) {
			finished = true;
		}
		if ((distanceRemaining < 12.0) && (distanceRemaining > -12.0) && (velocity < 0.10) && (velocity > -0.10)) {
			finished = true;
		}
	}

	public String toString() {
		return "Wait for the motion profile to finish moving to target";
	}

}
