package org.wildstang.outputmanager.outputs;

import org.wildstang.outputmanager.base.IOutput;
import org.wildstang.outputmanager.base.IOutputEnum;
import org.wildstang.subjects.base.BooleanSubject;
import org.wildstang.subjects.base.ISubjectEnum;
import org.wildstang.subjects.base.Subject;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author Nathan
 */
public class WsSolenoid implements IOutput {

	BooleanSubject subject;
	Solenoid solenoid;

	public WsSolenoid(String name, int module, int channel1) {
		this.subject = new BooleanSubject(name);
		subject.setValue(false);
		solenoid = new Solenoid(module, channel1);
		solenoid.set(false);

	}

	public WsSolenoid(String name, int channel1) {
		this(name, 0, channel1);
	}

	public void set(IOutputEnum key, Object value) {
		subject.setValue(((Boolean) value).booleanValue());
		System.out.println("solenoid set: " + subject.getName() + " to " + value);
	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return subject;
	}

	public Object get(IOutputEnum key) {
		return subject.getValueAsObject();
	}

	public void update() {
		subject.updateValue();
		solenoid.set(subject.getValue());
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
