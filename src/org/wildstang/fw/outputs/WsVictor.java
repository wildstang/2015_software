package org.wildstang.fw.outputs;

import org.wildstang.fw.outputmanager.IOutput;
import org.wildstang.fw.outputmanager.IOutputEnum;
import org.wildstang.fw.subject.DoubleSubject;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.Subject;

import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Nathan
 */
public class WsVictor implements IOutput {

	DoubleSubject motorSpeed;
	Victor victor;

	// By giving the victor1 number in the constructor we can make this generic
	// for all digital victor1s
	public WsVictor(String name, int channel) {
		this.motorSpeed = new DoubleSubject(name);
		this.victor = new Victor(channel);

		motorSpeed.setValue(0.0);
	}

	public void set(IOutputEnum key, Object value) {
		motorSpeed.setValue(((Double) value).doubleValue());

	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return motorSpeed;
	}

	public Object get(IOutputEnum key) {
		return motorSpeed.getValueAsObject();
	}

	public void update() {
		motorSpeed.updateValue();
		this.victor.set(motorSpeed.getValue());
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