/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.pid.outputs;

import org.wildstang.pid.outputs.base.IPidOutput;
import org.wildstang.subsystems.DriveBase;
import org.wildstang.subsystems.base.SubsystemManager;
import org.wildstang.yearly.robot.Robot;

/**
 *
 * @author Nathan
 */
public class DriveBaseSpeedPidOutput implements IPidOutput {

	public DriveBaseSpeedPidOutput() {
		// Nothing to do here
	}

	public void pidWrite(double output) {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).setPidSpeedValue(output);
	}
}
