package org.wildstang.fw.subject;

/**
 *
 * @author Nathan
 */
public interface IObservable {

	public Subject getSubject(ISubjectEnum subjectEnum);

	public Subject getSubject();
}
