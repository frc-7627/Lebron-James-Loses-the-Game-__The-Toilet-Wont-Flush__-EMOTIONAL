package frc.robot.commands.Endafector;

import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;

/** See Constructor for details */
public class WaitForCoral extends Command {
    private EndJoeBidenFactor module;
    Bluetooth bluetooth = new Bluetooth();
    EndJoeBidenFactor joe = new EndJoeBidenFactor();

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
    public WaitForCoral(EndJoeBidenFactor module) {
        this.module = module;
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.stop();
    }

    @Override
    public void execute(){
        if (joe.CoralIn()){
        bluetooth.color("eggPlant");
    }
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
