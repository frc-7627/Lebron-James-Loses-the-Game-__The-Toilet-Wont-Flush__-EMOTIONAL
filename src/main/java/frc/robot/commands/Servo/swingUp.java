package frc.robot.commands.Servo;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.TemuMinecrafFilm;


/** See Constructor for details */
public class swingUp extends Command {
    private TemuMinecrafFilm module;
    private Bluetooth led;

    /**
    * Runs the Endefector Forward, until interrupted.
    * Used to manually push the gamepiece through the endefector 
    * without using the TOF sensors to stop automatically
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public swingUp(TemuMinecrafFilm module, Bluetooth led) {
        this.module = module;
        this.led = led;
        
        addRequirements(module);
    }
    
    /** Run once at Command Start */
    @Override
    public void initialize()  {
        module.moveUp();
        led.color("orange");
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
        return module.atGoal(); 
    }
}
