package frc.robot.commands.Endafector;

import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;

public class WaitForCoral extends Command {
    private EndJoeBidenFactor module;
    Bluetooth bluetooth = new Bluetooth();
    EndJoeBidenFactor joe = new EndJoeBidenFactor();

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
        bluetooth.eggPlant();
    }
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
