package org.wildstang.fw.outputs;

import org.wildstang.fw.outputmanager.IOutput;
import org.wildstang.fw.outputmanager.IOutputEnum;
import org.wildstang.fw.subject.BooleanSubject;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.Subject;

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
