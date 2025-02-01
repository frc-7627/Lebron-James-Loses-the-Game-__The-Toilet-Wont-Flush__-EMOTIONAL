package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.climber.AdultDiapers;

public class ClimberSlowDown extends Command {
    private final AdultDiapers module;
    private final Bluetooth led;

    public ClimberSlowDown(AdultDiapers module, Bluetooth led) {
        this.module = module;
        this.led = led;
    }
    
    @Override
    public void initialize() {
        led.color("eggPlant");
        module.slowMoveDown();
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
