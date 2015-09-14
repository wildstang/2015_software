/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.inputs;

import org.wildstang.fw.inputmanager.IInput;
import org.wildstang.fw.inputmanager.IInputEnum;
import org.wildstang.fw.subject.IObserver;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.Subject;

/**
 *
 * @author Joey
 */
public class NoInput implements IInput {
	protected Subject subject = new Subject() {
		protected Object object = new Object();

		public void attach(IObserver observer) {
		}

		public Object getValueAsObject() {
			return object;
		}

	};

	public void set(IInputEnum key, Object value) {
	}

	public Object get(IInputEnum key) {
		return subject.getValueAsObject();
	}

	public void update() {
	}

	public void pullData() {
	}

	public void notifyConfigChange() {
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

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return subject;
	}
}
