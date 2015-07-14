package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveDistanceAtSpeed;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramDriveAtSpeedForTime extends AutonomousProgram {
	protected final IntegerConfigFileParameter DISTANCE_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "Distance", 40);
	protected final DoubleConfigFileParameter SPEED_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "Speed", .5);

	protected void defineSteps() {
		// Shift into high gear
		addStep(new AutonomousStepSetShifter(true));
		addStep(new AutonomousStepDriveDistanceAtSpeed(DISTANCE_CONFIG.getValue(), SPEED_CONFIG.getValue(), false));
	}

	public String toString() {
		return "Drive Distance at Speed";
	}
}