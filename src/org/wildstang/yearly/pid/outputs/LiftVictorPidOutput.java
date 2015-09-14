/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.yearly.pid.outputs;

import org.wildstang.fw.outputmanager.OutputManager;
import org.wildstang.fw.pid.output.IPidOutput;

/**
 *
 * @author Joey
 */
public class LiftVictorPidOutput implements IPidOutput {
	protected int victorIndexA;
	protected int victorIndexB;

	public LiftVictorPidOutput(int victorIndexA, int victorIndexB) {
		this.victorIndexA = victorIndexA;
		this.victorIndexB = victorIndexB;
	}

	public void pidWrite(double output) {
		OutputManager.getInstance().getOutput(victorIndexA).set(new Double(output));
		OutputManager.getInstance().getOutput(victorIndexB).set(new Double(output));
	}

}
