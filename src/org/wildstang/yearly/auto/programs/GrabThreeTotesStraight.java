package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.control.AutoStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveManual;
import org.wildstang.yearly.auto.steps.drivebase.StepSetShifter;
import org.wildstang.yearly.auto.steps.drivebase.StepStrafe;
import org.wildstang.yearly.auto.steps.intake.StepIntakeEndAuto;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeIn;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeOff;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakePistonsState;
import org.wildstang.yearly.auto.steps.intake.StepSpinIntakeLeft;
import org.wildstang.yearly.auto.steps.intake.StepSpinIntakeRight;
import org.wildstang.yearly.auto.steps.lift.StepLiftManualControl;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftTop;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GrabThreeTotesStraight extends AutoProgram {
	protected final IntegerConfigFileParameter BETWEEN_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Between_Duration", 2750);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 0.5);
	protected final DoubleConfigFileParameter DRIVE_SPEED_LOW = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed_low", 0.5);
	
	protected final IntegerConfigFileParameter TIME_TO_SECOND_TOTE = new IntegerConfigFileParameter(this.getClass().getName(), "time_to_second_tote", 3000);
	protected final IntegerConfigFileParameter TIME_TO_THIRD_TOTE = new IntegerConfigFileParameter(this.getClass().getName(), "time_to_third_tote", 2500);
	
	protected final IntegerConfigFileParameter INTAKE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Intake_Duration", 800);

	protected final IntegerConfigFileParameter SCORE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Score_Duration", 3000);
	protected final DoubleConfigFileParameter SCORE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Score_Speed", 0.75);

	protected final IntegerConfigFileParameter BACK_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Back_Duration", 1000);
	protected final DoubleConfigFileParameter BACKUP_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Back_Speed", -0.5);

	protected final DoubleConfigFileParameter TOTES_DISTANCE_A = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_A", 35);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_B = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_B", 40);
	protected final DoubleConfigFileParameter BACKUP_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "Backup_Distance", 42);
	@Override
	protected void defineSteps() {
		// picks up first bin
		// should start where only the extensions touch the tote
		// sucks in tote all the way
		addStep(new StepSetIntakeIn());
		addStep(new StepSetIntakePistonsState(true));
		addStep(new AutoStepDelay(500));
		// stops spinning intake and opens
		addStep(new StepSetIntakePistonsState(false));
		addStep(new StepSetIntakeOff());
		addStep(new AutoStepDelay(100));
		// lifts tote up
		addStep(new StepSetLiftTop());
		addStep(new AutoStepDelay(2000));

		// picks up second bin
		// shifts into high gear and begins driving
		addStep(new StepSetShifter(true));
		// closes intake starts spinning left
		addStep(new StepSpinIntakeLeft());
		addStep(new StepSetIntakePistonsState(true));
		addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue(), false));
		////addStep(new AutonomousStepDriveManual(DRIVE_SPEED.getValue(), 0));
		// opens intake (attempt to knock bins)
		addStep(new AutoStepDelay(250));
		addStep(new StepSetIntakePistonsState(false));
		addStep(new AutoStepDelay(500));
		addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue(), false));
		// stops driving closes intake and spins intake in
		addStep(new StepSetIntakeIn());
		////addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new StepSetIntakePistonsState(true));
		addStep(new AutoStepDelay(INTAKE_TIME.getValue()));
		// opens intake stops spinning
		addStep(new StepSetIntakePistonsState(false));
		addStep(new AutoStepDelay(250));
		addStep(new StepSetIntakeOff());
		// picks up tote
		addStep(new StepSetLiftBottom());
		addStep(new AutoStepDelay(1750));
		// WHY ISN'T THIS WORKING
		addStep(new StepSetLiftTop());
		addStep(new AutoStepDelay(2000));

		// picks up third tote
		// closes intake starts spinning left
		addStep(new StepSpinIntakeLeft());
		addStep(new StepSetIntakePistonsState(true));
		addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue(), false));
		////addStep(new AutonomousStepDriveManual(DRIVE_SPEED.getValue(), 0));
		// opens intake (attempt to knock bins)
		addStep(new AutoStepDelay(250));
		addStep(new StepSetIntakePistonsState(false));
		addStep(new AutoStepDelay(500));
		addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue(), false));
		// stops driving closes intake and spins intake in
		addStep(new StepSetIntakeIn());
		////addStep(new AutonomousStepDriveManual(0.0, 0));
		addStep(new StepSetIntakePistonsState(true));
		addStep(new AutoStepDelay(INTAKE_TIME.getValue()));
		// opens intake stops spinning
		addStep(new StepSetIntakePistonsState(false));
		addStep(new AutoStepDelay(250));
		addStep(new StepSetIntakeOff());
		// picks up tote
		addStep(new StepSetLiftBottom());
		addStep(new AutoStepDelay(1500));
		addStep(new StepLiftManualControl(0.2, 700));

		// scores tote
		// strafes over to scoring zone (doesn't work)
		addStep(new StepStrafe(SCORE_SPEED.getValue()));
		addStep(new AutoStepDelay(SCORE_TIME.getValue()));
		addStep(new StepStrafe(0.0));
		// sets down totes (may be unneeded, just dragging totes)
		addStep(new StepSetLiftBottom());
		addStep(new AutoStepDelay(2000));
		// backs off of totes
		addStep(new StepDriveDistanceAtSpeed(BACKUP_DISTANCE.getValue(), BACKUP_SPEED.getValue(), false));
		////addStep(new AutonomousStepDriveManual(BACK_SPEED.getValue(), 0));
		////addStep(new AutonomousStepDelay(BACKUP_TIME.getValue()));
		////addStep(new AutonomousStepDriveManual(0.0, 0));

		addStep(new StepIntakeEndAuto());
	}

	@Override
	public String toString() {
		return "Drive straight through and pickup 3 totes";
	}

}
