package org.wildstang.yearly.pid.inputs;

import org.wildstang.fw.pid.input.IPidInput;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

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
