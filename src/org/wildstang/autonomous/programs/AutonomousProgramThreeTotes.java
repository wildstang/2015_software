package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepQuickTurn;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStrafe;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftMiddle;
import org.wildstang.config.DoubleConfigFileParameter;

public class AutonomousProgramThreeTotes extends AutonomousProgram {

	protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 175.0);

	@Override
	protected void defineSteps() {
		AutonomousSerialStepGroup driveUp = new AutonomousSerialStepGroup("Drive");
		driveUp.addStep(new AutonomousStepStartDriveUsingMotionProfile(5, 1.0));
		driveUp.addStep(new AutonomousStepWaitForDriveMotionProfile());
		driveUp.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(driveUp);
		pickup.addStep(new AutonomousStepSetLiftMiddle());
		pickup.addStep(new AutonomousStepSetLiftBottom());
		pickup.addStep(new AutonomousStepSetLiftMiddle());

		AutonomousSerialStepGroup strafeLeft = new AutonomousSerialStepGroup("Strafe Left");
		strafeLeft.addStep(new AutonomousStepStrafe(1, true, false));

		AutonomousSerialStepGroup strafeRight = new AutonomousSerialStepGroup("Strafe Right");
		strafeRight.addStep(new AutonomousStepStrafe(1, false, true));

		AutonomousSerialStepGroup driveToNext = new AutonomousSerialStepGroup("Drive To Next");
		driveToNext.addStep(strafeRight);
		driveToNext.addStep(new AutonomousStepStartDriveUsingMotionProfile(10, 1.0));
		driveToNext.addStep(new AutonomousStepWaitForDriveMotionProfile());
		driveToNext.addStep(new AutonomousStepStopDriveUsingMotionProfile());
		driveToNext.addStep(strafeLeft);
		
		AutonomousSerialStepGroup score = new AutonomousSerialStepGroup("Score Totes");
		score.addStep(new AutonomousStepQuickTurn(45));
		score.addStep(new AutonomousStepStartDriveUsingMotionProfile(15, 1.0));
		score.addStep(new AutonomousStepWaitForDriveMotionProfile());
		score.addStep(new AutonomousStepStopDriveUsingMotionProfile());
		score.addStep(new AutonomousStepSetLiftBottom());
		score.addStep(new AutonomousStepStartDriveUsingMotionProfile(5, -.5));
		score.addStep(new AutonomousStepWaitForDriveMotionProfile());
		score.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		addStep(pickup);
		addStep(driveToNext);
		addStep(pickup);
		addStep(driveToNext);
		addStep(pickup);
		addStep(score);

	}

	@Override
	public String toString() {
		return "Pickup Three Totes";
	}

}