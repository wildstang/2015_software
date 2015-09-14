/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.fw.auto.steps.AutoStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

/**
 *
 * @author coder65535
 */
public class StepDriveManual extends AutoStep {

	private double throttle, heading;
	public static final double KEEP_PREVIOUS_STATE = 2.0;

	public StepDriveManual(double throttle, double heading) {
		this.throttle = throttle;
		this.heading = heading;
	}

	public void initialize() {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideThrottleValue(throttle);
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).overrideHeadingValue(heading);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set throttle to " + throttle + " and heading to " + heading;
	}
}
