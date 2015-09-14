package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepWaitForDriveMotionProfile;

public class Drive extends AutonomousProgram {
	protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 140.0);

	protected void defineSteps() {

		AutonomousSerialStepGroup drive = new AutonomousSerialStepGroup("Drive");
		drive.addStep(new StepStartDriveUsingMotionProfile(DISTANCE_CONFIG.getValue(), 1.0));
		drive.addStep(new StepWaitForDriveMotionProfile());
		drive.addStep(new StepStopDriveUsingMotionProfile());

		addStep(drive);
	}

	public String toString() {
		return "Drive";
	}
}