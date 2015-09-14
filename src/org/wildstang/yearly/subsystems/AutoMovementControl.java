package org.wildstang.yearly.subsystems;

import org.wildstang.logger.Logger;
import org.wildstang.subject.IObserver;
import org.wildstang.subject.Subject;
import org.wildstang.subsystemmanager.AutoMovement;
import org.wildstang.subsystemmanager.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Joey
 */
public class AutoMovementControl extends Subsystem implements IObserver {

	private AutoMovement runningProgram;
	private AutoMovement programToRun;
	private boolean programInProgress;

	public AutoMovementControl(String name) {
		super(name);
	}

	public void init() {
		runningProgram = null;
		programToRun = null;
		programInProgress = false;
	}

	public void update() {
		if (runningProgram != null) {
			runningProgram.update();
			if (runningProgram.isFinished()) {
				cleanUpRunningProgram();
			}
		}
	}

	protected void startProgram() {
		runningProgram = programToRun;
		Logger.getLogger().always("AutoMovementCtrl", "Running Auto Movement", runningProgram.toString());
		runningProgram.initialize();
		SmartDashboard.putString("Auto Movement:", runningProgram.toString());
	}

	public void notifyConfigChange() {
	}

	public void acceptNotification(Subject subjectThatCaused) {
	}

	public void requestMovement(AutoMovement movement) {
		programToRun = movement;
		if (programInProgress && runningProgram != null) {
			// terminate current program and start new one.
			cancelProgram();
		}
		startProgram();
		programInProgress = true;
	}

	public void cancelMovement() {
		if (runningProgram != null) {
			cancelProgram();
		}
	}

	private void cancelProgram() {
		Logger.getLogger().always("AutoMovementCtrl", "Abort Auto Movement", runningProgram.toString());
		runningProgram.abort();
		cleanUpRunningProgram();
	}

	protected void cleanUpRunningProgram() {
		runningProgram.cleanup();
		runningProgram = null;
		programInProgress = false;
		SmartDashboard.putString("Auto Movement:", "");
	}
}
