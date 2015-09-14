/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.auto.programs;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.control.AutonomousStepStopAutonomous;

/**
 *
 * @author coder65535
 */
/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
public class AutonomousProgramSleeper extends AutonomousProgram {

	public void defineSteps() {
		addStep(new AutonomousStepStopAutonomous());
	}

	public String toString() {
		return "Sleeper";
	}
}
