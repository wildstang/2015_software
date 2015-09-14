/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.parameters;

import org.wildstang.fw.auto.AutonomousManager;

/**
 *
 * @author chadschmidt
 */
public class AutonomousDoubleStartPositionConfigFileParameter extends AutonomousDoubleConfigFileParameter {

	public AutonomousDoubleStartPositionConfigFileParameter(String pName, double defValue) {
		super(pName + "." + AutonomousManager.getInstance().getStartPosition().toConfigString(), defValue);
	}

}
