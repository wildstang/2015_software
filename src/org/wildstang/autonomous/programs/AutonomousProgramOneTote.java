package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepQuickTurn;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftMiddle;
import org.wildstang.config.DoubleConfigFileParameter;

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