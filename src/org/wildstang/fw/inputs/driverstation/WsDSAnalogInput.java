package org.wildstang.fw.inputs.driverstation;

import org.wildstang.fw.inputmanager.IInput;
import org.wildstang.fw.inputmanager.IInputEnum;
import org.wildstang.fw.subject.DoubleSubject;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.Subject;

/**
 *
 * @author Nathan
 */
public class WsDSAnalogInput implements IInput {

	DoubleSubject analogValue;
	int channel;

	// By giving the input number in the constructor we can make this generic
	// for all analog inputs
	public WsDSAnalogInput(int channel) {
		this.analogValue = new DoubleSubject("AnalogInput" + channel);
		this.channel = channel;

		analogValue.setValue(0.0);
	}

	public void set(IInputEnum key, Object value) {
		double d = 0.0;
		if (value instanceof Boolean) {
			boolean bool = ((Boolean) value).booleanValue();
			d = (bool ? 1 : 0);
		} else {
			d = ((Double) value).doubleValue();
		}
		analogValue.setValue(d);

	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return analogValue;
	}

	public Object get(IInputEnum key) {
		return analogValue.getValueAsObject();
	}

	public void update() {
		analogValue.updateValue();
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

	public void pullData() {
		// analogValue.setValue(DriverStation.getInstance().getAnalogIn(channel));
	}

	public void notifyConfigChange() {
		// Nothing to update here, since the config value only affect the
		// start state.
	}
}
