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

    @Override
    public void initialize() {
        System.out.println("Coral Eject");
        module.idle();
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
    }

    @Override 
    public boolean isFinished() {
        return (module.CoralTouchBack()); 
    }
}
