package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class BobbyCoral extends Command {
    private NotSwerveSubsystem module;

    /**
    * Runs the Endafector Forwards, in order to push the gampiece
    * out the back for scoring. Automatticlly ends when a gamepiece crosses 
    * the Front TOF sensor inside the Endafector, indicating it has 
    * left contact with the robot. 
    *
    * Uses leds to indicate the status of the gamepiece, 
    * the statuses are as follows:
    *   blinking eggPlant: Endafector Running: No gamepiece detected
    *   vomitGreen: Endafector Stopped: Gamepiece is secure
    *   led off: Command interupted
    *
    * @requires AdultDiapers
    * @requires led - For Visual notifications
    * @version 1.0
    */
    public BobbyCoral(NotSwerveSubsystem module) {
        this.module = module;
        addRequirements(module);
     }

    /** Run once at Command Start */
    @Override
    public void initialize()  {
    }

    @Override
    public void execute() {
        module.idle();
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
        return (module.CoralTouchBack()); 
    }
}
