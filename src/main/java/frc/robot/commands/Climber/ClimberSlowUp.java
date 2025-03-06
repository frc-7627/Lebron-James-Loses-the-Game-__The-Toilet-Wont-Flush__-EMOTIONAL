package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.climber.AdultDiapers;

/** See Constructor for details */
public class ClimberSlowUp extends Command {
    private final AdultDiapers module;
    private final Bluetooth led;

    /**
    * Moves the Climber Up, but at a lower speed until interrupted
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public ClimberSlowUp(AdultDiapers module, Bluetooth led) {
        this.module = module;
        this.led = led;
    }
    
    /** Run once at Command Start */
    @Override
    public void initialize()  {
        led.color("eggPlant");
        module.slowMoveUp();
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
        module.stop();
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
