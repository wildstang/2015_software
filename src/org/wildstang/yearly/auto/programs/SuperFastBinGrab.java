package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.AutoParallelStepGroup;
import org.wildstang.fw.auto.steps.control.AutoStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.StepSetShifter;
import org.wildstang.yearly.auto.steps.intake.StepRetractBinGrabbers;
import org.wildstang.yearly.auto.steps.lift.StepLiftManualControl;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.BinGrabber;

public class SuperFastBinGrab extends AutoProgram {
	
	protected final DoubleConfigFileParameter DRIVE_DISTANCE = new DoubleConfigFileParameter(this.getClass().getName(), "drive_distance", 50);
	protected final DoubleConfigFileParameter DRIVE_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "drive_speed", 1.0);
	protected final IntegerConfigFileParameter DEPLOY_DELAY = new IntegerConfigFileParameter(this.getClass().getName(), "deploy_delay", 1000);
	
	@Override
	public void initialize() {
		// Override all the things!
		// We manually deploy the bin grabbers immediately so that we save time
		// Otherwise, it could take a cycle or two (20-40ms) to deploy them
		// We manually update the subsystem and specific output to save valuable milliseconds
		((BinGrabber) SubsystemManager.getInstance().getSubsystem(Robot.BIN_GRABBER)).deployBinGrabbers();
		((BinGrabber) SubsystemManager.getInstance().getSubsystem(Robot.BIN_GRABBER)).update();
		OutputManager.getInstance().getOutput(Robot.BIN_GRABBER).update();
		
		// Now that that's out of the way, carry on as we normally would.
		super.initialize();
	}

	@Override
	protected void defineSteps() {
		AutoParallelStepGroup shiftAndWait = new AutoParallelStepGroup("Shift and wait");
		shiftAndWait.addStep(new StepSetShifter(true));
		shiftAndWait.addStep(new AutoStepDelay(DEPLOY_DELAY.getValue()));
		addStep(shiftAndWait);
		AutoParallelStepGroup driveAndRaiseLift = new AutoParallelStepGroup();
		driveAndRaiseLift.addStep(new StepDriveDistanceAtSpeed(DRIVE_DISTANCE.getValue(), DRIVE_SPEED.getValue(), false));
		driveAndRaiseLift.addStep(new StepLiftManualControl(1, 1000));
		addStep(driveAndRaiseLift);
		addStep(new AutoStepDelay(1000));
		addStep(new StepRetractBinGrabbers());
	}

	@Override
	public String toString() {
		return "SUPER FAST Bin Grabbing";
	}

}
