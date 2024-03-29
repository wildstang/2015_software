package org.wildstang.subsystems;

import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.inputmanager.inputs.joystick.JoystickAxisEnum;
import org.wildstang.inputmanager.inputs.joystick.JoystickButtonEnum;
import org.wildstang.logger.sender.LogManager;
import org.wildstang.outputmanager.base.OutputManager;
import org.wildstang.outputmanager.outputs.WsVictor;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.DoubleSubject;
import org.wildstang.subjects.base.IObserver;
import org.wildstang.subjects.base.Subject;
import org.wildstang.subsystems.base.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeWheels extends Subsystem implements IObserver {

	private static DoubleConfigFileParameter INTAKE_TURN_SCALE_FACTOR_CONFIG;
	private static DoubleConfigFileParameter INTAKE_TURN_HORIZONTAL_RADIUS_CONFIG;
	private static DoubleConfigFileParameter INTAKE_DEADBAND_CONFIG;

	private static double INTAKE_TURN_SCALE_FACTOR = 0.5;
	private static double INTAKE_TURN_HORIZONTAL_RADIUS = 0.15;
	private static double INTAKE_DEADBAND = .2;

	private static boolean auto = false;

	boolean intakePistonsOut;

	public IntakeWheels(String name) {
		super(name);

		INTAKE_DEADBAND_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "intake_deadband", 0.2);
		INTAKE_TURN_SCALE_FACTOR_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "intake_turn_scale_factor", 0.5);
		INTAKE_TURN_HORIZONTAL_RADIUS_CONFIG = new DoubleConfigFileParameter(this.getClass().getName(), "intake_turn_horizontal_radius", 0.15);

		registerForJoystickButtonNotification(JoystickButtonEnum.MANIPULATOR_BUTTON_5);
	}

	public void init() {
		// Sketchy... on the comp bot, true is false and false is true
		intakePistonsOut = true;
		auto = false;

		INTAKE_TURN_SCALE_FACTOR = INTAKE_TURN_SCALE_FACTOR_CONFIG.getValue();
		INTAKE_TURN_HORIZONTAL_RADIUS = INTAKE_TURN_HORIZONTAL_RADIUS_CONFIG.getValue();
	}

	public void update() {
		double intakeValue = -((Double) (getJoystickValue(JoystickAxisEnum.MANIPULATOR_RIGHT_JOYSTICK_Y))).doubleValue();
		double turnValue = ((Double) (getJoystickValue(JoystickAxisEnum.MANIPULATOR_RIGHT_JOYSTICK_X))).doubleValue();
		if (intakeValue != 0 || turnValue != 0) {
			auto = false;
		}

		if (!auto) {

			double rightMotorSpeed, leftMotorSpeed;
			if (Math.abs(turnValue) > INTAKE_TURN_HORIZONTAL_RADIUS) {
				// Manipulator wants to turn the tote instead of intaking it
				rightMotorSpeed = turnValue * INTAKE_TURN_SCALE_FACTOR;
				leftMotorSpeed = (-turnValue) * INTAKE_TURN_SCALE_FACTOR;
			} else {
				// Do a straight intake
				if (1 - INTAKE_DEADBAND <= intakeValue) {
					intakeValue = 1;
				}
				if (-1 + INTAKE_DEADBAND >= intakeValue) {
					intakeValue = -1;
				}
				rightMotorSpeed = intakeValue;
				leftMotorSpeed = intakeValue;
			}

			getOutput(OutputManager.INTAKE_WHEEL_LEFT_INDEX).set(new Double(leftMotorSpeed));
			getOutput(OutputManager.INTAKE_WHEEL_RIGHT_INDEX).set(new Double(rightMotorSpeed));
			LogManager.getInstance().addLog("Intake Wheel Right", rightMotorSpeed);
			LogManager.getInstance().addLog("Intake Wheel Left", leftMotorSpeed);
			SmartDashboard.putNumber("Intake Wheel Right", rightMotorSpeed);
			SmartDashboard.putNumber("Intake Wheel Left", leftMotorSpeed);
		}
		// Backwards on the comp bot
		getOutput(OutputManager.INTAKE_PISTONS_INDEX).set(new Boolean(!intakePistonsOut));
		SmartDashboard.putBoolean("Intake Pistons Out", intakePistonsOut);
		LogManager.getInstance().addLog("Intake Pistons", intakePistonsOut);
	}

	@Override
	public void acceptNotification(Subject subjectThatCaused) {

		if (subjectThatCaused.getType() == JoystickButtonEnum.MANIPULATOR_BUTTON_5) {
			if (((BooleanSubject) subjectThatCaused).getValue()) {
				intakePistonsOut = !intakePistonsOut;
			}
		}
	}

	public void setPistons(boolean state) {
		intakePistonsOut = state;
	}

	public void endAuto() {
		auto = false;
	}

	public void setWheels(double speed) {
		auto = true;
		getOutput(OutputManager.INTAKE_WHEEL_LEFT_INDEX).set(new Double(-speed));
		getOutput(OutputManager.INTAKE_WHEEL_RIGHT_INDEX).set(new Double(-speed));
	}

	public void setWheels(double rightSpeed, double leftSpeed) {
		auto = true;
		getOutput(OutputManager.INTAKE_WHEEL_LEFT_INDEX).set(new Double(-leftSpeed));
		getOutput(OutputManager.INTAKE_WHEEL_RIGHT_INDEX).set(new Double(-rightSpeed));
	}

	@Override
	public void notifyConfigChange() {
		INTAKE_TURN_SCALE_FACTOR = INTAKE_TURN_SCALE_FACTOR_CONFIG.getValue();
		INTAKE_TURN_HORIZONTAL_RADIUS = INTAKE_TURN_HORIZONTAL_RADIUS_CONFIG.getValue();
	}

}
