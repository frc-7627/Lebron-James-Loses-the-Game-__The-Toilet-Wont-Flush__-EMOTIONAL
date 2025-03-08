package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Lebronavator;

/** See Constructor for details */
public class horn extends Command{
    private Lebronavator module;
    
    /**
    * Plays a steady tone using the kraken motors in the elevator,
    * until interrupted
    *
    * @requires Lebronavator
    * // @requires led - For visual manual control in use notification
    * @version 1.0
    */
     public horn(Lebronavator module) {
        this.module = module;
        addRequirements(module);
     }

    /** Run once at Command Start */
    @Override
    public void initialize() {
        module.playNote(440);
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
        module.resetControlMode();
    }
}
