package frc.robot.commands.Endafector;

import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;

public class EjectCoral extends Command {
    private EndJoeBidenFactor module;

    public EjectCoral(EndJoeBidenFactor module) {
        this.module = module;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.eject();
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
    }

    @Override 
    public boolean isFinished() {
        return (module.CoralOut()); 
    }
}
