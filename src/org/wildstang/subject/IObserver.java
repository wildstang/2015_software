package org.wildstang.subject;

/**
 *
 * @author Nathan
 */
public interface IObserver {

	public void acceptNotification(Subject subjectThatCaused);
}
