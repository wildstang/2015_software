/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.fw.auto.steps.AutonomousStep;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

/**
 *
 * @author Nathan
 */
public class AutonomousStepWaitForDriveMotionProfile extends AutonomousStep {

	public AutonomousStepWaitForDriveMotionProfile() {
	}

	public void initialize() {
	}

	public void update() {
		double distanceRemaining = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getDistanceRemaining();
		double velocity = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getVelocity();
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
