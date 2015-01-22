package org.wildstang.autonomous.steps.hooks;

import org.wildstang.autonomous.steps.AutonomousStep;
import org.wildstang.subsystems.Hooks;
import org.wildstang.subsystems.base.SubsystemContainer;

public class AutonomousStepSetHooksState extends AutonomousStep
{
    protected boolean open;
    
    public AutonomousStepSetHooksState(boolean open)
    {
        this.open = open;
    }
    
    public void initialize()
    {
        ((Hooks) SubsystemContainer.getInstance().getSubsystem(SubsystemContainer.HOOKS_SOLENOID_INDEX)).setHooksState(open);
        finished = true;
    }

    public void update()
    {
    }

    public String toString()
    {
        return "Set hooks " + (open ? "Open" : "Closed");
    }
    
}