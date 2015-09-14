/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.outputmanager;

/**
 *
 * @author chadschmidt
 */
public interface IServo extends IOutput {

	void setAngle(IOutputEnum key, Object value);

}
