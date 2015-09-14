/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.parameters;

import org.wildstang.fw.config.DoubleConfigFileParameter;

/**
 *
 * @author chadschmidt
 */
public class AutoDoubleConfigFileParameter extends DoubleConfigFileParameter {

	public AutoDoubleConfigFileParameter(String pName, double defValue) {
		super("org.wildstang.autonomous.parameters", pName, defValue);
	}

}
