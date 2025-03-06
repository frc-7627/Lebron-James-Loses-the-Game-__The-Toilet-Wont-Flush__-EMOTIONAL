package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class EjectCoral extends Command {
    private NotSwerveSubsystem module;
    private Bluetooth led;

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
    public EjectCoral(NotSwerveSubsystem module, Bluetooth led) {
        this.module = module;
        this.led = led;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        System.out.println("Coral Eject");
        module.eject();
    }

    @Override
    public void end(boolean interrupted) {
        led.bluetoothOFF();
        module.stop();
    }

    @Override 
    public boolean isFinished() {
        return (module.CoralOut()); 
    }
}
