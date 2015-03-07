/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.pid.outputs;

import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.pid.outputs.base.IPidOutput;

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
