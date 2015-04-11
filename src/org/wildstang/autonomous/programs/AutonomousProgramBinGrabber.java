package org.wildstang.autonomous.programs;

import org.wildstang.autonomous.AutonomousProgram;
import org.wildstang.autonomous.steps.AutonomousSerialStepGroup;
import org.wildstang.autonomous.steps.bingrabber.AutonomousStepReleaseBinGrabbers;
import org.wildstang.autonomous.steps.bingrabber.AutonomousStepRetractBinGrabbers;
import org.wildstang.autonomous.steps.control.AutonomousStepDelay;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepDriveManual;
import org.wildstang.autonomous.steps.drivebase.AutonomousStepSetShifter;
import org.wildstang.config.DoubleConfigFileParameter;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousProgramBinGrabber extends AutonomousProgram{
	
	protected final DoubleConfigFileParameter BIN_SPEED = new DoubleConfigFileParameter(this.getClass().getName(), "Stealing_Bin_Speed", 1.0);

	@Override
	protected void defineSteps() {
		// TODO Auto-generated method stub
		addStep(new AutonomousStepSetShifter(DoubleSolenoid.Value.kReverse));
		addStep(new AutonomousStepReleaseBinGrabbers());
		addStep(new AutonomousStepDelay(1000));
		addStep(new AutonomousStepDriveManual(BIN_SPEED.getValue(), 0)); 
		addStep(new AutonomousStepDelay(3000));
		addStep(new AutonomousStepDriveManual(0.0, 0));
		// addStep(new AutonomousStepRetractBinGrabbers());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Bin Grabbing";
	}

}
