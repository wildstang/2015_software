package org.wildstang.subsystems;

import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.IntegerSubject;
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
	int selectedHallEffectSensor;

	// PAWL STUFF

	private enum PawlState {
		PAWL_DISENGAGED, PAWL_ENGAGING, PAWL_ENGAGED, PAWL_DISENGAGING
	}

	PawlState pawlState = PawlState.PAWL_ENGAGED;
	private int numCyclesWinchMotorAtZero = 0;
	private long lastPawlStateChange = 0;

	public Lift(String name) {
		super(name);

		registerForSensorNotification(InputManager.HALL_EFFECT_BOTTOM);
		registerForSensorNotification(InputManager.HALL_EFFECT_TOP);
		registerForSensorNotification(InputManager.HALL_EFFECT_INDEX);
	}

	public void init() {
		atBottom = false;
		atTop = false;
	}

	public void update() {
		potVal = (double) getSensorInput(InputManager.LIFT_POT_INDEX).getSubject().getValueAsObject();

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
			} else if ((atBottom && winchJoystickValue < 0) || (atTop && winchJoystickValue > 0)) {
				// We are at the top/bottom; engage the pawl immediately
				pawlState = PawlState.PAWL_ENGAGING;
				lastPawlStateChange = System.currentTimeMillis();
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
			if (Math.abs(winchMotorSpeed) > LIFT_DEADBAND) {
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

		/*
		 * We need to drive the lift motors at a minimum of 70% to avoid stalling. At this point in the code the motor
		 * value should be in the range -1 to 1. We'll map this to a value less than -0.7 or greater than 0.7, depending
		 * on the desired direction.
		 * 
		 * To simplify calculations, we'll convert any negative values to positive ones before mapping between ranges.
		 * After the mapping, we'll convert back to a negative number.
		 */
		boolean isWinchMotorSpeedNegative = (winchMotorSpeed < 0 ? true : false);
		if (isWinchMotorSpeedNegative) {
			winchMotorSpeed *= -1;
		}

		double scaledMotorSpeed;

		if (winchMotorSpeed > LIFT_DEADBAND) {
			scaledMotorSpeed = (winchMotorSpeed - LIFT_DEADBAND) / (1.0 - LIFT_DEADBAND) * (1.0 - 0.7) + 0.7;
		} else {
			scaledMotorSpeed = 0;
		}

		// If necessary, convert back to a negative number.
		if (isWinchMotorSpeedNegative) {
			scaledMotorSpeed *= -1;
		}

		// Invert the output so we go in the right direction
		getOutput(OutputManager.LIFT_A_INDEX).set(new Double(scaledMotorSpeed * -1));
		getOutput(OutputManager.LIFT_B_INDEX).set(new Double(scaledMotorSpeed * -1));

		// The pawl is engaged when the solenoid is false (piston retracted = pawl engaged)
		getOutput(OutputManager.PAWL_RELEASE_INDEX).set(new Boolean(!pawlEngaged));

		LogManager.getInstance().addObject("Winch", scaledMotorSpeed);
		LogManager.getInstance().addObject("Lift Pot", potVal);
		LogManager.getInstance().addObject("Pawl Engaged", pawlEngaged);

		SmartDashboard.putNumber("Winch", scaledMotorSpeed);
		SmartDashboard.putNumber("Lift Pot", potVal);
		SmartDashboard.putBoolean("Pawl Release", pawlEngaged);
		SmartDashboard.putBoolean("At top", atTop);
		SmartDashboard.putBoolean("At bottom", atBottom);
		SmartDashboard.putNumber("Selected hall effect", selectedHallEffectSensor);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		// Limit switch values are pulled low when they are triggered
		if (subjectThatCaused.equals(getSensorInput(InputManager.HALL_EFFECT_BOTTOM).getSubject())) {
			atBottom = !((BooleanSubject) subjectThatCaused).getValue();
		} else if (subjectThatCaused.equals(getSensorInput(InputManager.HALL_EFFECT_TOP).getSubject())) {
			atTop = !((BooleanSubject) subjectThatCaused).getValue();
		} else if (subjectThatCaused.equals(getSensorInput(InputManager.HALL_EFFECT_INDEX).getSubject())) {
			// Update from the arduino for the hall effect
			selectedHallEffectSensor = ((IntegerSubject) subjectThatCaused).getValue();
		}
	}
}
