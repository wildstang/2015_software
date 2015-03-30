package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousParallelStepGroup;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepQuickTurn;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStrafe;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.autonomous.steps.intake.AutonomousStepIntakeEndAuto;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeIn;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeOff;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeOut;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakePistonsState;
import org.wildstang.autonomous.steps.intake.AutonomousStepSpinIntakeLeft;
import org.wildstang.autonomous.steps.intake.AutonomousStepSpinIntakeRight;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftMiddle;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftTop;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramThreeTotesStraight  extends AutonomousProgram
{
	protected final IntegerConfigFileParameter BETWEEN_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Between_Duration", 2750);
	protected final DoubleConfigFileParameter BETWEEN_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Between_Speed", .5);
	
	protected final IntegerConfigFileParameter SCORE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Score_Duration", 3000);
	protected final DoubleConfigFileParameter SCORE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Score_Speed", .75);

	protected final IntegerConfigFileParameter BACK_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Back_Duration", 500);
	protected final DoubleConfigFileParameter BACK_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Back_Speed", -.5);
	
	@Override
	protected void defineSteps()
	{
		//picks up first bin
		//should start where only the extensions touch the tote
		//sucks in tote all the way
		addStep(new AutonomousStepSetIntakeIn());
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(250));
		//stops spinning intake
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepSetIntakeOff());
		addStep(new AutonomousStepDelay(100));
		//lifts tote up
		addStep(new AutonomousStepDriveManual(-.75, 0));
		addStep(new AutonomousStepDelay(250));
		addStep(new AutonomousStepDriveManual(0, 0));
		addStep(new AutonomousStepSetLiftTop());
		addStep(new AutonomousStepDelay(2000));
		
		//picks up second bin
		//shifts into high gear and begins driving
		addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
		addStep(new AutonomousStepDriveManual(BETWEEN_SPEED.getValue(), 0));
		//closes intake starts spinning out
		addStep(new AutonomousStepSpinIntakeLeft());
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()/4));
		//opens intake (attempt to knock bins)
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()/4));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()/2));
		//stops driving closes intake and spins intake in
		addStep(new AutonomousStepSetIntakeIn());
		addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(500));
		//opens intake stops spinning
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepDelay(100));
		addStep(new AutonomousStepSetIntakeOff());
		//picks up tote
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDelay(1000));
		addStep(new AutonomousStepSetLiftTop());
		addStep(new AutonomousStepDelay(2000));

		//picks up second bin
		//shifts into high gear and begins driving
		addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
		addStep(new AutonomousStepDriveManual(BETWEEN_SPEED.getValue(), 0));
		//closes intake starts spinning out
		addStep(new AutonomousStepSpinIntakeRight());
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()/4));
		//opens intake (attempt to knock bins)
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()/4));
		addStep(new AutonomousStepDelay(BETWEEN_TIME.getValue()/2));
		//stops driving closes intake and spins intake in
		addStep(new AutonomousStepSetIntakeIn());
		addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(new AutonomousStepDelay(500));
		//opens intake stops spinning
		addStep(new AutonomousStepSetIntakePistonsState(false));
		addStep(new AutonomousStepDelay(100));
		addStep(new AutonomousStepSetIntakeOff());
		//picks up tote
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDelay(1000));
		//addStep(new AutonomousStepSetLiftTop());
		//addStep(new AutonomousStepDelay(2000));
		
		//scores tote
		//quick turn is secret way to enter dance mode (needs gyro)
		/*addStep(new AutonomousStepQuickTurn(90));*/
		//strafes over to scoring zone (doesn't work)
		addStep(new AutonomousStepStrafe(SCORE_SPEED.getValue(), false, true));
		addStep(new AutonomousStepDelay(SCORE_TIME.getValue()));
		addStep(new AutonomousStepStrafe(0.0, false, false));
		//drives to auto zone (needed if turing not strafing)
		/*addStep(new AutonomousStepDriveManual(SCORE_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(SCORE_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));*/
		//sets down totes (may be unneeded if we just drag totes)
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDelay(2000));
		//backs off of totes
		addStep(new AutonomousStepDriveManual(BACK_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(BACK_TIME.getValue()));
		addStep(new AutonomousStepDriveManual(0.0, 0));
		
		addStep(new AutonomousStepIntakeEndAuto());
	}

	@Override
	public String toString()
	{
		return "Drive straight through and pickup 3 totes";
	}

}
