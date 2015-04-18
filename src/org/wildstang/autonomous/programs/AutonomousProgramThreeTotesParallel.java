package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousParallelStepGroup;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveDistanceAtSpeed;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStrafe;
import org.wildstang.autonomous.steps.intake.AutonomousStepIntakeEndAuto;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeIn;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakeOff;
import org.wildstang.autonomous.steps.intake.AutonomousStepSetIntakePistonsState;
import org.wildstang.autonomous.steps.intake.AutonomousStepSpinIntakeLeft;
import org.wildstang.autonomous.steps.intake.AutonomousStepSpinIntakeRight;
import org.wildstang.autonomous.steps.lift.AutonomousStepLiftManualControl;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftTop;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramThreeTotesParallel extends AutonomousProgram {
	protected final IntegerConfigFileParameter BETWEEN_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Between_Duration", 2750);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 0.5);
	protected final DoubleConfigFileParameter DRIVE_SPEED_LOW = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed_low", 0.5);
	
	protected final IntegerConfigFileParameter TIME_TO_SECOND_TOTE = new IntegerConfigFileParameter(this.getClass().getName(), "time_to_second_tote", 3000);
	protected final IntegerConfigFileParameter TIME_TO_THIRD_TOTE = new IntegerConfigFileParameter(this.getClass().getName(), "time_to_third_tote", 2500);

	protected final IntegerConfigFileParameter INTAKE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Intake_Duration", 800);
	protected final IntegerConfigFileParameter INIT_INTAKE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Init_Intake_Duration", 800);

	protected final IntegerConfigFileParameter SCORE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Score_Duration", 3000);
	protected final IntegerConfigFileParameter TURN_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Turn_Duration", 0750);
	protected final DoubleConfigFileParameter SCORE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Score_Speed", 0.75);

	protected final DoubleConfigFileParameter BACKUP_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Back_Speed", -0.5);
	protected final DoubleConfigFileParameter BACKUP_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "Backup_Distance", 42);
	protected final DoubleConfigFileParameter BACKUP_COUNTER_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Backup_Counter_Speed", -0.4);
	
	protected final DoubleConfigFileParameter COUNTER_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Counter_Speed", 0.3);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_A = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_A", 35);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_B = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_B", 40);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_A_2 = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_A_2", 35);
	protected final DoubleConfigFileParameter TOTES_DISTANCE_B_2 = new DoubleConfigFileParameter(this.getClass().getName(), "Tote_Distance_B_2", 40);
	
	protected final IntegerConfigFileParameter RISE_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Rise_Time", 2000);
	protected final IntegerConfigFileParameter DROP_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "Drop_Time", 1500);
	
	@Override
	protected void defineSteps() {
		//closes the intake and spins in
		AutonomousParallelStepGroup intakeTote1 = new AutonomousParallelStepGroup("Intake First Tote");
		intakeTote1.addStep(new AutonomousStepSetIntakeIn());
		intakeTote1.addStep(new AutonomousStepSetIntakePistonsState(true));
		intakeTote1.addStep(new AutonomousStepDelay(INIT_INTAKE_TIME.getValue()));
		addStep(intakeTote1);
		
		// stops spinning intake, opens, and shifts into high gear
		AutonomousParallelStepGroup openIntakeTote1 = new AutonomousParallelStepGroup("Open and stop intake for Tote1");
		openIntakeTote1.addStep(new AutonomousStepSetIntakePistonsState(false));
		openIntakeTote1.addStep(new AutonomousStepSetIntakeOff());
		openIntakeTote1.addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kForward));
		addStep(openIntakeTote1);
		
		// lifts tote up
		AutonomousParallelStepGroup raise1stTote = new AutonomousParallelStepGroup("Rasing 1st tote");
		raise1stTote.addStep(new AutonomousStepSetLiftTop());
		raise1stTote.addStep(new AutonomousStepDelay(RISE_TIME.getValue()));
		addStep(raise1stTote);

		//closes the intake, spins wheels left, and drives to opening point
		AutonomousParallelStepGroup kick1stBinAndDrive = new AutonomousParallelStepGroup("Kick First Bin and Drive");
		kick1stBinAndDrive.addStep(new AutonomousStepSpinIntakeLeft());
		kick1stBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(true));
		kick1stBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue()));
		addStep(kick1stBinAndDrive);
		
		//opens intake and starts driving to next tote
		AutonomousParallelStepGroup push1stBinAndDrive = new AutonomousParallelStepGroup("Push 1st bin and drive");
		push1stBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(false));
		push1stBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue()));
		addStep(push1stBinAndDrive);
		
		//spins intake in and closes
		AutonomousParallelStepGroup intake2ndTote = new AutonomousParallelStepGroup("Intake 2nd Tote");
		intake2ndTote.addStep(new AutonomousStepSetIntakeIn());
		intake2ndTote.addStep(new AutonomousStepSetIntakePistonsState(true));
		intake2ndTote.addStep(new AutonomousStepDelay(INTAKE_TIME.getValue()));
		addStep(intake2ndTote);
		
		
		
		//opens intake and stops spinning
		AutonomousParallelStepGroup openIntakeTote2 = new AutonomousParallelStepGroup("Open and stop intake for Tote2");
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
		kick2ndBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_A_2.getValue(), DRIVE_SPEED_LOW.getValue()));
		addStep(kick2ndBinAndDrive);
		
		//opens intake, starts driving to next tote, and counters the bin push with strafe
		AutonomousParallelStepGroup push2ndBinAndDrive = new AutonomousParallelStepGroup("Push 2nd bin and drive");
		push2ndBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(false));
		push2ndBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_B_2.getValue(), DRIVE_SPEED.getValue()));
		push2ndBinAndDrive.addStep(new AutonomousStepStrafe(COUNTER_SPEED.getValue()));
		addStep(push2ndBinAndDrive);
		
		//stops strafing
		addStep(new AutonomousStepStrafe(0));
		
		//closes intake and spins in
		AutonomousParallelStepGroup intake3rdTote = new AutonomousParallelStepGroup("Intake 3rd Tote");
		intake3rdTote.addStep(new AutonomousStepSetIntakeIn());
		intake3rdTote.addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(intake3rdTote);
		
		addStep(new AutonomousStepDelay(INTAKE_TIME.getValue()));

		//stops spinning and opens intake
		AutonomousParallelStepGroup stopIntake = new AutonomousParallelStepGroup("Reset intake");
		stopIntake.addStep(new AutonomousStepSetIntakePistonsState(false));
		stopIntake.addStep(new AutonomousStepSetIntakeOff());
		addStep(stopIntake);
		
		//picks up tote
		AutonomousParallelStepGroup grabTote3 = new AutonomousParallelStepGroup("Lowers lift under 3rd tote");
		grabTote3.addStep(new AutonomousStepSetLiftBottom());
		grabTote3.addStep(new AutonomousStepDelay(DROP_TIME.getValue()));
		addStep(grabTote3);

		addStep(new AutonomousStepLiftManualControl(0.2, 700));

		//strafes to the autozone with a counter
		AutonomousParallelStepGroup strafe = new AutonomousParallelStepGroup("strafe");
		//strafe.addStep(new AutonomousStepStrafe(SCORE_SPEED.getValue()));
		strafe.addStep(new AutonomousStepDriveManual(0, .75));
		strafe.addStep(new AutonomousStepDelay(TURN_TIME.getValue()));
		addStep(strafe);
		
		addStep(new AutonomousStepDriveManual(1, 0));
		addStep(new AutonomousStepDelay(SCORE_TIME.getValue()));
		
		//stops strafing and countering and drops lift
		AutonomousParallelStepGroup finish = new AutonomousParallelStepGroup("Finshing");
		finish.addStep(new AutonomousStepStrafe(0.0));
		finish.addStep(new AutonomousStepDriveManual(0, 0));
		finish.addStep(new AutonomousStepSetLiftBottom());
		addStep(finish);
		
		// backs off of totes
		addStep(new AutonomousStepDriveDistanceAtSpeed(BACKUP_DISTANCE.getValue(), BACKUP_SPEED.getValue())); 

		//resets the auto flag for intake
		addStep(new AutonomousStepIntakeEndAuto());
		strafe.addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
	}

	@Override
	public String toString() {
		return "Parallel 3 totes ";
	}

}
