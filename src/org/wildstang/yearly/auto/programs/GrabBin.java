package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousParallelStepGroup;
import org.wildstang.fw.auto.steps.control.AutonomousStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepSetShifter;
import org.wildstang.yearly.auto.steps.intake.StepDeployBinGrabbers;
import org.wildstang.yearly.auto.steps.intake.StepRetractBinGrabbers;
import org.wildstang.yearly.auto.steps.lift.StepLiftManualControl;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GrabBin extends AutonomousProgram {

	protected final DoubleConfigFileParameter DRIVE_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "drive_distance", 50);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 1.0);
	protected final IntegerConfigFileParameter DEPLOY_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "deploy_delay", 1000);

	@Override
	protected void defineSteps() {
		addStep(new StepSetShifter(true));
		addStep(new StepDeployBinGrabbers());
		addStep(new AutonomousStepDelay(DEPLOY_DELAY.getValue()));
		AutonomousParallelStepGroup driveAndRaiseLift = new AutonomousParallelStepGroup();
		driveAndRaiseLift.addStep(new StepDriveDistanceAtSpeed(DRIVE_DISTANCE.getValue(), DRIVE_SPEED.getValue(), false));
		driveAndRaiseLift.addStep(new StepLiftManualControl(1, 1000));
		addStep(driveAndRaiseLift);
		addStep(new AutonomousStepDelay(1000));
		addStep(new StepRetractBinGrabbers());
	}

	@Override
	public String toString() {
		return "Bin Grabbing";
	}

}
