/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.testprograms;

import org.wildstang.fw.auto.AutonomousManager;
import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStartDriveUsingMotionProfileAndHeading;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * @author Nathan
 */
/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
public class AutonomousProgramDriveDistanceMotionProfile extends AutonomousProgram {

	private DoubleConfigFileParameter distance;
	private DoubleConfigFileParameter heading;

	public void defineSteps() {
		distance = new DoubleConfigFileParameter(this.getClass().getName(), AutonomousManager.getInstance().getStartPosition().toConfigString() + ".distance", 10.0);
		heading = new DoubleConfigFileParameter(this.getClass().getName(), AutonomousManager.getInstance().getStartPosition().toConfigString() + ".heading", 0.0);
		addStep(new AutonomousStepSetShifter(true));
		addStep(new AutonomousStepStartDriveUsingMotionProfileAndHeading(distance.getValue(), 0.0, heading.getValue()));
		addStep(new AutonomousStepWaitForDriveMotionProfile());
		addStep(new AutonomousStepStopDriveUsingMotionProfile());

		// programSteps[3] = new AutonomousStepEnableDriveDistancePid();
		// programSteps[4] = new
		// AutonomousStepSetDriveDistancePidSetpoint(distance.getValue());
		// programSteps[5] = new AutonomousStepWaitForDriveDistancePid();
		// programSteps[6] = new
		// AutonomousStepStartDriveUsingMotionProfile(distance.getValue(), 0.0);
		// programSteps[7] = new AutonomousStepWaitForDriveMotionProfile();
		// programSteps[8] = new AutonomousStepStopDriveUsingMotionProfile();
	}

	public String toString() {
		return "TEST Motion profile distance";
	}
}
