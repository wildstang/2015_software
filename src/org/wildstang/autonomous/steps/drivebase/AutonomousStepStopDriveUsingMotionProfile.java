/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.autonomous.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

/**
 *
 * @author Nathan
 */
public class AutonomousStepStopDriveUsingMotionProfile extends AutonomousStep {

	public AutonomousStepStopDriveUsingMotionProfile() {
	}

	public void initialize() {
	}

	public void update() {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).stopStraightMoveWithMotionProfile();
		finished = true;
	}

	public String toString() {
		return "Stop the drive using motion profile";
	}
}
