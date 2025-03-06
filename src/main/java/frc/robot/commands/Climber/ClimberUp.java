package frc.robot.commands.Climber;

import frc.robot.subsystems.Bluetooth;
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
    public ClimberUp(AdultDiapers module, Bluetooth led) {
        this.module = module;
        addRequirements(module);
     }

    /** Run once at Command Start */
    @Override
    public void initialize()  {
        module.moveUp();
    }

     /** 
      * Run once at Command End 
      * 
      * @param interupted - False if Command ended by isFinished() 
      *                     True if by something else like 
      *                              letting go of a button
      */
    @Override
    public void end(boolean interrupted) {
        module.stop();
    }

    /** 
      * Checks if it's time to end the Command 
      * 
      * @return True - End the Command
      *         False - Keep running Periodic
      */
    @Override 
    public boolean isFinished() {
        return false; 
    }
}
