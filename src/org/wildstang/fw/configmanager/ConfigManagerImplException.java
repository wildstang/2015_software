package org.wildstang.fw.configmanager;

/**
 *
 * @author Nathan
 */
public class ConfigManagerImplException extends Exception {

	public ConfigManagerImplException(String message) {
		super(message);
		System.out.println(message);
	}
}