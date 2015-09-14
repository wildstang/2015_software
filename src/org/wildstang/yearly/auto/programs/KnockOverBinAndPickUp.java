package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.control.AutoStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveManual;
import org.wildstang.yearly.auto.steps.drivebase.StepSetShifter;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeIn;
import org.wildstang.yearly.auto.steps.intake.StepSetIntakeOff;
import org.wildstang.yearly.auto.steps.lift.StepSetLiftBottom;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class KnockOverBinAndPickUp extends AutoProgram {
	protected final IntegerConfigFileParameter DRIVE_DURATION = new IntegerConfigFileParameter(this.getClass().getName(), "drive_duration", 500);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 0.5);
	protected final IntegerConfigFileParameter BIN_TIP_TIME = new IntegerConfigFileParameter(this.getClass().getName(), "bin_tip_time", 1000);
	protected final IntegerConfigFileParameter INTAKE_ENGAGE_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "intake_engage_delay", 1000);	

	@Override
	protected void defineSteps() {
		// Shift into high gear and begins driving
		addStep(new StepSetShifter(true));
		addStep(new StepDriveManual(DRIVE_SPEED.getValue(), 0));
		addStep(new AutoStepDelay(DRIVE_DURATION.getValue()));
		// Stop
		addStep(new StepDriveManual(0.0, 0));
		// Wait for bin to tip
		addStep(new AutoStepDelay(BIN_TIP_TIME.getValue()));
		// Drive backwards
		addStep(new StepDriveManual(-DRIVE_SPEED.getValue(), 0));
		addStep(new AutoStepDelay(DRIVE_DURATION.getValue()));
		// Stop
		addStep(new StepDriveManual(0.0, 0));
		// Allow the robot to settle
		addStep(new AutoStepDelay(300));
		// Lower the lift
		addStep(new StepSetLiftBottom());
		// Wait for the lift to reach the bottom
		addStep(new AutoStepDelay(1500));
		// Allow things to settle
		addStep(new AutoStepDelay(300));
		// Shift into low gear and drive forward slowly to pick up the bin
		addStep(new StepSetShifter(false));
		addStep(new StepDriveManual(0.2, 0.0));
		// Begin running intake
		addStep(new StepSetIntakeIn());
		// After the specified delay, close intake
		addStep(new AutoStepDelay(INTAKE_ENGAGE_DELAY.getValue()));
		// Continue driving forward
		addStep(new AutoStepDelay(1000));
		// Stop driving, disable intake
		addStep(new StepDriveManual(0.0, 0.0));
		addStep(new StepSetIntakeOff());
	}

	@Override
	public String toString() {
		return "Drive to knock over bin";
	}

}
