package frc.robot.commands.Endafector;

import frc.robot.subsystems.arm.NotSwerveSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;

/** See Constructor for details */
public class WaitForCoral extends Command {
    private final NotSwerveSubsystem module;
    private final Bluetooth led;

    /**
    * Stops the Endefector, waits and ends when the Rear TOF is triggered,
    * While ending, sets the led color to eggPlant to indicate posetion of
    * a gamepiece   
    *
    * Intended to be passed or used with other Commands or CommandGroup
    *
    * @requires AdultDiapers
    * @requires led - Indicates when robot is in possession of gamepiece
    * @version 1.0
    */
    public WaitForCoral(NotSwerveSubsystem module, Bluetooth led) {
        this.module = module;
        this.led = led;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.stop();
    }

    @Override
    public void execute(){
        if (module.CoralTouchFront()){
        led.color("eggPlant");
    }
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
