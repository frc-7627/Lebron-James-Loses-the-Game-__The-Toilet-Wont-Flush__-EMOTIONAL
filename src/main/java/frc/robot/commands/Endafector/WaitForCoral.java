package frc.robot.commands.Endafector;

import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import com.playingwithfusion.TimeOfFlight;


public class WaitForCoral extends Command {
    private EndJoeBidenFactor module;
    Bluetooth bluetooth = new Bluetooth();
    EndJoeBidenFactor joe = new EndJoeBidenFactor();
    TimeOfFlight tof = new TimeOfFlight(32);

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
        System.out.println("TOF READING:" + tof.getRange());
        if (joe.CoralIn()){
        bluetooth.color("eggPlant");
    }
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
