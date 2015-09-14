package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.fw.auto.steps.AutonomousStep;
import org.wildstang.fw.config.BooleanConfigFileParameter;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousStepDriveDistanceAtSpeed extends AutonomousStep {

	private final DoubleConfigFileParameter DRIFTING_COMPENSATION_FACTOR_CONFIG;
	private final BooleanConfigFileParameter USE_DRIFTING_COMPENSATION_FACTOR_CONFIG;

	private static final long MILLIS_TO_REVERSE = 200;

	private double distance;
	private double speed;
	private boolean hasReachedTarget = false;
	private long timeWhenTargetReached;
	private boolean shouldHardStop;

	private DriveBase driveBase;

	public AutonomousStepDriveDistanceAtSpeed(double distanceInInches, double speed, boolean shouldHardStop) {
		this.distance = distanceInInches;
		this.speed = Math.abs(speed);
		this.shouldHardStop = shouldHardStop;
		DRIFTING_COMPENSATION_FACTOR_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "drifting_compensation_factor", 0.05);
		USE_DRIFTING_COMPENSATION_FACTOR_CONFIG = new BooleanConfigFileParameter(this.getClass().getName(), "use_comp", false);
		SmartDashboard.putBoolean("Using comp factor", USE_DRIFTING_COMPENSATION_FACTOR_CONFIG.getValue());
	}

	public void initialize() {
		driveBase = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE));
		driveBase.resetLeftEncoder();
		driveBase.resetRightEncoder();
		if (distance < 0) {
			speed = -speed;
		}
		driveBase.overrideThrottleValue(speed);
		hasReachedTarget = false;
	}

	public void update() {
		double leftDistance = driveBase.getLeftDistance();
		double rightDistance = driveBase.getRightDistance();
		if (!hasReachedTarget) {
			if (Math.abs(leftDistance) > Math.abs(distance) || Math.abs(rightDistance) > Math.abs(distance)) {
				hasReachedTarget = true;
				timeWhenTargetReached = System.currentTimeMillis();
			} else {
				if (USE_DRIFTING_COMPENSATION_FACTOR_CONFIG.getValue()) {
					SmartDashboard.putBoolean("Using comp factor NOW", USE_DRIFTING_COMPENSATION_FACTOR_CONFIG.getValue());
					// Still need to reach target. Try to compensate for drifting by applying a heading.
					double distanceDifference = rightDistance - leftDistance;
					if (distance > 0) {
						// We're driving forward
						driveBase.overrideHeadingValue(distanceDifference * DRIFTING_COMPENSATION_FACTOR_CONFIG.getValue());
					} else {
						// We're driving backwards. Heading compensation is reversed.
						driveBase.overrideHeadingValue(distanceDifference * DRIFTING_COMPENSATION_FACTOR_CONFIG.getValue() * -1);
					}
				}
			}
		}

		if (hasReachedTarget && shouldHardStop) {
			if (System.currentTimeMillis() < timeWhenTargetReached + MILLIS_TO_REVERSE) {
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
