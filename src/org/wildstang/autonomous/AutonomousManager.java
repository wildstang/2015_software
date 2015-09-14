package org.wildstang.autonomous;

import java.util.ArrayList;
import java.util.List;

import org.wildstang.autonomous.programs.*;
import org.wildstang.logger.Logger;
import org.wildstang.subject.IObserver;
import org.wildstang.subject.Subject;
import org.wildstang.yearly.auto.programs.AutonomousProgramBinGrabber;
import org.wildstang.yearly.auto.programs.AutonomousProgramDriveAtSpeedForTime;
import org.wildstang.yearly.auto.programs.AutonomousProgramKnockOverBin;
import org.wildstang.yearly.auto.programs.AutonomousProgramSleeper;
import org.wildstang.yearly.auto.programs.AutonomousProgramSuperFastBinGrabbers;
import org.wildstang.yearly.auto.programs.AutonomousProgramThreeTotesParallel;
import org.wildstang.yearly.auto.programs.AutonomousProgramThreeTotesStraight;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class AutonomousManager implements IObserver {

	private List<AutonomousProgram> programs = new ArrayList<AutonomousProgram>();
	private AutonomousProgram runningProgram;
	private boolean programFinished, programRunning;
	private static AutonomousManager instance = null;
	private AutonomousStartPositionEnum currentPosition;
	private SendableChooser chooser;
	private SendableChooser lockinChooser;

	private AutonomousManager() {
		currentPosition = AutonomousStartPositionEnum.UNKNOWN;
		chooser = new SendableChooser();
		lockinChooser = new SendableChooser();
		lockinChooser.addDefault("Unlocked", false);
		lockinChooser.addObject("Locked", true);

		definePrograms();

		SmartDashboard.putData("Select Autonomous Program", chooser);
		SmartDashboard.putData("Lock in auto program", lockinChooser);

		clear();
	}

	public void update() {
		if (programFinished) {
			runningProgram.cleanup();
			programFinished = false;
			startSleeper();
		}
		runningProgram.update();
		if (runningProgram.isFinished()) {
			programFinished = true;
		}
	}

	public void startCurrentProgram() {
		if ((Boolean) lockinChooser.getSelected()) {
			runningProgram = (AutonomousProgram) chooser.getSelected();
		} else {
			runningProgram = programs.get(0);
		}
		Logger.getLogger().always("Auton", "Running Autonomous Program", runningProgram.toString());
		runningProgram.initialize();
		SmartDashboard.putString("Running Autonomous Program", runningProgram.toString());
	}

	public void startSleeper() {
		runningProgram = programs.get(0);
		runningProgram.initialize();
	}

	public void clear() {
		programFinished = false;
		programRunning = false;
		if (runningProgram != null) {
			runningProgram.cleanup();
		}
		runningProgram = (AutonomousProgram) programs.get(0);
		SmartDashboard.putString("Running Autonomous Program", "No Program Running");
		SmartDashboard.putString("Current Start Position", currentPosition.toString());
	}

	public AutonomousProgram getRunningProgram() {
		if (programRunning) {
			return runningProgram;
		} else {
			return (AutonomousProgram) null;
		}
	}

	public String getRunningProgramName() {
		return runningProgram.toString();
	}

	/*
	 * public String getSelectedProgramName() { return programs.get(currentProgram).toString(); }
	 * 
	 * public String getLockedProgramName() { return programs.get(lockedProgram).toString(); }
	 */

	public AutonomousStartPositionEnum getStartPosition() {
		return currentPosition;
	}

	public void acceptNotification(Subject cause) {
		/*
		 * if (cause instanceof DoubleSubject) { if (cause ==
		 * InputManager.getInstance().getOiInput(InputManager.START_POSITION_SELECTOR_INDEX).getSubject()) {
		 * positionSwitch = (float) ((DoubleSubject) cause).getValue(); if (positionSwitch >= 3.3) { positionSwitch =
		 * 3.3f; } if (positionSwitch < 0) { positionSwitch = 0; } currentPosition =
		 * AutonomousStartPositionEnum.getEnumFromValue((int) (Math.floor((positionSwitch / 3.4) *
		 * AutonomousStartPositionEnum.POSITION_COUNT))); SmartDashboard.putString("Current Start Position",
		 * currentPosition.toString()); } else if (cause ==
		 * InputManager.getInstance().getOiInput(InputManager.AUTO_PROGRAM_SELECTOR_INDEX).getSubject()) {
		 * selectorSwitch = (float) ((DoubleSubject) cause).getValue(); if (selectorSwitch >= 3.3) { selectorSwitch =
		 * 3.3f; } if (selectorSwitch < 0) { selectorSwitch = 0; } currentProgram = (int) (Math.floor((selectorSwitch /
		 * 3.4) * programs.size())); SmartDashboard.putString("Current Autonomous Program",
		 * programs.get(currentProgram).toString()); } } else if (cause instanceof BooleanSubject) { lockInSwitch =
		 * ((BooleanSubject) cause).getValue(); lockedProgram = !lockInSwitch ? currentProgram : 0;
		 * SmartDashboard.putString("Locked Autonomous Program", programs.get(lockedProgram).toString()); }
		 */
	}

	public static AutonomousManager getInstance() {
		if (AutonomousManager.instance == null) {
			AutonomousManager.instance = new AutonomousManager();
		}
		return AutonomousManager.instance;
	}

	/*
	 * public void setProgram(int index) { if (index >= programs.size() || index < 0) { index = 0; } currentProgram =
	 * index; lockedProgram = currentProgram; }
	 */

	public void setPosition(int index) {
		if (index >= AutonomousStartPositionEnum.POSITION_COUNT) {
			index = 0;
		}
		currentPosition = AutonomousStartPositionEnum.getEnumFromValue(index);
	}

	private void definePrograms() {
		addProgram(new AutonomousProgramSleeper()); // Always leave Sleeper as
													// 0. Other parts of the
													// code assume 0 is Sleeper.

		addProgram(new AutonomousProgramThreeTotesStraight());
		addProgram(new AutonomousProgramDriveAtSpeedForTime());
		addProgram(new AutonomousProgramKnockOverBin());
		addProgram(new AutonomousProgramThreeTotesParallel());
		addProgram(new AutonomousProgramBinGrabber());
		addProgram(new AutonomousProgramSuperFastBinGrabbers());
	}

	private void addProgram(AutonomousProgram program) {
		programs.add(program);
		chooser.addObject(program.toString(), program);
	}
}
