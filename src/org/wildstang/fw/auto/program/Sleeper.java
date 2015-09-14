/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.program;

import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.auto.steps.control.AutoStepStopAutonomous;

/**
 *
 * @author coder65535
 */
/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
public class Sleeper extends AutoProgram {

	public void defineSteps() {
		addStep(new AutoStepStopAutonomous());
	}

	public String toString() {
		return "Sleeper";
	}
}
