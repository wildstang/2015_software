package org.wildstang.subsystems.lift;

import org.wildstang.config.DoubleConfigFileParameter;

public class LiftPreset {

	protected DoubleConfigFileParameter WANTED_VOLTAGE_CONFIG;
	protected double wantedVoltage;

	public LiftPreset(int defaultWantedVoltage, String presetName) {
		this.WANTED_VOLTAGE_CONFIG = new DoubleConfigFileParameter(this
				.getClass().getName() + "." + presetName,
				"wanted_voltage", defaultWantedVoltage);

		this.wantedVoltage = WANTED_VOLTAGE_CONFIG.getValue();
	}

	public double getWantedVoltage() {
		return wantedVoltage;
	}

	public void notifyConfigChange() {
		this.wantedVoltage = this.WANTED_VOLTAGE_CONFIG.getValue();
	}

	public String toString() {
		return "Wanted Voltage: " + this.wantedVoltage;
	}

}