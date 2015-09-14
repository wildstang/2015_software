package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousParallelStepGroup;
import org.wildstang.fw.auto.steps.control.AutonomousStepDelay;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepDriveDistanceAtSpeed;
import org.wildstang.yearly.auto.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.yearly.auto.steps.intake.AutonomousStepRetractBinGrabbers;
import org.wildstang.yearly.auto.steps.lift.AutonomousStepLiftManualControl;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.BinGrabber;

public class AutonomousProgramSuperFastBinGrabbers extends AutonomousProgram {
	
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
		AutonomousParallelStepGroup shiftAndWait = new AutonomousParallelStepGroup("Shift and wait");
		shiftAndWait.addStep(new AutonomousStepSetShifter(true));
		shiftAndWait.addStep(new AutonomousStepDelay(DEPLOY_DELAY.getValue()));
		addStep(shiftAndWait);
		AutonomousParallelStepGroup driveAndRaiseLift = new AutonomousParallelStepGroup();
		driveAndRaiseLift.addStep(new AutonomousStepDriveDistanceAtSpeed(DRIVE_DISTANCE.getValue(), DRIVE_SPEED.getValue(), false));
		driveAndRaiseLift.addStep(new AutonomousStepLiftManualControl(1, 1000));
		addStep(driveAndRaiseLift);
		addStep(new AutonomousStepDelay(1000));
		addStep(new AutonomousStepRetractBinGrabbers());
	}

	@Override
	public String toString() {
		return "SUPER FAST Bin Grabbing";
	}

}
