/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.outputs;

import org.wildstang.fw.outputmanager.IOutputEnum;
import org.wildstang.fw.outputmanager.IServo;
import org.wildstang.fw.subject.DoubleSubject;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.Subject;

/**
 *
 * @author Rick a.k.a. Batman
 */
public class NoServo implements IServo {

	DoubleSubject position;
	private boolean angleSet;

	public NoServo(String name, int channel) {
		this.position = new DoubleSubject(name);
		angleSet = false;
	}

	public void set(IOutputEnum key, Object value) {
		position.setValue(((Double) value).doubleValue());
		angleSet = false;
	}

	public void setAngle(IOutputEnum key, Object value) {
		position.setValue(((Double) value).doubleValue());
		angleSet = true;
	}

	public Object get(IOutputEnum key) {
		return this.position.getValueAsObject();
	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return this.position;
	}

	public void update() {
		this.position.updateValue();
	}

	public void set(Object value) {
		this.set((IOutputEnum) null, value);
	}

	public Object get() {
		return this.get((IOutputEnum) null);
	}

	public void notifyConfigChange() {
	}
}