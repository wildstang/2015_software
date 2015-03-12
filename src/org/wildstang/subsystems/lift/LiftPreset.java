package org.wildstang.subsystems.lift;

import org.wildstang.config.DoubleConfigFileParameter;
import org.wildstang.config.IntegerConfigFileParameter;

public class LiftPreset {
	
	protected String presetName;

	protected DoubleConfigFileParameter WANTED_VOLTAGE_CONFIG;
	protected IntegerConfigFileParameter HALL_EFFECT_INDEX_CONFIG;
	protected double wantedVoltage;
	protected int hallEffectIndex;

	public LiftPreset(String presetName, double defaultWantedVoltage, int defaultHallEffectIndex) {
		this.presetName = presetName;
		
		this.WANTED_VOLTAGE_CONFIG = new DoubleConfigFileParameter(this.getClass().getName() + "." + presetName, "wanted_voltage", defaultWantedVoltage);
		this.HALL_EFFECT_INDEX_CONFIG = new IntegerConfigFileParameter(this.getClass().getName() + "." + presetName, "hall_effect_index", defaultHallEffectIndex);
		
		System.out.println(WANTED_VOLTAGE_CONFIG.getFullParamName());
		System.out.println(HALL_EFFECT_INDEX_CONFIG.getFullParamName());

		this.wantedVoltage = WANTED_VOLTAGE_CONFIG.getValue();
		this.hallEffectIndex = HALL_EFFECT_INDEX_CONFIG.getValue();
	}

	public double getWantedVoltage() {
		return wantedVoltage;
	}

	public int getHallEffectIndex() {
		return hallEffectIndex;
	}

	public void notifyConfigChange() {
		this.wantedVoltage = WANTED_VOLTAGE_CONFIG.getValue();
		this.hallEffectIndex = HALL_EFFECT_INDEX_CONFIG.getValue();
	}

	public String toString() {
		return "Name: " + presetName + "; Wanted Voltage: " + this.wantedVoltage + "; Hall Effect Index: " + this.hallEffectIndex;
	}

}