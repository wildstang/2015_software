
package com.wildstangs.subsystems;

import com.wildstangs.config.IntegerConfigFileParameter;
import com.wildstangs.inputmanager.inputs.joystick.JoystickDPadButtonEnum;
import com.wildstangs.outputmanager.base.IOutputEnum;
import com.wildstangs.outputmanager.base.IServo;
import com.wildstangs.outputmanager.base.OutputManager;
import com.wildstangs.subjects.base.BooleanSubject;
import com.wildstangs.subjects.base.IObserver;
import com.wildstangs.subjects.base.Subject;
import com.wildstangs.subsystems.base.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sahil
 */
public class Ears extends Subsystem implements IObserver{
    
    protected final IntegerConfigFileParameter LEFT_EAR_UP_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "EarPreset.Left.Up", 90);
    protected final IntegerConfigFileParameter LEFT_EAR_DOWN_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "EarPreset.Left.Down", 0);
    protected final IntegerConfigFileParameter RIGHT_EAR_UP_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "EarPreset.Right.Up", 0);
    protected final IntegerConfigFileParameter RIGHT_EAR_DOWN_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "EarPreset.Right.Down", 90);
    
    boolean currentEarState = false;
    
    protected int leftEarUp, rightEarUp, leftEarDown, rightEarDown;
    
    public Ears(String name) {
        super(name);
        registerForJoystickButtonNotification(JoystickDPadButtonEnum.MANIPULATOR_D_PAD_BUTTON_UP);
        registerForJoystickButtonNotification(JoystickDPadButtonEnum.MANIPULATOR_D_PAD_BUTTON_DOWN);
        
        leftEarUp = LEFT_EAR_UP_CONFIG.getValue();
        rightEarUp = RIGHT_EAR_UP_CONFIG.getValue();
        
        leftEarDown = LEFT_EAR_DOWN_CONFIG.getValue();
        rightEarDown = RIGHT_EAR_DOWN_CONFIG.getValue();
    }

    public void init() {
        currentEarState = false;
    }
    
    public void update() {
       SmartDashboard.putBoolean("Ears are up", currentEarState);
       
       ((IServo) OutputManager.getInstance().getOutput(OutputManager.LEFT_EAR_SERVO_INDEX)).setAngle((IOutputEnum) null, new Double(currentEarState ? leftEarUp : leftEarDown));      
       ((IServo) OutputManager.getInstance().getOutput(OutputManager.RIGHT_EAR_SERVO_INDEX)).setAngle((IOutputEnum) null, new Double(currentEarState ? rightEarUp : rightEarDown));  
    }

    public void notifyConfigChange() {
        leftEarUp = LEFT_EAR_UP_CONFIG.getValue();
        rightEarUp = RIGHT_EAR_UP_CONFIG.getValue();
        
        leftEarDown = LEFT_EAR_DOWN_CONFIG.getValue();
        rightEarDown = RIGHT_EAR_DOWN_CONFIG.getValue();
    }

    public void acceptNotification(Subject subjectThatCaused) 
    {
        boolean buttonState = ((BooleanSubject) subjectThatCaused).getValue();
        if(buttonState)
        {
            if (subjectThatCaused.getType() == JoystickDPadButtonEnum.MANIPULATOR_D_PAD_BUTTON_UP)
            {
                this.currentEarState = true;
            }
            else if(subjectThatCaused.getType() == JoystickDPadButtonEnum.MANIPULATOR_D_PAD_BUTTON_DOWN)
            {
                this.currentEarState = false;
            }
        }
    }
    
    public void setEarState (boolean currentEarState) {
        this.currentEarState = currentEarState;
    }

}