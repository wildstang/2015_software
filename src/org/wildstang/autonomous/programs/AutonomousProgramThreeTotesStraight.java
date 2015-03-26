package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousParallelStepGroup;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepQuickTurn;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStrafe;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeIn;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeOff;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeOut;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakePistonsState;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftMiddle;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

public class AutonomousProgramThreeTotesStraight  extends AutonomousProgram
{
	protected final IntegerConfigFileParameter INIT_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Init_Duration", 500);
	protected final DoubleConfigFileParameter INIT_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Init_Speed", .5);

	protected final IntegerConfigFileParameter BETWEEN_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Between_Duration", 1500);
	protected final DoubleConfigFileParameter BETWEEN_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Between_Speed", .5);
	
	protected final IntegerConfigFileParameter SCORE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Score_Duration", 2000);
	protected final DoubleConfigFileParameter SCORE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Score_Speed", .5);

	protected final IntegerConfigFileParameter BACK_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Back_Duration", 250);
	protected final DoubleConfigFileParameter BACK_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Back_Speed", .5);
	
	/*
	 * Order of steps:
	 * raise lift/
	 * open intake/
	 * spin intake in/
	 * drive to first tote/
	 * close intake/
	 * stop intake spin/
	 * open intake/
	 * lower lift/
	 * raise lift mid/
	 * 
	 * drive to next tote/
	 * open intake/
	 * spin intake in/
	 * drive to tote/
	 * close intake/
	 * stop intake spin/
	 * open intake/
	 * lower lift/
	 * raise lift mid/
	 * 
	 * repeat last section/
	 * 
	 * turn right ~90* /
	 * drive into auto zone/
	 * lower lift/
	 * drive backward a little/
	 */
	@Override
	protected void defineSteps()
	{
		addStep(new AutonomousStepSetLiftMiddle());
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepSetIntakeIn());
		addStep(new AutonomousStepDriveManual(INIT_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(INIT_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(500));
		addStep(new AutonomousStepSetIntakeOff());
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepSetLiftMiddle());

		addStep(new AutonomousStepDriveManual(BETWEEN_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(500));
		addStep(new AutonomousStepSetIntakeOff());
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepSetLiftMiddle());

		addStep(new AutonomousStepDriveManual(BETWEEN_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(500));
		addStep(new AutonomousStepSetIntakeOff());
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepSetLiftMiddle());
		
		//quick turn is secret way to enter dance mode
		//addStep(new AutonomousStepQuickTurn(90));
		addStep(new AutonomousStepDriveManual(SCORE_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(SCORE_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDriveManual(BACK_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(BACK_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));
	}

	@Override
	public String toString()
	{
		return "Drive straight through and pickup 3 totes";
	}

}
