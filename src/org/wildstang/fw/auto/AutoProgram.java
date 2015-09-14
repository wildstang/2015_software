package org.wildstang.fw.auto;

import java.util.ArrayList;
import java.util.List;

import org.wildstang.fw.auto.steps.AutoStep;
import org.wildstang.fw.auto.steps.control.AutoStepStopAutonomous;
import org.wildstang.fw.config.IntegerConfigFileParameter;
import org.wildstang.fw.logger.Logger;

/**
 *
 * @author Nathan
 */
public abstract class AutoProgram {

	protected final List<AutoStep> programSteps = new ArrayList<>();
	protected int currentStep;
	protected boolean finishedPreviousStep, finished;

	protected abstract void defineSteps(); // Use this method to set the steps
											// for this program. Programs
											// execute the steps in the array
											// programSteps serially.
	// Remember to clear everything before all of your steps are finished,
	// because once they are, it immediately drops into Sleeper.

	public void initialize() {
		defineSteps();
		loadStopPosition();
		currentStep = 0;
		finishedPreviousStep = false;
		finished = false;
		((AutoStep) programSteps.get(0)).initialize();
		Logger.getLogger().debug("Auton", "Step Starting", programSteps.get(0).toString());
	}

	public void cleanup() {
		programSteps.clear();
	}

	public void update() {
		if (finished) {
			return;
		}
		if (finishedPreviousStep) {
			finishedPreviousStep = false;
			currentStep++;
			if (currentStep >= programSteps.size()) {
				finished = true;
				return;
			} else {
				Logger.getLogger().debug("Auton", "Step Start", programSteps.get(currentStep).toString());
				((AutoStep) programSteps.get(currentStep)).initialize();
			}
		}
		AutoStep step = (AutoStep) programSteps.get(currentStep); // Prevent
																				// errors
																				// caused
																				// by
																				// mistyping.
		step.update();
		if (step.isFinished()) {
			Logger.getLogger().debug("Auton", "Step Finished", step);
			finishedPreviousStep = true;
		}
	}

	public AutoStep getCurrentStep() {
		return (AutoStep) programSteps.get(currentStep);
	}

	public AutoStep getNextStep() {
		if (currentStep + 1 < programSteps.size()) {
			return (AutoStep) programSteps.get(currentStep + 1);
		} else {
			return null;
		}
	}

	protected void loadStopPosition() {
		IntegerConfigFileParameter forceStopAtStep = new IntegerConfigFileParameter(this.getClass().getName(), "ForceStopAtStep", 0);
		if (forceStopAtStep.getValue() != 0) {
			int forceStop = forceStopAtStep.getValue();
			if ((forceStop <= programSteps.size()) && (forceStop > 0)) {
				programSteps.set(forceStop, new AutoStepStopAutonomous());
				Logger.getLogger().always("Auton", "Force Stop", "Program is forced to stop at Step " + forceStop);
			} else {
				Logger.getLogger().error("Auton", "Force Stop", "Force stop value is outside of bounds. (0 to " + (programSteps.size() - 1));
			}
		}
	}

	public boolean isFinished() {
		return finished;
	}

	protected void addStep(AutoStep newStep) {
		programSteps.add(newStep);
	}

	public abstract String toString();
}
