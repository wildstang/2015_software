package org.wildstang.subsystems;

import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;
import org.wildstang.inputmanager.base.InputManager;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.IntegerSubject;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;
import org.wildstang.subsystems.lift.LiftPreset;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends Subsystem implements IObserver {

	private static DoubleConfigFileParameter LIFT_POT_BOTTOM_VOLTAGE_CONFIG, LIFT_POT_TOP_VOLTAGE_CONFIG, TOP_SLOW_DOWN_RADIUS_CONFIG, BOTTOM_SLOW_DOWN_RADIUS_CONFIG;
	private static IntegerConfigFileParameter PAWL_ENGAGE_TIME_MILLIS_CONFIG, PAWL_DISENGAGE_TIME_MILLIS_CONFIG, MIN_CYCLES_WINCH_MOTOR_AT_ZERO_CONFIG;
	private static DoubleConfigFileParameter LIFT_DEADBAND_CONFIG;

	private static long PAWL_ENGAGE_TIME_MILLIS;
	private static long PAWL_DISENGAGE_TIME_MILLIS;
	private static int MIN_CYCLES_WINCH_MOTOR_AT_ZERO;
	private static double LIFT_DEADBAND;
	private static double BOTTOM_VOLTAGE;
	private static double TOP_VOLTAGE;
	private static double TOP_SLOW_DOWN_RADIUS;
	private static double BOTTOM_SLOW_DOWN_RADIUS;

	private static LiftPreset bottomPreset = new LiftPreset("bottom", 0, -1);
	private static LiftPreset levelTwoPreset = new LiftPreset("level_two", 1, 2);
	private static LiftPreset levelThreePreset = new LiftPreset("level_three", 150, 3);
	private static LiftPreset levelFourPreset = new LiftPreset("level_four", 150, 4);
	private static LiftPreset topPreset = new LiftPreset("top", 5, -1);
	
	private static int wantedHallEffect;
	private static double wantedPotVoltage;
	private static double wantedPotVoltageBuffer = .1;
	private static boolean goingToPosition;

	double potVoltage;
	int selectedHallEffectSensor;

	boolean manualOverride;

	// PAWL STUFF

	private enum PawlState {
		PAWL_DISENGAGED, PAWL_ENGAGING, PAWL_ENGAGED, PAWL_DISENGAGING
	}

	PawlState pawlState = PawlState.PAWL_ENGAGED;
	private int numCyclesWinchMotorAtZero = 0;
	private long lastPawlStateChange = 0;

	public Lift(String name) {
		super(name);

		// Initialize config parameters
		LIFT_POT_BOTTOM_VOLTAGE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "pot_bottom_voltage", 0.0);
		LIFT_POT_TOP_VOLTAGE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "pot_top_voltage", 5.0);
		// If the current pot voltage gets within this amount of the max or min
		// voltage, we'll slow down the lift motor
		TOP_SLOW_DOWN_RADIUS_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "top_slow_down_radius", 0.2);
		BOTTOM_SLOW_DOWN_RADIUS_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "bottom_slow_down_radius", 0.4);
		PAWL_ENGAGE_TIME_MILLIS_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "pawl_engage_time_millis", 100);
		PAWL_DISENGAGE_TIME_MILLIS_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "pawl_disengage_time_millis", 100);
		MIN_CYCLES_WINCH_MOTOR_AT_ZERO_CONFIG = new IntegerConfigFileParameter(this.getClass().getName(), "min_cycles_winch_motor_at_zero", 5);
		LIFT_DEADBAND_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "deadband", 0.05);

		BOTTOM_VOLTAGE = LIFT_POT_BOTTOM_VOLTAGE_CONFIG.getValue();
		TOP_VOLTAGE = LIFT_POT_TOP_VOLTAGE_CONFIG.getValue();
		LIFT_DEADBAND = LIFT_DEADBAND_CONFIG.getValue();
		PAWL_ENGAGE_TIME_MILLIS = PAWL_ENGAGE_TIME_MILLIS_CONFIG.getValue();
		PAWL_DISENGAGE_TIME_MILLIS = PAWL_DISENGAGE_TIME_MILLIS_CONFIG.getValue();
		MIN_CYCLES_WINCH_MOTOR_AT_ZERO = MIN_CYCLES_WINCH_MOTOR_AT_ZERO_CONFIG.getValue();
		TOP_SLOW_DOWN_RADIUS = TOP_SLOW_DOWN_RADIUS_CONFIG.getValue();
		BOTTOM_SLOW_DOWN_RADIUS = BOTTOM_SLOW_DOWN_RADIUS_CONFIG.getValue();

		registerForSensorNotification(InputManager.HALL_EFFECT_INDEX);

		SmartDashboard.putString("lift", "started");
		// Manual override
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_1);
		// down
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_2);
		// up
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_4);
		// 1 bin
		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_3);

		goingToPosition = false;
	}

	public void init() {

	}

	public void setPreset(LiftPreset preset) {
		goingToPosition = true;
		SmartDashboard.putString("Preset", preset.toString());
		wantedHallEffect = preset.getHallEffectIndex();
		wantedPotVoltage = preset.getWantedVoltage();
		System.out.println("Preset set with voltage " + preset.getWantedVoltage());
	}

	public void setBottom()
	{
		setPreset(bottomPreset);
	}
	
	public void setTop()
	{
		setPreset(topPreset);
	}
	
	public void setFour()
	{
		setPreset(levelFourPreset);
	}
	
	public void update() {
		potVoltage = (double) getSensorInput(InputManager.LIFT_POT_INDEX).getSubject().getValueAsObject();

		double winchJoystickValue = ((Double) (getJoystickValue(JoystickAxisEnum.MANIPULATOR_LIFT))).doubleValue();
		double winchMotorSpeed = 0;

		if (Math.abs(winchJoystickValue) > 0) {
			// Immediately disable pid if the manipulator wants manual control.
			goingToPosition = false;
			winchMotorSpeed = winchJoystickValue;
		}
		
		else if (goingToPosition)
		{
			if((potVoltage > wantedPotVoltage && potVoltage < wantedPotVoltage) || selectedHallEffectSensor == wantedHallEffect)
			{
				//if in the wanted range stop
				goingToPosition = false;
			}
			else
			{
				//otherwise keep on going
				
				if(potVoltage < wantedPotVoltage)
				{
					//go up
					winchMotorSpeed = 1;
					
					if (potVoltage > (wantedPotVoltage - wantedPotVoltageBuffer) && winchMotorSpeed > 0)
					{
						double scaleFactor = (wantedPotVoltage - potVoltage) / wantedPotVoltageBuffer;
						winchMotorSpeed *= scaleFactor;
					}
				}
				else
				{
					//goDown
					winchMotorSpeed = -1;
					
					if (potVoltage < (wantedPotVoltage + wantedPotVoltageBuffer) && winchMotorSpeed < 0)
					{
						double scaleFactor = (potVoltage - wantedPotVoltage) / wantedPotVoltageBuffer;
						winchMotorSpeed *= scaleFactor;
					}
				}
			}
			
			System.out.println("Preset motor speed: " + winchMotorSpeed);

		}
		else
		{
			// Pid is disabled, manipulator is not requesting any movement.
			// Stop the winch
			winchMotorSpeed = 0.0;
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
					// Engage the pawl if the winch has been stopped for
					// specified number of cycles
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

		// If we're in manual override mode, don't stop at the top or bottom
		if (!manualOverride ) {
			// If we're trying to move down and we're close to the bottom of the
			// lift, slow down.
			if (potVoltage < (BOTTOM_VOLTAGE + BOTTOM_SLOW_DOWN_RADIUS) && potVoltage > BOTTOM_VOLTAGE && scaledMotorSpeed < 0) {
				double scaleFactor = (potVoltage - BOTTOM_VOLTAGE) / (BOTTOM_SLOW_DOWN_RADIUS);
				scaledMotorSpeed *= scaleFactor;
				System.out.println("Scale factor (at bot): " + scaleFactor);
			} else if (potVoltage <= BOTTOM_VOLTAGE && scaledMotorSpeed < 0) {
				scaledMotorSpeed = 0;
			}

			// If we're trying to move up and we're close to the top of the lift,
			// slow down
			if (potVoltage > (TOP_VOLTAGE - TOP_SLOW_DOWN_RADIUS) && potVoltage < TOP_VOLTAGE && scaledMotorSpeed > 0) {
				double scaleFactor = (TOP_VOLTAGE - potVoltage) / TOP_SLOW_DOWN_RADIUS;
				scaledMotorSpeed *= scaleFactor;
				System.out.println("Scale factor (at top): " + scaleFactor);
			} else if (potVoltage >= TOP_VOLTAGE && scaledMotorSpeed > 0) {
				scaledMotorSpeed = 0;
			}
		}

		// Invert the output so we go in the right direction
		getOutput(OutputManager.LIFT_A_INDEX).set(new Double(scaledMotorSpeed * -1));
		getOutput(OutputManager.LIFT_B_INDEX).set(new Double(scaledMotorSpeed * -1));

		// The pawl is engaged when the solenoid is false (piston retracted =
		// pawl engaged)
		getOutput(OutputManager.PAWL_RELEASE_INDEX).set(new Boolean(!pawlEngaged));

		LogManager.getInstance().addObject("Winch", scaledMotorSpeed);
		LogManager.getInstance().addObject("Lift Pot", potVoltage);
		LogManager.getInstance().addObject("Pawl Engaged", pawlEngaged);

		SmartDashboard.putNumber("Winch", scaledMotorSpeed);
		SmartDashboard.putNumber("Lift Pot", potVoltage);
		SmartDashboard.putBoolean("Pawl Release", pawlEngaged);
		SmartDashboard.putNumber("Selected hall effect", selectedHallEffectSensor);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {
		if (subjectThatCaused.equals(getSensorInput(InputManager.HALL_EFFECT_INDEX).getSubject())) {
			// Update from the arduino for the hall effect
			selectedHallEffectSensor = ((IntegerSubject) subjectThatCaused).getValue();
		} 
		else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_1) 
		{
			manualOverride = ((BooleanSubject) subjectThatCaused).getValue();
		} 
		else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_2) 
		{
			if (((BooleanSubject) subjectThatCaused).getValue()) 
			{
				setPreset(bottomPreset);
			}
		} 
		else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_4) 
		{
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				setPreset(topPreset);
			}
		}
		else if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_3) 
		{
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				setPreset(levelFourPreset);
			}
		}
	}

	@Override
	public void notifyConfigChange() {
		BOTTOM_VOLTAGE = LIFT_POT_BOTTOM_VOLTAGE_CONFIG.getValue();
		TOP_VOLTAGE = LIFT_POT_TOP_VOLTAGE_CONFIG.getValue();
		LIFT_DEADBAND = LIFT_DEADBAND_CONFIG.getValue();
		PAWL_ENGAGE_TIME_MILLIS = PAWL_ENGAGE_TIME_MILLIS_CONFIG.getValue();
		PAWL_DISENGAGE_TIME_MILLIS = PAWL_DISENGAGE_TIME_MILLIS_CONFIG.getValue();
		MIN_CYCLES_WINCH_MOTOR_AT_ZERO = MIN_CYCLES_WINCH_MOTOR_AT_ZERO_CONFIG.getValue();
		TOP_SLOW_DOWN_RADIUS = TOP_SLOW_DOWN_RADIUS_CONFIG.getValue();
		BOTTOM_SLOW_DOWN_RADIUS = BOTTOM_SLOW_DOWN_RADIUS_CONFIG.getValue();

		// Notify presets
		bottomPreset.notifyConfigChange();
		levelTwoPreset.notifyConfigChange();
		levelThreePreset.notifyConfigChange();
		levelFourPreset.notifyConfigChange();
		topPreset.notifyConfigChange();
	}
}
