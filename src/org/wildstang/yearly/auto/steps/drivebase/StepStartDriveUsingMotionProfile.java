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
 * @author Nathan
 */
public class StepStartDriveUsingMotionProfile extends AutoStep {

	double distance;
	double goal_velocity;

	public StepStartDriveUsingMotionProfile(double distance, double goal_velocity) {
		this.distance = distance;
		this.goal_velocity = goal_velocity;
	}

	public void initialize() {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).startStraightMoveWithMotionProfile(distance, goal_velocity);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Start the drive using motion profile for " + distance + " inches and reach going " + goal_velocity + " inches/second ";
	}
}
