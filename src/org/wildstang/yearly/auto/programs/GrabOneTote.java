package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepQuickTurn;
import org.wildstang.yearly.auto.steps.drivebase.StepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepWaitForDriveMotionProfile;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftMiddle;

public class GrabOneTote extends AutonomousProgram {

	protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 175.0);

	@Override
	protected void defineSteps() {
		AutonomousSerialStepGroup drive = new AutonomousSerialStepGroup("Drive");
		drive.addStep(new StepStartDriveUsingMotionProfile(5, 1.0));
		drive.addStep(new StepWaitForDriveMotionProfile());
		drive.addStep(new StepStopDriveUsingMotionProfile());

		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(new StepSetLiftMiddle());
		pickup.addStep(new StepSetLiftBottom());
		pickup.addStep(new StepSetLiftMiddle());
		drive.addStep(pickup);

		AutonomousSerialStepGroup score = new AutonomousSerialStepGroup("Score Totes");
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