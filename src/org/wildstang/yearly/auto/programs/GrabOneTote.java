package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.AutoSerialStepGroup;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepQuickTurn;
import org.wildstang.yearly.auto.steps.drivebase.StepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepWaitForDriveMotionProfile;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftMiddle;

public class GrabOneTote extends AutoProgram {

	protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 175.0);

	@Override
	protected void defineSteps() {
		AutoSerialStepGroup drive = new AutoSerialStepGroup("Drive");
		drive.addStep(new StepStartDriveUsingMotionProfile(5, 1.0));
		drive.addStep(new StepWaitForDriveMotionProfile());
		drive.addStep(new StepStopDriveUsingMotionProfile());

		AutoSerialStepGroup pickup = new AutoSerialStepGroup("Pickup");
		pickup.addStep(new StepSetLiftMiddle());
		pickup.addStep(new StepSetLiftBottom());
		pickup.addStep(new StepSetLiftMiddle());
		drive.addStep(pickup);

		AutoSerialStepGroup score = new AutoSerialStepGroup("Score Totes");
		score.addStep(new StepQuickTurn(45));
		score.addStep(new StepStartDriveUsingMotionProfile(15, 1.0));
		score.addStep(new StepWaitForDriveMotionProfile());
		score.addStep(new StepStopDriveUsingMotionProfile());
		score.addStep(new StepSetLiftBottom());
		score.addStep(new StepStartDriveUsingMotionProfile(5, -.5));
		score.addStep(new StepWaitForDriveMotionProfile());
		score.addStep(new StepStopDriveUsingMotionProfile());

		addStep(drive);
		addStep(score);
	}

	@Override
	public String toString() {
		return "Pickup Tote";
	}

}