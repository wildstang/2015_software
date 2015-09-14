package org.wildstang.fw.outputs;

import org.wildstang.fw.outputmanager.IOutput;
import org.wildstang.fw.outputmanager.IOutputEnum;
import org.wildstang.fw.subject.BooleanSubject;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.Subject;

/**
 *
 * @author Nathan
 */
public class NoSolenoid implements IOutput {

	BooleanSubject subject;

	public NoSolenoid(String name, int module, int channel1) {
		this.subject = new BooleanSubject(name);
		subject.setValue(false);

	}

	public NoSolenoid(String name, int channel1) {
		this(name, 1, channel1);
	}

	public void set(IOutputEnum key, Object value) {
		subject.setValue(((Boolean) value).booleanValue());

	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return subject;
	}

	public Object get(IOutputEnum key) {
		return subject.getValueAsObject();
	}

	public void update() {
		subject.updateValue();
	}

	public void set(Object value) {
		this.set((IOutputEnum) null, value);
	}

	public Object get() {
		return this.get((IOutputEnum) null);
	}

	public void notifyConfigChange() {
		// Nothing to update here, since the config value only affect the
		// start state.
	}
}
