/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wildstang.fw.auto.testprograms;

import org.wildstang.fw.auto.AutoManager;
import org.wildstang.fw.auto.AutoProgram;
import org.wildstang.fw.config.DoubleConfigFileParameter;
import org.wildstang.yearly.auto.steps.drivebase.StepQuickTurn;
import org.wildstang.yearly.auto.steps.drivebase.StepStartDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepStopDriveUsingMotionProfile;
import org.wildstang.yearly.auto.steps.drivebase.StepWaitForDriveMotionProfile;

/**
 *
 * @author Joey
 */
/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
public class AutoProgramDrivePatterns extends AutoProgram {

	private DoubleConfigFileParameter firstAngle, secondAngle, firstDriveDistance, firstDriveVelocity, secondDriveDistance, secondDriveVelocity;

	public void defineSteps() {
		firstAngle = new DoubleConfigFileParameter(this.getClass().getName(), AutoManager.getInstance().getStartPosition().toConfigString() + ".FirstRelativeAngle", 45);
		secondAngle = new DoubleConfigFileParameter(this.getClass().getName(), AutoManager.getInstance().getStartPosition().toConfigString() + ".SecondRelativeAngle", 45);
		firstDriveDistance = new DoubleConfigFileParameter(this.getClass().getName(), AutoManager.getInstance().getStartPosition().toConfigString() + ".FirstDriveDistance", -100);
		firstDriveVelocity = new DoubleConfigFileParameter(this.getClass().getName(), AutoManager.getInstance().getStartPosition().toConfigString() + ".FirstDriveVelocity", 0.0);
		secondDriveDistance = new DoubleConfigFileParameter(this.getClass().getName(), AutoManager.getInstance().getStartPosition().toConfigString() + ".SecondDriveDistance", -30);
		secondDriveVelocity = new DoubleConfigFileParameter(this.getClass().getName(), AutoManager.getInstance().getStartPosition().toConfigString() + ".SecondDriveVelocity", 0.0);

		addStep(new StepStartDriveUsingMotionProfile(firstDriveDistance.getValue(), firstDriveVelocity.getValue()));
		addStep(new StepWaitForDriveMotionProfile());
		addStep(new StepStopDriveUsingMotionProfile());
		addStep(new StepQuickTurn(firstAngle.getValue()));
		addStep(new StepStartDriveUsingMotionProfile(secondDriveDistance.getValue(), secondDriveVelocity.getValue()));
		addStep(new StepWaitForDriveMotionProfile());
		addStep(new StepStopDriveUsingMotionProfile());
		addStep(new StepQuickTurn(secondAngle.getValue()));
	}

	public String toString() {
		return "Testing drive patterns for after shoot 5";
	}
}