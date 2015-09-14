package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;

public class AutonomousProgramDrive extends AutonomousProgram {
	protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 140.0);

	protected void defineSteps() {

		AutonomousSerialStepGroup drive = new AutonomousSerialStepGroup("Drive");
		drive.addStep(new AutonomousStepStartDriveUsingMotionProfile(DISTANCE_CONFIG.getValue(), 1.0));
		drive.addStep(new AutonomousStepWaitForDriveMotionProfile());
		drive.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		addStep(drive);
	}

	public String toString() {
		return "Drive";
	}
}