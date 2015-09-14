package org.wildstang.fw.subject;

/**
 *
 * @author Nathan
 */
public interface IObserver {

	public void acceptNotification(Subject subjectThatCaused);
}
