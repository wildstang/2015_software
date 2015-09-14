/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.parameters;

import org.wildstang.fw.config.IntegerConfigFileParameter;

/**
 *
 * @author chadschmidt
 */
public class AutoIntegerConfigFileParameter extends IntegerConfigFileParameter {

	public AutoIntegerConfigFileParameter(String pName, int defValue) {
		super("org.wildstang.autonomous.parameters", pName, defValue);
	}

}
