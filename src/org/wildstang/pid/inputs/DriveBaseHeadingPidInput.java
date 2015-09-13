package org.wildstang.pid.inputs;

import org.wildstang.pid.inputs.base.IPidInput;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

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
