/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.parameters;

import org.wildstang.fw.config.BooleanConfigFileParameter;

/**
 *
 * @author chadschmidt
 */
public class AutoBooleanConfigFileParameter extends BooleanConfigFileParameter {

	public AutoBooleanConfigFileParameter(String pName, boolean defValue) {
		super("org.wildstang.autonomous.parameters", pName, defValue);
	}

}
