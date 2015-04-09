package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramDriveAtSpeedForTime extends AutonomousProgram {
	protected final IntegerConfigFileParameter TIME_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "Duration", 2000);
	protected final DoubleConfigFileParameter SPEED_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "Speed", .5);

	protected void defineSteps() {

		AutonomousSerialStepGroup drive = new AutonomousSerialStepGroup("Drive");
		// Shift into high gear
		drive.addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
		drive.addStep(new AutonomousStepDriveManual(SPEED_CONFIG.getValue(), 0));
		drive.addStep(new AutonomousStepDelay(TIME_CONFIG.getValue()));
		drive.addStep(new AutonomousStepDriveManual(0.0, 0));

		addStep(drive);
	}

	public String toString() {
		return "Drive For Two Seconds";
	}
}