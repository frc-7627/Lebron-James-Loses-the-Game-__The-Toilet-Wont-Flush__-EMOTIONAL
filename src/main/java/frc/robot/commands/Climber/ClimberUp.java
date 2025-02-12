package frc.robot.commands.Climber;

import frc.robot.subsystems.climber.AdultDiapers;
import edu.wpi.first.wpilibj2.command.Command;

/** See Constructor for details */
public class ClimberUp extends Command {
    private AdultDiapers module;

    /**
    * Moves the Climber Up until interrupted
    *
    * @requires AdultDiapers
    * @version 1.0
    */
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
