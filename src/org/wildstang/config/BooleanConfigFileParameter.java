/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.config;

import org.wildstang.configmanager.ConfigManager;
import org.wildstang.logger.Logger;

/**
 *
 * @author Nathan
 */
public class BooleanConfigFileParameter extends ConfigFileParameter {

	boolean defaultValue;

	/**
	 * Creates a boolean config file parameter.
	 *
	 * @param cName
	 *            The name of the class
	 * @param pName
	 *            The name of the parameter
	 * @param defValue
	 *            a default value to use.
	 */
	public BooleanConfigFileParameter(String cName, String pName, boolean defValue) {
		super(cName, pName);
		defaultValue = defValue;
	}

	/**
	 * Retrieve the config file parameter.
	 *
	 * @return the config file parameter value.
	 */
	public boolean getValue() {
		String fullName = getFullParamName();
		String val;
		try {
			val = ConfigManager.getInstance().getConfigParamByName(fullName);
			if (val.equals("true") || val.equals("True") || val.equals("TRUE")) {
				return true;
			} else {
				return false;
			}
		} catch (Throwable e) {
			System.out.println(fullName + " parameter not found. Using default value.");
			return defaultValue;
		}
	}
}
