/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.testprograms;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.AutonomousParallelStepGroup;
import org.wildstang.fw.auto.steps.control.AutonomousStepDelay;
import org.wildstang.yearly.auto.steps.drivebase.StepDriveManual;

/**
 *
 * @author coder65535
 */
/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
public class AutonomousProgramTestParallel extends AutonomousProgram {

	public void defineSteps() {
		AutonomousParallelStepGroup parallelGroup = new AutonomousParallelStepGroup("Test parallel step group");
		parallelGroup.addStep(new StepDriveManual(StepDriveManual.KEEP_PREVIOUS_STATE, 1.0));
		parallelGroup.addStep(new AutonomousStepDelay(250));
		parallelGroup.addStep(new StepDriveManual(1.0, StepDriveManual.KEEP_PREVIOUS_STATE));
		addStep(parallelGroup);
		addStep(new StepDriveManual(0.0, 0.0));

	}

	public String toString() {
		return "Test Parallel Groups";
	}
}
