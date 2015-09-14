package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.control.AutonomousStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSetIntakeIn;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepSetIntakeOff;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepSetLiftBottom;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramKnockOverBinAndPickUp extends AutonomousProgram {
	protected final IntegerConfigFileParameter DRIVE_DURATION = new IntegerConfigFileParameter(this.getClass().getName(), "drive_duration", 500);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 0.5);
	protected final IntegerConfigFileParameter BIN_TIP_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "bin_tip_time", 1000);
	protected final IntegerConfigFileParameter INTAKE_ENGAGE_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "intake_engage_delay", 1000);	

	@Override
	protected void defineSteps() {
		// Shift into high gear and begins driving
		addStep(new AutonomousStepSetShifter(true));
		addStep(new AutonomousStepDriveManual(DRIVE_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(DRIVE_DURATION.getValue()));
		// Stop
		addStep(new AutonomousStepDriveManual(0.0, 0));
		// Wait for bin to tip
		addStep(new AutonomousStepDelay(BIN_TIP_TIME.getValue()));
		// Drive backwards
		addStep(new AutonomousStepDriveManual(-DRIVE_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(DRIVE_DURATION.getValue()));
		// Stop
		addStep(new AutonomousStepDriveManual(0.0, 0));
		// Allow the robot to settle
		addStep(new AutonomousStepDelay(300));
		// Lower the lift
		addStep(new AutonomousStepSetLiftBottom());
		// Wait for the lift to reach the bottom
		addStep(new AutonomousStepDelay(1500));
		// Allow things to settle
		addStep(new AutonomousStepDelay(300));
		// Shift into low gear and drive forward slowly to pick up the bin
		addStep(new AutonomousStepSetShifter(false));
		addStep(new AutonomousStepDriveManual(0.2, 0.0));
		// Begin running intake
		addStep(new AutonomousStepSetIntakeIn());
		// After the specified delay, close intake
		addStep(new AutonomousStepDelay(INTAKE_ENGAGE_DELAY.getValue()));
		// Continue driving forward
		addStep(new AutonomousStepDelay(1000));
		// Stop driving, disable intake
		addStep(new AutonomousStepDriveManual(0.0, 0.0));
		addStep(new AutonomousStepSetIntakeOff());
	}

	@Override
	public String toString() {
		return "Drive to knock over bin";
	}

}
