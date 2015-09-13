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
public class DriveBaseDistancePidInput implements IPidInput {

	public DriveBaseDistancePidInput() {
		// Nothing to do here
	}

	public double pidRead() {
		double right_encoder_value;
		right_encoder_value = ((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).getRightDistance();
		SmartDashboard.putNumber("Distance: ", right_encoder_value);
		return right_encoder_value;
	}
}
