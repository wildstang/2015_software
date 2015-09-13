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
public class AutonomousStepStartDriveUsingMotionProfileAndHeading extends AutonomousStep {

	double distance;
	double goal_velocity;
	double heading;

	public AutonomousStepStartDriveUsingMotionProfileAndHeading(double distance, double goal_velocity, double heading) {
		this.distance = distance;
		this.goal_velocity = goal_velocity;
		this.heading = heading;
	}

	public void initialize() {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).startMoveWithHeadingAndMotionProfile(distance, goal_velocity, heading);
		finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Start the drive using motion profile for " + distance + " inches and reach going " + goal_velocity + " inches/second at a heading of " + heading;
	}
}
