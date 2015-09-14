package org.wildstang.fw.inputs;

import java.util.TimerTask;

import org.wildstang.fw.inputmanager.IInput;
import org.wildstang.fw.inputmanager.IInputEnum;
import org.wildstang.fw.subject.ISubjectEnum;
import org.wildstang.fw.subject.IntegerSubject;
import org.wildstang.fw.subject.Subject;

import edu.wpi.first.wpilibj.I2C.Port;

public class WsLIDAR implements IInput {

	IntegerSubject distanceSubject;
	private LidarSensor lidar;

	public WsLIDAR() {
		distanceSubject = new IntegerSubject();

		lidar = new LidarSensor(Port.kOnboard);
		lidar.start();
	}

	@Override
	public Subject getSubject(ISubjectEnum subjectEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject getSubject() {
		// TODO Auto-generated method stub
		return distanceSubject;
	}

	@Override
	public void set(IInputEnum key, Object value) {
		distanceSubject.setValue(((Double) value).doubleValue());

	}

	@Override
	public void set(Object value) {
		this.set(null, value);

	}

	@Override
	public Object get(IInputEnum key) {
		return distanceSubject;
	}

	@Override
	public Object get() {
		return distanceSubject.getValueAsObject();
	}

	@Override
	public void update() {
		distanceSubject.updateValue();

	}

	@Override
	public void pullData() {
		distanceSubject.setValue(lidar.getSmoothedDistance());
	}

	@Override
	public void notifyConfigChange() {
		// Nothing to do
	}

}
