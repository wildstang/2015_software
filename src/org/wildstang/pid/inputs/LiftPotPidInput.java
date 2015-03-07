/*
 * To change thias template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.pid.inputs;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.pid.inputs.base.IPidInput;

/**
 *
 * @author Joey
 */
public class LiftPotPidInput implements IPidInput {
	protected int potIndex;

	public LiftPotPidInput(int potIndex) {
		this.potIndex = potIndex;
	}

	public double pidRead() {
		return ((Double) InputManager.getInstance().getSensorInput(potIndex).get()).doubleValue();
	}
}
