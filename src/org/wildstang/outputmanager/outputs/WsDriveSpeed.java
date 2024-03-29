package org.wildstang.outputmanager.outputs;

import org.wildstang.outputmanager.base.IOutput;
import org.wildstang.outputmanager.base.IOutputEnum;
import org.wildstang.subjects.base.DoubleSubject;
import org.wildstang.subjects.base.ISubjectEnum;
import org.wildstang.subjects.base.Subject;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class WsDriveSpeed implements IOutput {

	DoubleSubject motorSpeed;
	Victor victor1;
	Victor victor2;

	// By giving the victor1 number in the constructor we can make this generic
	// for all digital victor1s
	public WsDriveSpeed(String name, int channel1, int channel2) {
		this.motorSpeed = new DoubleSubject(name);
		this.victor1 = new Victor(channel1);
		this.victor2 = new Victor(channel2);

		motorSpeed.setValue(0.0);
	}

	public void set(IOutputEnum key, Object value) {
		motorSpeed.setValue(((Double) value).doubleValue());
		SmartDashboard.putNumber(motorSpeed.getName() + "value: ", ((Double) value).doubleValue());

	}

	public Subject getSubject(ISubjectEnum subjectEnum) {
		return motorSpeed;
	}

	public Object get(IOutputEnum key) {
		return motorSpeed.getValueAsObject();
	}

	public void update() {
		motorSpeed.updateValue();
		this.victor1.set(motorSpeed.getValue());
		this.victor2.set(motorSpeed.getValue());
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
