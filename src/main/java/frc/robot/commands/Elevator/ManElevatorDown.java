package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.Lebronavator;

public class ManElevatorDown extends Command {
    private final Lebronavator module;
    private final Bluetooth led;

    public ManElevatorDown(Lebronavator module, Bluetooth led) {
        this.module = module;
        this.led = led;
    }
    
    @Override
    public void initialize() {
        led.color("eggPlant");
        module.shimDown();
    }

    @Override
    public void end(boolean interrupted) {
        module.StopMotor();
        led.bluetoothOFF();
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }
}
