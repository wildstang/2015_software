package org.wildstang.subsystems;

import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeWheels extends Subsystem implements IObserver{
	boolean pressed = false;
	public IntakeWheels(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public void init(){
	registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_3);	
	getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(0.0));
	getOutput(OutputManager.INTAKE_WHEELS_INDEX).update();
	}
	public void update(){
		if (pressed){
			getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(10.0));
			
		}
		else {
			getOutput(OutputManager.INTAKE_WHEELS_INDEX).set(new Double(0.0));
			
		}
		getOutput(OutputManager.INTAKE_WHEELS_INDEX).update();
		SmartDashboard.putBoolean("Intake Wheels", pressed);
	}
	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if(subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_3){
			pressed = true;
		}
		else{
			pressed = false; 
		}
	}
	
}
