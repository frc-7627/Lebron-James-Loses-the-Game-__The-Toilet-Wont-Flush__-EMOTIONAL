package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class ManCoralForward extends Command {
    private final NotSwerveSubsystem module;
    private final Bluetooth led;

    /**
    * Runs the Endefector Forward, until interrupted.
    * Used to manually push the gamepiece through the endefector 
    * without using the TOF sensors to stop automatically
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public ManCoralForward(NotSwerveSubsystem module, Bluetooth led) {
        this.module = module;
        this.led = led;
        
        addRequirements(module);
    }
    
    @Override
    public void initialize() {
        led.color("eggPlant");
        module.shimForward();
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
        led.bluetoothOFF();
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }
}
