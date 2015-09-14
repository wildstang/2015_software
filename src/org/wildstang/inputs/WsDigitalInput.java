package org.wildstang.inputs;

import org.wildstang.inputmanager.IInput;
import org.wildstang.inputmanager.IInputEnum;
import org.wildstang.subject.BooleanSubject;
import org.wildstang.subject.ISubjectEnum;
import org.wildstang.subject.Subject;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Nathan
 */
public class WsDigitalInput implements IInput {

	BooleanSubject digitalValue;
	DigitalInput input;

	// By giving the input number in the constructor we can make this generic
	// for all digital inputs
	public WsDigitalInput(int channel) {
		this.digitalValue = new BooleanSubject("DigitalInput" + channel);
		this.input = new DigitalInput(channel);

		digitalValue.setValue(false);
	}

	public void set(IInputEnum key, Object value) {
		digitalValue.setValue(((Boolean) value).booleanValue());

	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return digitalValue;
	}

	public Object get(IInputEnum key) {
		return digitalValue.getValueAsObject();
	}

	public void update() {
		digitalValue.updateValue();
	}

	public void pullData() {
		digitalValue.setValue(input.get());
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
		// Nothing to update here, since the config value only affect the
		// start state.
	}
}
