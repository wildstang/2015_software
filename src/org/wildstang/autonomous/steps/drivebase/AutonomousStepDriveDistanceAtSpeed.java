package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepDriveDistanceAtSpeed extends AutonomousStep {

	private static final long MILLIS_TO_REVERSE = 200;

	private double distance;
	private double speed;
	private boolean hasReachedTarget = false;
	private long timeWhenTargetReached;
	private boolean shouldHardStop;

	private DriveBase driveBase;

	public AutonomousStepDriveDistanceAtSpeed(double distanceInInches,
			double speed, boolean shouldHardStop) {
		this.distance = distanceInInches;
		this.speed = Math.abs(speed);
		this.shouldHardStop = shouldHardStop;
	}

	public void initialize() {
		driveBase = ((DriveBase) SubsystemContainer.getInstance().getSubsystem(
				SubsystemContainer.DRIVE_BASE_INDEX));
		driveBase.resetLeftEncoder();
		if (distance < 0) {
			speed = -speed;
		}
		driveBase.overrideThrottleValue(speed);
		hasReachedTarget = false;
	}

	public void update() {
		if (!hasReachedTarget) {
			if (Math.abs(driveBase.getLeftDistance()) > Math.abs(distance)) {
				hasReachedTarget = true;
				timeWhenTargetReached = System.currentTimeMillis();
			}
		}

		if (hasReachedTarget && shouldHardStop) {
			if (System.currentTimeMillis() < timeWhenTargetReached
					+ MILLIS_TO_REVERSE) {
				driveBase.overrideThrottleValue(-speed);
			} else {
				driveBase.disableDriveOverride();
				finished = true;
			}
		} else if (hasReachedTarget) {
			driveBase.disableDriveOverride();
			finished = true;
		}
	}

	public String toString() {
		return "Drive distance at speed";
	}

}
