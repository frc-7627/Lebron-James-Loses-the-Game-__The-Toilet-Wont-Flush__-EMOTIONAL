package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class ManCoralReverse extends Command {
    private final NotSwerveSubsystem module;
    private final Bluetooth led;

    /**
    * Runs the Endefector Backward, until interrupted.
    * Used to manually push the gamepiece back up through the endefector 
    * in the event that the gamepiece is too far in
    *
    * @requires AdultDiapers
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public ManCoralReverse(NotSwerveSubsystem module, Bluetooth led) {
        this.module = module;
        this.led = led;
        
        addRequirements(module);
    }
    
    /** Run once at Command Start */
    @Override
    public void initialize() {
        led.color("eggPlant");
        System.out.println("init");
        module.shimReverse();
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
        System.out.println("MAN BCKWRD INTERRUPTED");
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
