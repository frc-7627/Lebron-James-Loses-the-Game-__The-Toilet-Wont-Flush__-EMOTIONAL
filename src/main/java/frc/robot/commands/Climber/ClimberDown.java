package frc.robot.commands.Climber;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.climber.AdultDiapers;
import edu.wpi.first.wpilibj2.command.Command;

/** See Constructor for details */
public class ClimberDown extends Command {
    private AdultDiapers module;


    /**
    * Moves the Climber Down until interrupted
    *
    * @requires AdultDiapers
    * @version 1.0
    */
    public ClimberDown(AdultDiapers module, Bluetooth led) {
        this.module = module;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.moveDown();
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
