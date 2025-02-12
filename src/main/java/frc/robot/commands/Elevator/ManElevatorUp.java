package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.Lebronavator;

/** See Constructor for details */
public class ManElevatorUp extends Command {
    private final Lebronavator module;
    private final Bluetooth led;

    /**
    * Moves the Elevator Down, at a constant speed until interrupted
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public ManElevatorUp(Lebronavator module, Bluetooth led) {
        this.module = module;
        this.led = led;
    }
    
    @Override
    public void initialize() {
        led.color("eggPlant");
        module.shimUp();
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
