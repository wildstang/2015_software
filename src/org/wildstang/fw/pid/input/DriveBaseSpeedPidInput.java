package org.wildstang.fw.pid.input;

import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

/**
 *
 * @author Nathan
 */
public class DriveBaseSpeedPidInput implements IPidInput {

	public DriveBaseSpeedPidInput() {
		// Nothing to do here
	}

	public double pidRead() {
		double /* left_encoder_value, */right_encoder_value, final_encoder_value;
		// left_encoder_value = ((DriveBase)
		// SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.DRIVE_BASE)).getLeftDistance();
		double currentVelocity = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getVelocity();
		return currentVelocity;
	}
}
