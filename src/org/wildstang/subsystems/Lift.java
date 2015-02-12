package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends Subsystem implements IObserver {
	boolean atBottom;
	boolean atTop;
	boolean pawl;
	double potVal;

	public Lift(String name) {
		super(name);

		registerForSensorNotification(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX);
		registerForSensorNotification(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX);
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_4);
	}

	public void init() {
		atBottom = false;
		atTop = false;
	}

	public void update() {
		double winchJoystickValue = ((Double) (getJoystickValue(JoystickAxisEnum.MANIPULATOR_LEFT_JOYSTICK_Y))).doubleValue();
		double winchMotorSpeed = 0;
		if ((atBottom && winchJoystickValue < 0) || (atTop && winchJoystickValue > 0)) {
			// Prevent driving past the top/bottom of the lift
			winchMotorSpeed = 0;
		} else {
			winchMotorSpeed = winchJoystickValue;
		}
		if(winchMotorSpeed < 0)
		{
			pawl = true;
		}
		else if(pawl == true)
		{
			pawl = false;
		}
		
		getOutput(OutputManager.LIFT_A_INDEX).set(new Double(winchMotorSpeed));
		getOutput(OutputManager.LIFT_B_INDEX).set(new Double(winchMotorSpeed));
		getOutput(OutputManager.PAWL_RELEASE_INDEX).set(new Boolean(pawl));
		
		potVal = (double) getSensorInput(InputManager.LIFT_POT_INDEX).getSubject().getValueAsObject();

		LogManager.getInstance().addObject("Winch", winchMotorSpeed);
		SmartDashboard.putNumber("Winch", winchMotorSpeed);
		LogManager.getInstance().addObject("Lift Pot", potVal);
		SmartDashboard.putNumber("Lift Pot", potVal);
		LogManager.getInstance().addObject("Pawl Release", pawl);
		SmartDashboard.putBoolean("Pawl Release", pawl);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.equals(getSensorInput(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX).getSubject())) {
			atBottom = ((BooleanSubject) subjectThatCaused).getValue();
		} else if (subjectThatCaused.equals(getSensorInput(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX).getSubject())) {
			atTop = ((BooleanSubject) subjectThatCaused).getValue();
		}
		if(subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_4)
		{
			pawl = ((Boolean) subjectThatCaused.getValueAsObject());
		}
	}
	
	public void setPawl(boolean state)
	{
		pawl = state;
	}

}
