/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildstangs.autonomous.parameters;

import com.wildstangs.autonomous.AutonomousManager;

/**
 *
 * @author chadschmidt
 */
public class AutonomousIntegerStartPositionConfigFileParameter extends AutonomousIntegerConfigFileParameter{

    public AutonomousIntegerStartPositionConfigFileParameter(String pName, int defValue) {
        super(pName + "." + AutonomousManager.getInstance().getStartPosition().toConfigString(), defValue);
    }
    
}
