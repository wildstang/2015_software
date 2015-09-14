package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepQuickTurn;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftMiddle;

public class AutonomousProgramOneTote extends AutonomousProgram {

	protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 175.0);

	@Override
	protected void defineSteps() {
		AutonomousSerialStepGroup drive = new AutonomousSerialStepGroup("Drive");
		drive.addStep(new AutonomousStepStartDriveUsingMotionProfile(5, 1.0));
		drive.addStep(new AutonomousStepWaitForDriveMotionProfile());
		drive.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(new AutonomousStepSetLiftMiddle());
		pickup.addStep(new AutonomousStepSetLiftBottom());
		pickup.addStep(new AutonomousStepSetLiftMiddle());
		drive.addStep(pickup);

		AutonomousSerialStepGroup score = new AutonomousSerialStepGroup("Score Totes");
		score.addStep(new AutonomousStepQuickTurn(45));
		score.addStep(new AutonomousStepStartDriveUsingMotionProfile(15, 1.0));
		score.addStep(new AutonomousStepWaitForDriveMotionProfile());
		score.addStep(new AutonomousStepStopDriveUsingMotionProfile());
		score.addStep(new AutonomousStepSetLiftBottom());
		score.addStep(new AutonomousStepStartDriveUsingMotionProfile(5, -.5));
		score.addStep(new AutonomousStepWaitForDriveMotionProfile());
		score.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		addStep(drive);
		addStep(score);
	}

	@Override
	public String toString() {
		return "Pickup Tote";
	}

}