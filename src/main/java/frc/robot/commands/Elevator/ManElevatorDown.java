package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.Lebronavator;

/** See Constructor for details */
public class ManElevatorDown extends Command {
    private final Lebronavator module;
    private final Bluetooth led;

    /**
    * Moves the Elevator Down, at a constant speed until interrupted
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public ManElevatorDown(Lebronavator module, Bluetooth led) {
        this.module = module;
        this.led = led;
    }
    
    /** Run once at Command Start */
    @Override
    public void initialize()   {
        led.color("eggPlant");
        module.shimDown();
    }

     /** 
      * Run once at Command End 
      * 
      * @param interupted - False if Command ended by isFinished() 
      *                     True if by something else like 
      *                              letting go of a button
      */
    @Override
    public void end(boolean interrupted) {
        module.StopMotor();
        led.bluetoothOFF();
    }

    /** 
      * Checks if it's time to end the Command 
      * 
      * @return True - End the Command
      *         False - Keep running Periodic
      */
    @Override 
    public boolean isFinished() {
        return false; 
    }
}
