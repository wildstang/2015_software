package org.wildstang.yearly.pid.inputs;

import org.wildstang.pid.input.IPidInput;
import org.wildstang.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class DriveBaseHeadingPidInput implements IPidInput {

	public DriveBaseHeadingPidInput() {
		// Nothing to do here
	}

	public double pidRead() {
		double gyro_angle;
		gyro_angle = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getGyroAngle();
		SmartDashboard.putNumber("Gyro angle: ", gyro_angle);
		return gyro_angle;
	}
}
