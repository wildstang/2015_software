package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepDriveDistanceAtSpeed extends AutonomousStep {
	
	private double distance;
	private double speed;
	
	private DriveBase driveBase;
	
	public AutonomousStepDriveDistanceAtSpeed(double distanceInInches, double speed) {
		this.distance = distanceInInches;
		this.speed = speed;
	}

	public void initialize() {
		driveBase = ((DriveBase) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.DRIVE_BASE_INDEX));
		driveBase.resetLeftEncoder();
		driveBase.overrideThrottleValue(speed);
	}

	public void update() {
		if(driveBase.getLeftDistance() > distance) {
			finished = true;
			driveBase.disableDriveOverride();
		}
	}

	public String toString() {
		return "Drive distance at speed";
	}

}
