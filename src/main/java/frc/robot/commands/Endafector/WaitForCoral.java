package frc.robot.commands.Endafector;

import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;

public class WaitForCoral extends Command {
    private EndJoeBidenFactor module;

    public WaitForCoral(EndJoeBidenFactor module) {
        this.module = module;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.stop();
    }

    @Override 
    public boolean isFinished() {
        return (module.CoralIn());
    }
}
