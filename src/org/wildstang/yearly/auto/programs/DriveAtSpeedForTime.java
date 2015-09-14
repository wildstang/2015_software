package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.AutoSerialStepGroup;
import org.wildstang.fw.auto.steps.control.AutoStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveManual;
import org.wildstang.yearly.auto.steps.drivebase.StepSetShifter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DriveAtSpeedForTime extends AutoProgram {
	protected final IntegerConfigFileParameter DISTANCE_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "Distance", 40);
	protected final DoubleConfigFileParameter SPEED_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "Speed", .5);

	protected void defineSteps() {
		// Shift into high gear
		addStep(new StepSetShifter(true));
		addStep(new StepDriveDistanceAtSpeed(DISTANCE_CONFIG.getValue(), SPEED_CONFIG.getValue(), false));
	}

	public String toString() {
		return "Drive Distance at Speed";
	}
}