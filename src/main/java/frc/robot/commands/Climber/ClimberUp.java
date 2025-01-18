package frc.robot.commands.Climber;

import frc.robot.subsystems.climber.AdultDiapers;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimberUp extends Command {
    private AdultDiapers module;

    public ClimberUp(AdultDiapers module) {
        this.module = module;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.moveUp();
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }
}
