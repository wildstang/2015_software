package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousParallelStepGroup;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepQuickTurn;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStrafe;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeIn;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeOut;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakePistonsState;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftMiddle;

public class AutonomousProgramThreeTotesStraight  extends AutonomousProgram{

	@Override
	protected void defineSteps()
	{
		AutonomousSerialStepGroup driveUp = new AutonomousSerialStepGroup("Drive");
		driveUp.addStep(new AutonomousStepStartDriveUsingMotionProfile(5, 1.0));
		driveUp.addStep(new AutonomousStepWaitForDriveMotionProfile());
		driveUp.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(driveUp);
		pickup.addStep(new AutonomousStepSetLiftMiddle());
		pickup.addStep(new AutonomousStepSetLiftBottom());
		pickup.addStep(new AutonomousStepSetLiftMiddle());


		AutonomousSerialStepGroup driveToNext = new AutonomousSerialStepGroup("Drive To Next");
		driveToNext.addStep(new AutonomousStepStartDriveUsingMotionProfile(10, 1.0));
		driveToNext.addStep(new AutonomousStepWaitForDriveMotionProfile());
		driveToNext.addStep(new AutonomousStepStopDriveUsingMotionProfile());
		
		AutonomousParallelStepGroup driveThrough = new AutonomousParallelStepGroup("Drive Through");
		driveThrough.addStep(driveToNext);
		driveThrough.addStep(new AutonomousStepSetIntakeIn());
		driveThrough.addStep(new AutonomousStepSetIntakePistonsState(true));
		
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
		addStep(driveThrough);
		addStep(pickup);
		addStep(driveThrough);
		addStep(pickup);
		addStep(score);
	}

	@Override
	public String toString()
	{
		return "Drive straight through and pickup totes";
	}

}
