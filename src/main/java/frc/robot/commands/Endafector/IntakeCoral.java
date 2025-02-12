package frc.robot.commands.Endafector;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;

/** See Constructor for details */
public class IntakeCoral extends Command {
    private EndJoeBidenFactor module;
    private Bluetooth led;

    /**
    * Runs the Endafector Forward, in order to intake a gamepiece
    * Automatticlly ends when a gamepiece crosses the Rear TOF sensor,
    * inside the Endafector, indicating it is now securely inside the
    * robot. 
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
    public IntakeCoral(EndJoeBidenFactor module, Bluetooth led) {
        this.module = module;
        this.led = led;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        led.bluetoothOFF();
        module.load();
    }

    @Override
    public void execute(){
        //if(module.CoralIn()) 
        //led.color("eggPlant");
        led.blink("eggPlant");
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
        if(!interrupted) led.color("vomitGreen");
        else led.bluetoothOFF();
    }

    @Override 
    public boolean isFinished() {
        return (module.CoralOut());
    }
}
