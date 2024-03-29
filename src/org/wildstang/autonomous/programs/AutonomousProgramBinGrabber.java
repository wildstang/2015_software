package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousParallelStepGroup;
import org.wildstang.autonomous.steps.bingrabber.AutonomousStepDeployBinGrabbers;
import org.wildstang.autonomous.steps.bingrabber.AutonomousStepRetractBinGrabbers;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveDistanceAtSpeed;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.autonomous.steps.lift.AutonomousStepLiftManualControl;
import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramBinGrabber extends AutonomousProgram {

	protected final DoubleConfigFileParameter DRIVE_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "drive_distance", 50);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 1.0);
	protected final IntegerConfigFileParameter DEPLOY_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "deploy_delay", 1000);

	@Override
	protected void defineSteps() {
		addStep(new AutonomousStepSetShifter(true));
		addStep(new AutonomousStepDeployBinGrabbers());
		addStep(new AutonomousStepDelay(DEPLOY_DELAY.getValue()));
		AutonomousParallelStepGroup driveAndRaiseLift = new AutonomousParallelStepGroup();
		driveAndRaiseLift.addStep(new AutonomousStepDriveDistanceAtSpeed(DRIVE_DISTANCE.getValue(), DRIVE_SPEED.getValue(), false));
		driveAndRaiseLift.addStep(new AutonomousStepLiftManualControl(1, 1000));
		addStep(driveAndRaiseLift);
		addStep(new AutonomousStepDelay(1000));
		addStep(new AutonomousStepRetractBinGrabbers());
	}

	@Override
	public String toString() {
		return "Bin Grabbing";
	}

}
