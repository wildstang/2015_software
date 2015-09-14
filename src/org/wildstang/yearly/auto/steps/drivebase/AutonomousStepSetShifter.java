/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.auto.steps.drivebase;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystemmanager.SubsystemManager;
import org.wildstang.yearly.robot.Robot;
import org.wildstang.yearly.subsystems.DriveBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * @author Joey
 */
public class AutonomousStepSetShifter extends AutonomousStep {
	protected boolean highGear;;

	public AutonomousStepSetShifter(boolean highGear) {
		this.highGear = highGear;
	}

	public void initialize() {
		((DriveBase) SubsystemManager.getInstance().getSubsystem(Robot.DRIVE_BASE)).setShifter(highGear);
		this.finished = true;
	}

	public void update() {
	}

	public String toString() {
		return "Set Shifter State";
	}

}
