/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemContainer;

/**
 *
 * @author coder65535
 */
public class AutonomousStepDriveManual extends AutonomousStep {

	private double throttle, heading;
	public static final double KEEP_PREVIOUS_STATE = 2.0;

	public AutonomousStepDriveManual(double throttle, double heading) {
		this.throttle = throttle;
		this.heading = heading;
	}

	public void initialize() {
		((DriveBase) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.DRIVE_BASE_INDEX)).overrideThrottleValue(throttle);
		((DriveBase) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.DRIVE_BASE_INDEX)).overrideHeadingValue(heading);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set throttle to " + throttle + " and heading to " + heading;
	}
}
