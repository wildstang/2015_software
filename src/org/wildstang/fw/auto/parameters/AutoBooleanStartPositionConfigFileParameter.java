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
public class AutoBooleanStartPositionConfigFileParameter extends AutoBooleanConfigFileParameter {

	public AutoBooleanStartPositionConfigFileParameter(String pName, boolean defValue) {
		super(pName + "." + AutoManager.getInstance().getStartPosition().toConfigString(), defValue);
	}

}
