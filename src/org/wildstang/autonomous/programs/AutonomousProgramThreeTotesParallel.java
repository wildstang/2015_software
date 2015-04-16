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
		AutonomousParallelStepGroup intakeTote1 = new AutonomousParallelStepGroup("Intake First Tote");
		intakeTote1.addStep(new AutonomousStepSetIntakeIn());
		intakeTote1.addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(intakeTote1);
		addStep(new AutonomousStepDelay(500));
		// stops spinning intake and opens
		AutonomousParallelStepGroup openIntakeTote1 = new AutonomousParallelStepGroup("Open and stop intake for Tote1");
		openIntakeTote1.addStep(new AutonomousStepSetIntakePistonsState(false));
		openIntakeTote1.addStep(new AutonomousStepSetIntakeOff());
		addStep(openIntakeTote1);
		addStep(new AutonomousStepDelay(100));
		// lifts tote up
		addStep(new AutonomousStepSetLiftTop());
		addStep(new AutonomousStepDelay(2000));

		// picks up second bin
		// shifts into high gear and begins driving
		addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kForward));
		// closes intake starts spinning left
		AutonomousParallelStepGroup kick1stBinAndDrive = new AutonomousParallelStepGroup("Kick First Bin and Drive");
		kick1stBinAndDrive.addStep(new AutonomousStepSpinIntakeLeft());
		kick1stBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(true));
		kick1stBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue()));
		addStep(kick1stBinAndDrive);
		// opens intake (attempt to knock bins)
		AutonomousParallelStepGroup push1stBinAndDrive = new AutonomousParallelStepGroup("Push 1st bin and drive");
		push1stBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(false));
		push1stBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue()));
		addStep(push1stBinAndDrive);
		AutonomousParallelStepGroup intake2ndTote = new AutonomousParallelStepGroup("Intake 2nd Tote");
		// stops driving closes intake and spins intake in
		intake2ndTote.addStep(new AutonomousStepSetIntakeIn());
		////addStep(new AutonomousStepDriveManual(0.0, 0));
		intake2ndTote.addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(intake2ndTote);
		addStep(new AutonomousStepDelay(800));
		
		
		// opens intake stops spinning
		AutonomousParallelStepGroup openIntakeTote2 = new AutonomousParallelStepGroup("Open and stop intake for Tote2");
		openIntakeTote2.addStep(new AutonomousStepSetIntakePistonsState(false));
		openIntakeTote2.addStep(new AutonomousStepSetIntakeOff());
		addStep(openIntakeTote2);
		//Hopefully we can clear the intake just on down time, so leave delay out. 
		//addStep(new AutonomousStepDelay(100));
		// picks up tote
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDelay(1500));
		// WHY ISN'T THIS WORKING
		addStep(new AutonomousStepSetLiftTop());
		addStep(new AutonomousStepDelay(2000));

		// picks up third tote
		// closes intake starts spinning left
		AutonomousParallelStepGroup kick2ndBinAndDrive = new AutonomousParallelStepGroup("Kick 2nd Bin and Drive");
		kick2ndBinAndDrive.addStep(new AutonomousStepSpinIntakeLeft());
		kick2ndBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(true));
		kick2ndBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_A.getValue(), DRIVE_SPEED_LOW.getValue()));
		addStep(kick2ndBinAndDrive);
		// opens intake (attempt to knock bins)
		AutonomousParallelStepGroup push2ndBinAndDrive = new AutonomousParallelStepGroup("Push 2nd bin and drive");
		push2ndBinAndDrive.addStep(new AutonomousStepSetIntakePistonsState(false));
		push2ndBinAndDrive.addStep(new AutonomousStepDriveDistanceAtSpeed(TOTES_DISTANCE_B.getValue(), DRIVE_SPEED.getValue()));
		addStep(push2ndBinAndDrive);
		
		AutonomousParallelStepGroup intake3rdTote = new AutonomousParallelStepGroup("Intake 3rd Tote");
		// stops driving closes intake and spins intake in
		intake3rdTote.addStep(new AutonomousStepSetIntakeIn());
		////addStep(new AutonomousStepDriveManual(0.0, 0));
		intake3rdTote.addStep(new AutonomousStepSetIntakePistonsState(true));
		addStep(intake3rdTote);
		addStep(new AutonomousStepDelay(800));
		// opens intake stops spinning
		addStep(new AutonomousStepSetIntakeOff());
		// picks up tote
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDelay(1500));
		addStep(new AutonomousStepLiftManualControl(0.2, 700));

		// scores tote
		// strafes over to scoring zone (doesn't work)
		AutonomousParallelStepGroup strafe = new AutonomousParallelStepGroup("strafe");
		// stops driving closes intake and spins intake in
		strafe.addStep(new AutonomousStepStrafe(SCORE_SPEED.getValue()));
		////addStep(new AutonomousStepDriveManual(0.0, 0));
		strafe.addStep(new AutonomousStepDelay(SCORE_TIME.getValue()));
		strafe.addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
		strafe.addStep(new AutonomousStepDriveDistanceAtSpeed(SCORE_TIME.getValue(), -0.2));
		addStep(strafe);
		addStep(new AutonomousStepStrafe(0.0));
		// sets down totes (may be unneeded, just dragging totes)
		addStep(new AutonomousStepSetLiftBottom());
		addStep(new AutonomousStepDelay(2000));
		// backs off of totes
		addStep(new AutonomousStepDriveDistanceAtSpeed(BACKUP_DISTANCE.getValue(), BACKUP_SPEED.getValue()));
		////addStep(new AutonomousStepDriveManual(BACK_SPEED.getValue(), 0));
		////addStep(new AutonomousStepDelay(BACKUP_TIME.getValue()));
		////addStep(new AutonomousStepDriveManual(0.0, 0));

		addStep(new AutonomousStepIntakeEndAuto());
	}

	@Override
	public String toString() {
		return "Parallel 3 totes ";
	}

}
