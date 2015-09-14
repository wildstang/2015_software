package org.wildstang.inputs;

import java.util.TimerTask;

import org.wildstang.inputmanager.IInput;
import org.wildstang.inputmanager.IInputEnum;
import org.wildstang.subject.ISubjectEnum;
import org.wildstang.subject.IntegerSubject;
import org.wildstang.subject.Subject;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WsHallEffectInput implements IInput {

	private IntegerSubject activeSensor;
	I2C i2c;

	private int lastHallEffectSensor = -1;
	private int selectedHallEffectSensor = -1;

	private java.util.Timer updater;

	private Object lock = new Object();

	public WsHallEffectInput(Port port, int address) {
		activeSensor = new IntegerSubject();

		i2c = new I2C(port, address);

		updater = new java.util.Timer();

		// Update at 50Hz
		start(20);
	}

	@Override
	public Subject getSubject(ISubjectEnum subjectEnum) {
		return null;
	}

	@Override
	public Subject getSubject() {
		return activeSensor;
	}

	@Override
	public void set(IInputEnum key, Object value) {

	}

	@Override
	public void set(Object value) {
		activeSensor.setValue((Integer) value);

	}

	@Override
	public Object get(IInputEnum key) {
		return null;
	}

	@Override
	public Object get() {
		return activeSensor.getValueAsObject();
	}

	@Override
	public void update() {
		activeSensor.updateValue();

	}

	@Override
	public void pullData() {
		// This is all handled by the thread that polls the Arduino over I2C.
	}

	@Override
	public void notifyConfigChange() {

	}

	// Update the currently activated hall effect sensor
	private void updateSensor() {
		byte[] buffer = new byte[1];
		i2c.readOnly(buffer, 1);
		try {
			synchronized (lock) {
				this.selectedHallEffectSensor = buffer[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SmartDashboard.putNumber("READ HALL POSITION", selectedHallEffectSensor);
		if (selectedHallEffectSensor != lastHallEffectSensor) {
			System.out.println("READ HALL EFFECT: " + selectedHallEffectSensor);
		}
		lastHallEffectSensor = selectedHallEffectSensor;
		activeSensor.setValue(selectedHallEffectSensor);
	}

	// Start 10Hz polling
	public void start() {
		updater.scheduleAtFixedRate(new HallEffectUpdater(), 0, 100);
	}

	// Start polling for period in milliseconds
	public void start(int period) {
		updater.scheduleAtFixedRate(new HallEffectUpdater(), 0, period);
	}

	// Timer task to keep distance updated
	private class HallEffectUpdater extends TimerTask {
		public void run() {
			updateSensor();
		}
	}

}
