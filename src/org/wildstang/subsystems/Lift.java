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

	private static final long PAWL_ENGAGE_TIME_MILLIS = 100;
	private static final long PAWL_DISENGAGE_TIME_MILLIS = 100;
	private static final double LIFT_DEADBAND = 0.05;
	// 100 ms
	private static final long MIN_CYCLES_WINCH_MOTOR_AT_ZERO = 5;

	boolean atBottom;
	boolean atTop;
	double potVal;

	// PAWL STUFF

	private enum PawlState {
		PAWL_DISENGAGED, PAWL_ENGAGING, PAWL_ENGAGED, PAWL_DISENGAGING
	}

	PawlState pawlState = PawlState.PAWL_ENGAGED;
	private int numCyclesWinchMotorAtZero = 0;
	private long lastPawlStateChange = 0;

	public Lift(String name) {
		super(name);

		registerForSensorNotification(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX);
		registerForSensorNotification(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX);
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

		// State machine time!
		// Default to pawl engaged
		boolean pawlEngaged = true;
		switch (pawlState) {
		case PAWL_DISENGAGED:
			// the pawl is not engaged, allow the winch to turn freely
			pawlEngaged = false;
			if (Math.abs(winchMotorSpeed) < LIFT_DEADBAND) {
				// Begin counting cycles that the motor speed is within deadband
				numCyclesWinchMotorAtZero++;
				if (numCyclesWinchMotorAtZero > MIN_CYCLES_WINCH_MOTOR_AT_ZERO) {
					// Engage the pawl if the winch has been stopped for specified number of cycles
					pawlState = PawlState.PAWL_ENGAGING;
					lastPawlStateChange = System.currentTimeMillis();
					pawlEngaged = true;
				}
			} else {
				// Winch is still moving, reset cycle count
				numCyclesWinchMotorAtZero = 0;
			}
			break;
		case PAWL_ENGAGING:
			// Disable the winch motor
			winchMotorSpeed = 0;
			// Make sure the pawl piston is still engaged
			pawlEngaged = true;
			if (System.currentTimeMillis() > lastPawlStateChange + PAWL_ENGAGE_TIME_MILLIS) {
				// Pawl has finished engaging
				pawlState = PawlState.PAWL_ENGAGED;
			}
			break;
		case PAWL_ENGAGED:
			// Make sure the pawl piston is still engaged
			pawlEngaged = true;
			if (winchMotorSpeed != 0) {
				// We should disengage the pawl
				pawlState = PawlState.PAWL_DISENGAGING;
				lastPawlStateChange = System.currentTimeMillis();
			}
			// Disable the winch until the pawl is disengaged
			winchMotorSpeed = 0;
			break;
		case PAWL_DISENGAGING:
			// Disable the winch motor
			winchMotorSpeed = 0;
			// Make sure the pawl piston is still disengaged
			pawlEngaged = false;
			if (System.currentTimeMillis() > lastPawlStateChange + PAWL_DISENGAGE_TIME_MILLIS) {
				// Pawl has finished disengaging
				pawlState = PawlState.PAWL_DISENGAGED;
			}
			break;
		}

		getOutput(OutputManager.LIFT_A_INDEX).set(new Double(winchMotorSpeed));
		getOutput(OutputManager.LIFT_B_INDEX).set(new Double(winchMotorSpeed));
		
		// The pawl is engaged when the solenoid is false (piston retracted = pawl engaged)
		getOutput(OutputManager.PAWL_RELEASE_INDEX).set(new Boolean(!pawlEngaged));

		potVal = (double) getSensorInput(InputManager.LIFT_POT_INDEX).getSubject().getValueAsObject();

		LogManager.getInstance().addObject("Winch", winchMotorSpeed);
		SmartDashboard.putNumber("Winch", winchMotorSpeed);
		LogManager.getInstance().addObject("Lift Pot", potVal);
		SmartDashboard.putNumber("Lift Pot", potVal);
		LogManager.getInstance().addObject("Pawl Engaged", pawlEngaged);
		SmartDashboard.putBoolean("Pawl Release", pawlEngaged);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.equals(getSensorInput(InputManager.LIFT_BOTTOM_LIMIT_SWITCH_INDEX).getSubject())) {
			atBottom = !((BooleanSubject) subjectThatCaused).getValue();
		} else if (subjectThatCaused.equals(getSensorInput(InputManager.LIFT_TOP_LIMIT_SWITCH_INDEX).getSubject())) {
			atTop = !((BooleanSubject) subjectThatCaused).getValue();
		}
	}
}
