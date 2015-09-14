package org.wildstang.inputs.joystick;

import org.wildstang.inputmanager.IInput;
import org.wildstang.inputmanager.IInputEnum;
import org.wildstang.subject.BooleanSubject;
import org.wildstang.subject.DoubleSubject;
import org.wildstang.subject.ISubjectEnum;
import org.wildstang.subject.Subject;
import org.wildstang.yearly.subjects.Debouncer;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Nathan
 */
public class ManipulatorJoystick implements IInput {

	final static int numberOfAxes = 6;
	DoubleSubject[] axes;
	final static int numberOfButtons = 12;
	BooleanSubject[] buttons;
	protected final static int numberOfDPadButtons = 4;
	protected BooleanSubject[] dPadButtons;
	protected final static int neededCyclesForChange = 8;
	protected Debouncer[] dPadDebouncers;
	Joystick manipulatorJoystick = null;

	public ManipulatorJoystick() {
		manipulatorJoystick = (Joystick) new Joystick(1);

		axes = new DoubleSubject[numberOfAxes];
		for (int i = 0; i < axes.length; i++) {
			if (JoystickAxisEnum.getEnumFromIndex(false, i) != null) {
				axes[i] = new DoubleSubject(JoystickAxisEnum.getEnumFromIndex(false, i));
			} else {
				axes[i] = new DoubleSubject("ManipulatorSubject" + 1);
			}
		}

		buttons = new BooleanSubject[numberOfButtons];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new BooleanSubject(JoystickButtonEnum.getEnumFromIndex(false, i));
		}

		dPadButtons = new BooleanSubject[numberOfDPadButtons];
		dPadDebouncers = new Debouncer[numberOfDPadButtons];
		for (int i = 0; i < dPadButtons.length; i++) {
			dPadButtons[i] = new BooleanSubject(JoystickDPadButtonEnum.getEnumFromIndex(false, i));
			dPadDebouncers[i] = new Debouncer(20, new Boolean(false));
		}
	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		if (subjectEnum instanceof JoystickAxisEnum && ((JoystickAxisEnum) subjectEnum).isDriver() == false) {
			return axes[((JoystickAxisEnum) subjectEnum).toValue()];
		} else if (subjectEnum instanceof JoystickButtonEnum && ((JoystickButtonEnum) subjectEnum).isDriver() == false) {
			return buttons[((JoystickButtonEnum) subjectEnum).toValue()];
		} else if (subjectEnum instanceof JoystickDPadButtonEnum && !((JoystickDPadButtonEnum) subjectEnum).isDriver()) {
			return dPadButtons[((JoystickDPadButtonEnum) subjectEnum).toValue()];
		} else {
			System.out.println("Subject not supported or incorrect.");
			return null;
		}
	}

	public void set(IInputEnum key, Object value) {
		if (key instanceof JoystickAxisEnum && ((JoystickAxisEnum) key).isDriver() == false) {
			axes[((JoystickAxisEnum) key).toValue()].setValue(value);
		} else if (key instanceof JoystickButtonEnum && ((JoystickButtonEnum) key).isDriver() == false) {
			buttons[((JoystickButtonEnum) key).toValue()].setValue(value);
		} else if (key instanceof JoystickDPadButtonEnum && ((JoystickDPadButtonEnum) key).isDriver()) {
			dPadButtons[((JoystickDPadButtonEnum) key).toValue()].setValue(value);
		} else {
			System.out.println("key not supported or incorrect.");
		}
	}

	public Object get(IInputEnum key) {
		if (key instanceof JoystickAxisEnum) {
			return axes[((JoystickAxisEnum) key).toValue()].getValueAsObject();
		} else if (key instanceof JoystickButtonEnum && ((JoystickButtonEnum) key).isDriver() == false) {
			return buttons[((JoystickButtonEnum) key).toValue()].getValueAsObject();
		} else if (key instanceof JoystickDPadButtonEnum && ((JoystickDPadButtonEnum) key).isDriver()) {
			return dPadButtons[((JoystickDPadButtonEnum) key).toValue()].getValueAsObject();
		} else {
			return new Double(-100);
		}
	}

	public void update() {
		for (int i = 0; i < axes.length; i++) {
			axes[i].updateValue();
		}
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].updateValue();
		}
		for (int i = 0; i < dPadButtons.length; i++) {
			dPadButtons[i].updateValue();
		}
	}

	public void pullData() {
		if (manipulatorJoystick instanceof IHardwareJoystick) {
			((IHardwareJoystick) manipulatorJoystick).pullData();
		}
		for (int i = 0; i < axes.length; i++) {
			// Invert the vertical axes so that full up is 1
			if (i % 2 == 0) {
				axes[i].setValue(manipulatorJoystick.getRawAxis(i));
			} else {
				axes[i].setValue(manipulatorJoystick.getRawAxis(i) * -1);
			}
		}
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setValue(manipulatorJoystick.getRawButton(i + 1));
		}

		// DPad Button Logic
		dPadDebouncers[JoystickDPadButtonEnum.DPAD_DOWN].update(new Boolean(axes[JoystickAxisEnum.DPAD_Y].getValue() < 0));
		dPadDebouncers[JoystickDPadButtonEnum.DPAD_UP].update(new Boolean(axes[JoystickAxisEnum.DPAD_Y].getValue() > 0));
		dPadDebouncers[JoystickDPadButtonEnum.DPAD_LEFT].update(new Boolean(axes[JoystickAxisEnum.DPAD_X].getValue() < 0));
		dPadDebouncers[JoystickDPadButtonEnum.DPAD_RIGHT].update(new Boolean(axes[JoystickAxisEnum.DPAD_X].getValue() > 0));

		dPadButtons[JoystickDPadButtonEnum.DPAD_DOWN].setValue(dPadDebouncers[JoystickDPadButtonEnum.DPAD_DOWN].getDebouncedValue());
		dPadButtons[JoystickDPadButtonEnum.DPAD_UP].setValue(dPadDebouncers[JoystickDPadButtonEnum.DPAD_UP].getDebouncedValue());
		dPadButtons[JoystickDPadButtonEnum.DPAD_LEFT].setValue(dPadDebouncers[JoystickDPadButtonEnum.DPAD_LEFT].getDebouncedValue());
		dPadButtons[JoystickDPadButtonEnum.DPAD_RIGHT].setValue(dPadDebouncers[JoystickDPadButtonEnum.DPAD_RIGHT].getDebouncedValue());
	}

	public void set(Object value) {
		this.set((IInputEnum) null, value);
	}

	public Subject getSubject() {
		return this.getSubject((ISubjectEnum) null);
	}

	public Object get() {
		return this.get((IInputEnum) null);
	}

	public void notifyConfigChange() {
	}
}
