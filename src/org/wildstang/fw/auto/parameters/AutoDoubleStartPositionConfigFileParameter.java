/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.parameters;

import org.wildstang.fw.auto.AutoManager;

/**
 *
 * @author chadschmidt
 */
public class AutoDoubleStartPositionConfigFileParameter extends AutoDoubleConfigFileParameter {

	public AutoDoubleStartPositionConfigFileParameter(String pName, double defValue) {
		super(pName + "." + AutoManager.getInstance().getStartPosition().toConfigString(), defValue);
	}

}
