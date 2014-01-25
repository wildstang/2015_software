package com.wildstangs.subsystems;

import com.wildstangs.inputmanager.base.WsInputManager;
import com.wildstangs.inputmanager.inputs.joystick.WsJoystickButtonEnum;
import com.wildstangs.outputmanager.base.WsOutputManager;
import com.wildstangs.subjects.base.BooleanSubject;
import com.wildstangs.subjects.base.IObserver;
import com.wildstangs.subjects.base.Subject;
import com.wildstangs.subsystems.arm.Arm;
import com.wildstangs.subsystems.base.WsSubsystem;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author mail929
 */
public class BallHandler extends WsSubsystem implements IObserver
{
    
    protected Arm frontArm, backArm;
    protected boolean frontForwardButton = false, frontReverseButton = false;
    protected boolean backForwardButton = false, backReverseButton = false;
    
    public BallHandler(String name) 
    {
        super(name);
        
        this.frontArm = new Arm(WsOutputManager.FRONT_ARM_VICTOR_INDEX, WsOutputManager.FRONT_ARM_SPIKE_INDEX, WsInputManager.FRONT_ARM_POT_INDEX, true);
        this.backArm = new Arm(WsOutputManager.BACK_ARM_VICTOR_INDEX, WsOutputManager.BACK_ARM_SPIKE_INDEX, WsInputManager.BACK_ARM_POT_INDEX, false);
        
        registerForJoystickButtonNotification(WsJoystickButtonEnum.MANIPULATOR_BUTTON_5);
        registerForJoystickButtonNotification(WsJoystickButtonEnum.MANIPULATOR_BUTTON_6);
        registerForJoystickButtonNotification(WsJoystickButtonEnum.MANIPULATOR_BUTTON_7);
        registerForJoystickButtonNotification(WsJoystickButtonEnum.MANIPULATOR_BUTTON_8);
    }

    public void init() 
    {
    }

    public void update() 
    {
        //Both pressed or both are not pressed
        if(frontForwardButton == frontReverseButton) 
        {
            frontArm.setRoller(Relay.Value.kOff);
        }
        else if(frontForwardButton) 
        {
            frontArm.setRoller(Relay.Value.kForward);
        }
        else if(frontReverseButton) 
        {
            frontArm.setRoller(Relay.Value.kReverse);
        }
        
        //Both pressed or both are not pressed
        if(backForwardButton == backReverseButton)
        {
            backArm.setRoller(Relay.Value.kOff);
        }
        else if(backForwardButton) 
        {
            backArm.setRoller(Relay.Value.kForward);
        }
        else if(backReverseButton) 
        {
            backArm.setRoller(Relay.Value.kReverse);
        }
        
        frontArm.update();
        backArm.update();
        
        Relay.Value frontValue = frontArm.getRollerValue();
        Relay.Value backValue = backArm.getRollerValue();
        String frontString = frontValue == Relay.Value.kForward ? "Forward" : (frontValue == Relay.Value.kReverse ? "Reverse" : "Off");
        String backString = backValue == Relay.Value.kForward ? "Forward" : (backValue == Relay.Value.kReverse ? "Reverse" : "Off");
        SmartDashboard.putNumber("Current Front Arm Angle", frontArm.getCurrentAngle());
        SmartDashboard.putNumber("Wanted Front Arm Angle", frontArm.getWantedAngle());
        SmartDashboard.putNumber("Current Back Arm Angle", backArm.getCurrentAngle());
        SmartDashboard.putNumber("Wanted Back Arm Angle", backArm.getWantedAngle());
        SmartDashboard.putString("Front Arm Roller", frontString);
        SmartDashboard.putString("Back Arm Roller", backString);
    }

    public void notifyConfigChange() 
    {
        frontArm.notifyConfigChange();
        backArm.notifyConfigChange();
    }

    public void acceptNotification(Subject subjectThatCaused)
    {
        if(subjectThatCaused.getType() == WsJoystickButtonEnum.MANIPULATOR_BUTTON_5)
        {
            this.frontForwardButton = ((BooleanSubject) subjectThatCaused).getValue();
        }
        else if(subjectThatCaused.getType() == WsJoystickButtonEnum.MANIPULATOR_BUTTON_6)
        {
            this.frontReverseButton = ((BooleanSubject) subjectThatCaused).getValue();
        }
        else if(subjectThatCaused.getType() == WsJoystickButtonEnum.MANIPULATOR_BUTTON_7)
        {
            this.backForwardButton = ((BooleanSubject) subjectThatCaused).getValue();
        }
        else if(subjectThatCaused.getType() == WsJoystickButtonEnum.MANIPULATOR_BUTTON_8)
        {
            this.backReverseButton = ((BooleanSubject) subjectThatCaused).getValue();
        }
    }
}
