package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousParallelStepGroup;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.fw.auto.steps.control.AutonomousStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepStrafe;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepIntakeEndAuto;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSetIntakeIn;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSetIntakeOff;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSetIntakeOut;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSetIntakePistonsState;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSpinIntakeLeft;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSpinIntakeRight;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepLiftManualControl;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepOverrideContainment;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftTop;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramThreeTotesParallel extends AutonomousProgram {
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
		AutonomousParallelStepGroup intakeTote1 = new AutonomousParallelStepGroup("Intake Tote 1");
		intakeTote1.addStep(new AutonomousStepSetIntakeIn());
		intakeTote1.addStep(new AutonomousStepSetIntakePistonsState(true));
		intakeTote1.addStep(new AutonomousStepDelay(INIT_INTAKE_TIME.getValue()));
		addStep(intakeTote1);
		
		// stops spinning intake, opens, and shifts into high gear
		AutonomousParallelStepGroup openIntakeTote1 = new AutonomousParallelStepGroup("Open and stop intake for Tote 1");
		openIntakeTote1.addStep(new AutonomousStepSetIntakePistonsState(false));
		openIntakeTote1.addStep(new AutonomousStepSetIntakeOff());
		openIntakeTote1.addStep(new AutonomousStepSetShifter(true));
		addStep(openIntakeTote1);
		
		// lifts tote up
		AutonomousParallelStepGroup raise1stTote = new AutonomousParallelStepGroup("Raise lift for Tote 1");
		raise1stTote.addStep(new AutonomousStepSetLiftTop());
		raise1stTote.addStep(new AutonomousStepDelay(RISE_TIME.getValue()));
		addStep(raise1stTote);

		//closes the intake, spins wheels left, and drives to opening point
		AutonomousParallelStepGroup kick1stBinAndDrive = new AutonomousParallelStepGroup("Drive, run intake left");
		kick1stBinAndDrive.addStep(new AutonomousStepSpinIntakeLeft());
		kick1stBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(true));
		kick1stBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue(), false));
		addStep(kick1stBinAndDrive);
		
		//opens intake and starts driving to next tote
		AutonomousParallelStepGroup push1stBinAndDrive = new AutonomousParallelStepGroup("Kick bin 1, keep driving");
		push1stBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(false));
		push1stBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue(), true));
		addStep(push1stBinAndDrive);
		
		//intake tote 2
		AutonomousParallelStepGroup intake2ndTote = new AutonomousParallelStepGroup("Intake Tote 2");
		intake2ndTote.addStep(new AutonomousStepSetIntakeIn());
		intake2ndTote.addStep(new AutonomousStepSetIntakePistonsState(true));
		intake2ndTote.addStep(new AutonomousStepDelay(INTAKE_TIME.getValue()));
		addStep(intake2ndTote);
		
		//opens intake and stops spinning
		AutonomousParallelStepGroup openIntakeTote2 = new AutonomousParallelStepGroup("Open and stop intake for Tote 2");
		openIntakeTote2.addStep(new AutonomousStepSetIntakePistonsState(false));
		openIntakeTote2.addStep(new AutonomousStepSetIntakeOff());
		addStep(openIntakeTote2);
		
		//lowers lift
		AutonomousParallelStepGroup grabTote2 = new AutonomousParallelStepGroup("Lowers lift under 2nd tote");
		grabTote2.addStep(new AutonomousStepSetLiftBottom());
		grabTote2.addStep(new AutonomousStepDelay(DROP_TIME.getValue()));
		addStep(grabTote2);

		//raises lift
		AutonomousParallelStepGroup raiseTote2 = new AutonomousParallelStepGroup("Raise 2nd tote");
		raiseTote2.addStep(new AutonomousStepSetLiftTop());
		raiseTote2.addStep(new AutonomousStepDelay(RISE_TIME.getValue()));
		addStep(raiseTote2);

		//closes intake, spins left, and starts driving to opening point
		AutonomousParallelStepGroup kick2ndBinAndDrive = new AutonomousParallelStepGroup("Kick 2nd Bin and Drive");
		kick2ndBinAndDrive.addStep(new AutonomousStepSpinIntakeLeft());
		kick2ndBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(true));
		kick2ndBinAndDrive.addStep(new AutonomousStepStrafe(COUNTER_SPEED.getValue()));
		kick2ndBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_A_2.getValue(), DRIVE_SPEED_LOW.getValue(), false));
		addStep(kick2ndBinAndDrive);
		
		//opens intake, starts driving to next tote, and counters the bin push with strafe
		AutonomousParallelStepGroup push2ndBinAndDrive = new AutonomousParallelStepGroup("Push 2nd bin and drive");
		push2ndBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(false));
		push2ndBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_B_2.getValue(), DRIVE_SPEED.getValue(), true));
		addStep(push2ndBinAndDrive);
		
		//stops strafing
		addStep(new AutonomousStepStrafe(0));
		
		//closes intake and spins in
		AutonomousParallelStepGroup intake3rdTote = new AutonomousParallelStepGroup("Intake 3rd Tote");
		intake3rdTote.addStep(new AutonomousStepSetIntakeIn());
		intake3rdTote.addStep(new AutonomousStepSetIntakePistonsState(true));
		intake3rdTote.addStep(new AutonomousStepDelay(INTAKE_TIME.getValue()));
		addStep(intake3rdTote);
		
		//turn and drive to auto zone
		AutonomousParallelStepGroup turnToZone = new AutonomousParallelStepGroup("Turn and lower lift");
		turnToZone.addStep(new AutonomousStepSetIntakeOff());
		turnToZone.addStep(new AutonomousStepDriveManual(0, .75));
		turnToZone.addStep(new AutonomousStepDelay(TURN_TIME.getValue()));
		AutonomousSerialStepGroup delay = new AutonomousSerialStepGroup("delay");
		delay.addStep(new AutonomousStepDelay(DROP_DELAY.getValue()));
		delay.addStep(new AutonomousStepSetLiftBottom());
		turnToZone.addStep(delay);
		addStep(turnToZone);

		AutonomousParallelStepGroup score = new AutonomousParallelStepGroup("score");
		score.addStep(new AutonomousStepOverrideContainment());
		score.addStep(new AutonomousStepSetIntakePistonsState(false));
		score.addStep(new AutonomousStepDriveManual(0, 0));
		score.addStep(new AutonomousStepDriveDistanceAtSpeed(ZONE_DISTANCE.getValue(), 1, true));
		addStep(score);
		
		//stop, close intake, and run wheels out so spit out the totes
		AutonomousParallelStepGroup finish = new AutonomousParallelStepGroup("Finshing");
		finish.addStep(new AutonomousStepSetIntakePistonsState(true));
		finish.addStep(new AutonomousStepSetIntakeOut());
		finish.addStep(new AutonomousStepDelay(1000));
		addStep(finish);
		
		//return to low gear
		addStep(new AutonomousStepSetShifter(true));
	}

	@Override
	public String toString() {
		return "Parallel 3 totes";
	}

}
