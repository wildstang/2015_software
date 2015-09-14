package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousSerialStepGroup;
import org.wildstang.fw.auto.steps.control.AutonomousStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepSetShifter;

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