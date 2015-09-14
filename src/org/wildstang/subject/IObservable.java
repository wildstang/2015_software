package org.wildstang.subject;

/**
 *
 * @author Nathan
 */
public interface IObservable {

	public Subject getSubject(ISubjectEnum subjectEnum);

	public Subject getSubject();
}
