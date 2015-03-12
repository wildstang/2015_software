package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStartDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepStopDriveUsingMotionProfile;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepWaitForDriveMotionProfile;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftBottom;
import org.wildstang.autonomous.steps.lift.AutonomousStepSetLiftMiddle;
import org.wildstang.config.DoubleConfigFileParameter;

public class AutonomousProgramThreeTotes extends AutonomousProgram
{

    protected final DoubleConfigFileParameter DISTANCE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "DistanceToDrive", 175.0);
    
	@Override
	protected void defineSteps()
	{
        AutonomousSerialStepGroup drive = new AutonomousSerialStepGroup("Drive");
        drive.addStep(new AutonomousStepStartDriveUsingMotionProfile(10, 1.0));
        drive.addStep(new AutonomousStepWaitForDriveMotionProfile());
        drive.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		AutonomousSerialStepGroup pickup = new AutonomousSerialStepGroup("Pickup");
		pickup.addStep(drive);
		pickup.addStep(new AutonomousStepSetLiftMiddle());
		pickup.addStep(new AutonomousStepSetLiftBottom());
		pickup.addStep(new AutonomousStepSetLiftMiddle());

		AutonomousSerialStepGroup strafe = new AutonomousSerialStepGroup("Strafe");
        strafe.addStep(new AutonomousStepStartDriveUsingMotionProfile(20, 1.0));
        strafe.addStep(new AutonomousStepWaitForDriveMotionProfile());
        strafe.addStep(new AutonomousStepStopDriveUsingMotionProfile());
        
		AutonomousSerialStepGroup driveToNext = new AutonomousSerialStepGroup("Drive To Next");
		driveToNext.addStep(strafe);
		driveToNext.addStep(new AutonomousStepStartDriveUsingMotionProfile(20, 1.0));
		driveToNext.addStep(new AutonomousStepWaitForDriveMotionProfile());
        driveToNext.addStep(new AutonomousStepStopDriveUsingMotionProfile());

		addStep(pickup);
		addStep(driveToNext);
		addStep(pickup);
		addStep(driveToNext);
		addStep(pickup);
		
	}

	@Override
	public String toString()
	{
		return "Pickup Tote";
	}

}