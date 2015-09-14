package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.AutoParallelStepGroup;
import org.wildstang.fw.auto.steps.AutoSerialStepGroup;
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
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeOut;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakePistonsState;
import org.wildstang.yearly.auto.steps.intake.StepSpinIntakeLeft;
import org.wildstang.yearly.auto.steps.intake.StepSpinIntakeRight;
import org.wildstang.yearly.auto.steps.lift.StepLiftManualControl;
import org.wildstang.yearly.auto.steps.lift.StepOverrideContainment;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftTop;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GrabThreeTotesParallel extends AutoProgram {
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 0.5);
	protected final DoubleConfigFileParameter DRIVE_SPEED_LOW = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed_low", 0.5);

	protected final IntegerConfigFileParameter INTAKE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Intake_Duration", 800);
	protected final IntegerConfigFileParameter INIT_INTAKE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Init_Intake_Duration", 800);

	protected final IntegerConfigFileParameter TURN_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Turn_Duration", 0750);
	protected final DoubleConfigFileParameter ZONE_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "Zone_Distance", 50);

	protected final DoubleConfigFileParameter BACKUP_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Backup_Speed", -0.5);
	protected final DoubleConfigFileParameter BACKUP_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "Backup_Distance", 42);
	protected final DoubleConfigFileParameter BACKUP_COUNTER_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Backup_Counter_Speed", -0.4);
	
	protected final DoubleConfigFileParameter COUNTER_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Counter_Speed", 0.3);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_A = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_A", 35);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_B = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_B", 40);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_A_2 = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_A_2", 35);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_B_2 = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_B_2", 40);
	
	protected final IntegerConfigFileParameter RISE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Rise_Time", 2000);
	protected final IntegerConfigFileParameter DROP_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Drop_Time", 1500);
	protected final IntegerConfigFileParameter DROP_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "Drop_Delay", 250);
	
	@Override
	protected void defineSteps() {
		//closes the intake and spins in
		AutoParallelStepGroup intakeTote1 = new AutoParallelStepGroup("Intake Tote 1");
		intakeTote1.addStep(new StepSetIntakeIn());
		intakeTote1.addStep(new StepSetIntakePistonsState(true));
		intakeTote1.addStep(new AutoStepDelay(INIT_INTAKE_TIME.getValue()));
		addStep(intakeTote1);
		
		// stops spinning intake, opens, and shifts into high gear
		AutoParallelStepGroup openIntakeTote1 = new AutoParallelStepGroup("Open and stop intake for Tote 1");
		openIntakeTote1.addStep(new StepSetIntakePistonsState(false));
		openIntakeTote1.addStep(new StepSetIntakeOff());
		openIntakeTote1.addStep(new StepSetShifter(true));
		addStep(openIntakeTote1);
		
		// lifts tote up
		AutoParallelStepGroup raise1stTote = new AutoParallelStepGroup("Raise lift for Tote 1");
		raise1stTote.addStep(new StepSetLiftTop());
		raise1stTote.addStep(new AutoStepDelay(RISE_TIME.getValue()));
		addStep(raise1stTote);

		//closes the intake, spins wheels left, and drives to opening point
		AutoParallelStepGroup kick1stBinAndDrive = new AutoParallelStepGroup("Drive, run intake left");
		kick1stBinAndDrive.addStep(new StepSpinIntakeLeft());
		kick1stBinAndDrive.addStep(new StepSetIntakePistonsState(true));
		kick1stBinAndDrive.addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue(), false));
		addStep(kick1stBinAndDrive);
		
		//opens intake and starts driving to next tote
		AutoParallelStepGroup push1stBinAndDrive = new AutoParallelStepGroup("Kick bin 1, keep driving");
		push1stBinAndDrive.addStep(new StepSetIntakePistonsState(false));
		push1stBinAndDrive.addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue(), true));
		addStep(push1stBinAndDrive);
		
		//intake tote 2
		AutoParallelStepGroup intake2ndTote = new AutoParallelStepGroup("Intake Tote 2");
		intake2ndTote.addStep(new StepSetIntakeIn());
		intake2ndTote.addStep(new StepSetIntakePistonsState(true));
		intake2ndTote.addStep(new AutoStepDelay(INTAKE_TIME.getValue()));
		addStep(intake2ndTote);
		
		//opens intake and stops spinning
		AutoParallelStepGroup openIntakeTote2 = new AutoParallelStepGroup("Open and stop intake for Tote 2");
		openIntakeTote2.addStep(new StepSetIntakePistonsState(false));
		openIntakeTote2.addStep(new StepSetIntakeOff());
		addStep(openIntakeTote2);
		
		//lowers lift
		AutoParallelStepGroup grabTote2 = new AutoParallelStepGroup("Lowers lift under 2nd tote");
		grabTote2.addStep(new StepSetLiftBottom());
		grabTote2.addStep(new AutoStepDelay(DROP_TIME.getValue()));
		addStep(grabTote2);

		//raises lift
		AutoParallelStepGroup raiseTote2 = new AutoParallelStepGroup("Raise 2nd tote");
		raiseTote2.addStep(new StepSetLiftTop());
		raiseTote2.addStep(new AutoStepDelay(RISE_TIME.getValue()));
		addStep(raiseTote2);

		//closes intake, spins left, and starts driving to opening point
		AutoParallelStepGroup kick2ndBinAndDrive = new AutoParallelStepGroup("Kick 2nd Bin and Drive");
		kick2ndBinAndDrive.addStep(new StepSpinIntakeLeft());
		kick2ndBinAndDrive.addStep(new StepSetIntakePistonsState(true));
		kick2ndBinAndDrive.addStep(new StepStrafe(COUNTER_SPEED.getValue()));
		kick2ndBinAndDrive.addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_A_2.getValue(), DRIVE_SPEED_LOW.getValue(), false));
		addStep(kick2ndBinAndDrive);
		
		//opens intake, starts driving to next tote, and counters the bin push with strafe
		AutoParallelStepGroup push2ndBinAndDrive = new AutoParallelStepGroup("Push 2nd bin and drive");
		push2ndBinAndDrive.addStep(new StepSetIntakePistonsState(false));
		push2ndBinAndDrive.addStep(new StepDriveDistanceAtSpeed(TOTES_DISTANCE_B_2.getValue(), DRIVE_SPEED.getValue(), true));
		addStep(push2ndBinAndDrive);
		
		//stops strafing
		addStep(new StepStrafe(0));
		
		//closes intake and spins in
		AutoParallelStepGroup intake3rdTote = new AutoParallelStepGroup("Intake 3rd Tote");
		intake3rdTote.addStep(new StepSetIntakeIn());
		intake3rdTote.addStep(new StepSetIntakePistonsState(true));
		intake3rdTote.addStep(new AutoStepDelay(INTAKE_TIME.getValue()));
		addStep(intake3rdTote);
		
		//turn and drive to auto zone
		AutoParallelStepGroup turnToZone = new AutoParallelStepGroup("Turn and lower lift");
		turnToZone.addStep(new StepSetIntakeOff());
		turnToZone.addStep(new StepDriveManual(0, .75));
		turnToZone.addStep(new AutoStepDelay(TURN_TIME.getValue()));
		AutoSerialStepGroup delay = new AutoSerialStepGroup("delay");
		delay.addStep(new AutoStepDelay(DROP_DELAY.getValue()));
		delay.addStep(new StepSetLiftBottom());
		turnToZone.addStep(delay);
		addStep(turnToZone);

		AutoParallelStepGroup score = new AutoParallelStepGroup("score");
		score.addStep(new StepOverrideContainment());
		score.addStep(new StepSetIntakePistonsState(false));
		score.addStep(new StepDriveManual(0, 0));
		score.addStep(new StepDriveDistanceAtSpeed(ZONE_DISTANCE.getValue(), 1, true));
		addStep(score);
		
		//stop, close intake, and run wheels out so spit out the totes
		AutoParallelStepGroup finish = new AutoParallelStepGroup("Finshing");
		finish.addStep(new StepSetIntakePistonsState(true));
		finish.addStep(new StepSetIntakeOut());
		finish.addStep(new AutoStepDelay(1000));
		addStep(finish);
		
		//return to low gear
		addStep(new StepSetShifter(true));
	}

	@Override
	public String toString() {
		return "Parallel 3 totes";
	}

}
