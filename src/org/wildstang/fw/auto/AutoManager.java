package org.wildstang.fw.auto;

import java.util.ArrayList;
import java.util.List;

import org.wildstang.fw.auto.program.Sleeper;
import org.wildstang.fw.logger.Logger;
import org.wildstang.fw.subject.IObserver;
import org.wildstang.fw.subject.Subject;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class AutoManager implements IObserver {

	private List<AutoProgram> programs = new ArrayList<AutoProgram>();
	private AutoProgram runningProgram;
	private boolean programFinished, programRunning;
	private static AutoManager instance = null;
	private AutoStartPositionEnum currentPosition;
	private SendableChooser chooser;
	private SendableChooser lockinChooser;

	private AutoManager() {
		currentPosition = AutoStartPositionEnum.UNKNOWN;
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
			runningProgram = (AutoProgram) chooser.getSelected();
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
		runningProgram = (AutoProgram) programs.get(0);
		SmartDashboard.putString("Running Autonomous Program", "No Program Running");
		SmartDashboard.putString("Current Start Position", currentPosition.toString());
	}

	public AutoProgram getRunningProgram() {
		if (programRunning) {
			return runningProgram;
		} else {
			return (AutoProgram) null;
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

	public AutoStartPositionEnum getStartPosition() {
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

	public static AutoManager getInstance() {
		if (AutoManager.instance == null) {
			AutoManager.instance = new AutoManager();
		}
		return AutoManager.instance;
	}

	/*
	 * public void setProgram(int index) { if (index >= programs.size() || index < 0) { index = 0; } currentProgram =
	 * index; lockedProgram = currentProgram; }
	 */

	public void setPosition(int index) {
		if (index >= AutoStartPositionEnum.POSITION_COUNT) {
			index = 0;
		}
		currentPosition = AutoStartPositionEnum.getEnumFromValue(index);
	}

	private void definePrograms() {
		addProgram(new Sleeper()); // Always leave Sleeper as
													// 0. Other parts of the
													// code assume 0 is Sleeper.

	}

	public void addProgram(AutoProgram program) {
		programs.add(program);
		chooser.addObject(program.toString(), program);
	}
}
