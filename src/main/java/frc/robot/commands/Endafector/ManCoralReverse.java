package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.EndJoeBidenFactor;

/** See Constructor for details */
public class ManCoralReverse extends Command {
    private final EndJoeBidenFactor module;
    private final Bluetooth led;

    /**
    * Runs the Endefector Backward, until interrupted.
    * Used to manually push the gamepiece back up through the endefector 
    * in the event that the gamepiece is too far in
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public ManCoralReverse(EndJoeBidenFactor module, Bluetooth led) {
        this.module = module;
        this.led = led;
        
        addRequirements(module);
    }
    
    @Override
    public void initialize() {
        led.color("eggPlant");
        System.out.println("init");
        module.shimReverse();
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
        System.out.println("end");
        led.bluetoothOFF();
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }
}
