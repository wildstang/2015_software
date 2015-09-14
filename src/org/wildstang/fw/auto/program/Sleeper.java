/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.program;

import org.wildstang.fw.auto.AutonomousProgram;
import org.wildstang.fw.auto.steps.control.AutonomousStepStopAutonomous;

/**
 *
 * @author coder65535
 */
/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
public class Sleeper extends AutonomousProgram {

	public void defineSteps() {
		addStep(new AutonomousStepStopAutonomous());
	}

	public String toString() {
		return "Sleeper";
	}
}
