package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.AutoParallelStepGroup;
import org.wildstang.fw.auto.steps.control.AutoStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepSetShifter;
import org.wildstang.yearly.auto.steps.intake.StepDeployBinGrabbers;
import org.wildstang.yearly.auto.steps.intake.StepRetractBinGrabbers;
import org.wildstang.yearly.auto.steps.lift.StepLiftManualControl;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GrabBin extends AutoProgram {

	protected final DoubleConfigFileParameter DRIVE_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "drive_distance", 50);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 1.0);
	protected final IntegerConfigFileParameter DEPLOY_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "deploy_delay", 1000);

	@Override
	protected void defineSteps() {
		addStep(new StepSetShifter(true));
		addStep(new StepDeployBinGrabbers());
		addStep(new AutoStepDelay(DEPLOY_DELAY.getValue()));
		AutoParallelStepGroup driveAndRaiseLift = new AutoParallelStepGroup();
		driveAndRaiseLift.addStep(new StepDriveDistanceAtSpeed(DRIVE_DISTANCE.getValue(), DRIVE_SPEED.getValue(), false));
		driveAndRaiseLift.addStep(new StepLiftManualControl(1, 1000));
		addStep(driveAndRaiseLift);
		addStep(new AutoStepDelay(1000));
		addStep(new StepRetractBinGrabbers());
	}

	@Override
	public String toString() {
		return "Bin Grabbing";
	}

}
