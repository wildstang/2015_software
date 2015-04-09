package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramKnockOverBin extends AutonomousProgram {
	protected final IntegerConfigFileParameter DRIVE_DURATION = new IntegerConfigFileParameter(this.getClass().getName(), "drive_duration", 500);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 0.5);
	protected final IntegerConfigFileParameter DRIVE_PAUSE = new IntegerConfigFileParameter(this.getClass().getName(), "bin_tip_time", 1000);

	@Override
	protected void defineSteps() {
		// Shift into high gear and begins driving
		addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
		addStep(new AutonomousStepDriveManual(DRIVE_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(DRIVE_DURATION.getValue()));
		// Stop
		addStep(new AutonomousStepDriveManual(0.0, 0));
		// Wait for bin to tip
		addStep(new AutonomousStepDelay(DRIVE_PAUSE.getValue()));
		// Drive backwards
		addStep(new AutonomousStepDriveManual(-DRIVE_SPEED.getValue(), 0));
		addStep(new AutonomousStepDelay(DRIVE_DURATION.getValue()));
		// Stop
		addStep(new AutonomousStepDriveManual(0.0, 0));
	}

	@Override
	public String toString() {
		return "Drive to knock over bin";
	}

}
