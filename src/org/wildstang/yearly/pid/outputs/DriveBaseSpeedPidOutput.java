/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.pid.outputs;

import org.wildstang.fw.pid.output.IPidOutput;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

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
