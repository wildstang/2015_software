package org.wildstang.nooutput;

import org.wildstang.outputmanager.IOutput;
import org.wildstang.outputmanager.IOutputEnum;
import org.wildstang.subject.ISubjectEnum;
import org.wildstang.subject.IntegerSubject;
import org.wildstang.subject.Subject;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * @author Nathan
 */
public class NoDoubleSolenoid implements IOutput {

	IntegerSubject subject;

	public NoDoubleSolenoid(String name, int module, int channel1, int channel2) {
		this.subject = new IntegerSubject(name);
		subject.setValue(DoubleSolenoid.Value.kForward_val);
	}

	public NoDoubleSolenoid(String name, int channel1, int channel2) {
		this(name, 1, channel1, channel2);
	}

	public void set(IOutputEnum key, Object value) {
		subject.setValue(((Integer) value).intValue());

	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return subject;
	}

	public Object get(IOutputEnum key) {
		return subject.getValueAsObject();
	}

	public void set(Object value) {
		this.set((IOutputEnum) null, value);
	}

	public Object get() {
		return this.get((IOutputEnum) null);
	}

	public void update() {
		subject.updateValue();
	}

	public void notifyConfigChange() {
		// Nothing to update here, since the config value only affect the
		// start state.
	}
}
