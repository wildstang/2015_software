package org.wildstang.yearly.robot;

import org.wildstang.inputmanager.InputManager;
import org.wildstang.inputs.NoInput;
import org.wildstang.inputs.WsAnalogInput;
import org.wildstang.inputs.WsHallEffectInput;
import org.wildstang.inputs.WsLIDAR;
import org.wildstang.inputs.driverstation.WsDSAnalogInput;
import org.wildstang.inputs.driverstation.WsDSDigitalInput;
import org.wildstang.inputs.joystick.DriverJoystick;
import org.wildstang.inputs.joystick.ManipulatorJoystick;
import org.wildstang.nooutput.NoOutput;
import org.wildstang.outputmanager.OutputManager;
import org.wildstang.outputs.WsDoubleSolenoid;
import org.wildstang.outputs.WsDriveSpeed;
import org.wildstang.outputs.WsSolenoid;
import org.wildstang.outputs.WsVictor;
import org.wildstang.subsystemmanager.*;
import org.wildstang.yearly.subsystems.BinGrabber;
import org.wildstang.yearly.subsystems.Containment;
import org.wildstang.yearly.subsystems.DriveBase;
import org.wildstang.yearly.subsystems.IntakeWheels;
import org.wildstang.yearly.subsystems.Lift;
import org.wildstang.yearly.subsystems.Monitor;

import edu.wpi.first.wpilibj.I2C.Port;

public class Robot {
	
	private static Robot instance = null;
	
	/**
	 * Method to get the instance of this singleton object.
	 *
	 * @return The instance of InputManager
	 */
	public static Robot getInstance() {
		if (instance == null) {
			instance = new Robot();
		}
		return instance;
	}

	
	// Unknown index for all types.   All other indices start with 1.
	public static final int UNKNOWN_INDEX = 0;
	
	// OI Inputs
	public static final int DRIVER_JOYSTICK = 1;
	public static final int MANIPULATOR_JOYSTICK = 2;
	public static final int AUTO_PROGRAM_SELECTOR = 3;
	public static final int LOCK_IN_SWITCH = 4;
	public static final int START_POSITION_SELECTOR = 5;
	// Sensor Inputs
	public static final int LIDAR = 6;
	public static final int LIFT_POT = 7;
	public static final int HALL_EFFECT = 8;
	
	//Subsystems
	public static final int DRIVE_BASE = 9;
	public static final int LED = 10;
	public static final int AUTO_MOVEMENT_CONTROLLER = 11;
	public static final int MONITOR = 12;
	public static final int LIFT = 13;
	public static final int TEST = 14;
	public static final int INTAKE_WHEELS = 15;
	public static final int ARMS = 16;
	public static final int TOP_CONTAINMENT = 17;
	public static final int BIN_GRABBER = 18;
	
	//Outputs
	public static final int RIGHT_DRIVE_SPEED = 19;
	public static final int LEFT_DRIVE_SPEED = 20;
	public static final int STRAFE_DRIVE_SPEED = 21;
	public static final int SHIFTER = 22;
	public static final int LIFT_A = 23;
	public static final int LIFT_B = 24;
	public static final int INTAKE_WHEEL_LEFT = 25;
	public static final int INTAKE_WHEEL_RIGHT = 26;
	public static final int INTAKE_PISTONS = 27;
	public static final int PAWL_RELEASE = 28;

	
	protected Robot()
	{
		SubsystemManager smm = SubsystemManager.getInstance();
		InputManager im = InputManager.getInstance();
		OutputManager om = OutputManager.getInstance();
		
		smm.addSubsystem(DRIVE_BASE, new DriveBase("Driver Base"));
		//smm.addSubsystem(LED_INDEX, new LED("LED"));
		//smm.addSubsystem(AUTO_MOVEMENT_CONTROLLER_INDEX, new AutoMovementControl("AutoMovementController));
		//smm.addSubsystem(TEST_INDEX, new Test());
		smm.addSubsystem(LIFT, new Lift("Lift"));
		smm.addSubsystem(MONITOR, new Monitor("Monitor"));
		smm.addSubsystem(INTAKE_WHEELS, new IntakeWheels("Intake"));
		smm.addSubsystem(BIN_GRABBER, new BinGrabber("Bin Grabber"));
		smm.addSubsystem(TOP_CONTAINMENT, new Containment("Bin Gripper"));
		
		om.addOutput(UNKNOWN_INDEX, new NoOutput());

		// MOTORS
		om.addOutput(RIGHT_DRIVE_SPEED, new WsDriveSpeed("Right Drive Speed", 2, 3));
		om.addOutput(LEFT_DRIVE_SPEED, new WsDriveSpeed("Left Drive Speed", 0, 1));
		om.addOutput(STRAFE_DRIVE_SPEED, new WsDriveSpeed("Strafe Drive Speed", 4, 5));
		om.addOutput(LIFT_A, new WsVictor("Lift A", 6));
		om.addOutput(LIFT_B, new WsVictor("Lift B", 7));
		om.addOutput(INTAKE_WHEEL_RIGHT, new WsVictor("Right Intake Wheels", 9));
		om.addOutput(INTAKE_WHEEL_LEFT, new WsVictor("Left Intake Wheel", 8));

		// SOLENOIDS
		om.addOutput(SHIFTER, new WsDoubleSolenoid("Shifter", 0, 1));
		om.addOutput(PAWL_RELEASE, new WsSolenoid("Pawl Release", 2));
		om.addOutput(INTAKE_PISTONS, new WsSolenoid("Intake Pistons", 4));
		om.addOutput(TOP_CONTAINMENT, new WsSolenoid("Top Containment", 6));
		om.addOutput(BIN_GRABBER, new WsDoubleSolenoid("Bin Grabbers", 3, 5));
		
		im.addSensorInput(UNKNOWN_INDEX, new NoInput());
		im.addSensorInput(LIDAR, new WsLIDAR());
		im.addSensorInput(LIFT_POT, new WsAnalogInput(0));
		im.addSensorInput(HALL_EFFECT, new WsHallEffectInput(Port.kMXP, 0x10));

		im.addOiInput(UNKNOWN_INDEX, new NoInput());
		im.addOiInput(DRIVER_JOYSTICK, new DriverJoystick());
		im.addOiInput(MANIPULATOR_JOYSTICK, new ManipulatorJoystick());
		im.addOiInput(AUTO_PROGRAM_SELECTOR, new WsDSAnalogInput(1));
		im.addOiInput(LOCK_IN_SWITCH, new WsDSDigitalInput(1));
		im.addOiInput(START_POSITION_SELECTOR, new WsDSAnalogInput(2));
		
	}

}
