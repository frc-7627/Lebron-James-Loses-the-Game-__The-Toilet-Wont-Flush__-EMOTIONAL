package frc.robot.commands.Endafector;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.EndJoeBidenFactor;

import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCoral extends Command {
    private EndJoeBidenFactor module;
    private Bluetooth led;

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
        if(module.CoralIn()) led.eggPlant();
    }

    @Override
    public void end(boolean interrupted) {
        module.stop();
        led.orange();
    }

    @Override 
    public boolean isFinished() {
        return (!module.CoralOut());
    }
}
