package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Lebronavator;

/** See Constructor for details */
public class playSong extends Command{
    private Lebronavator module;
    private String songName;
    
    /**
    * Plays a user-specified song on the krakens built in 
    * the elevator until interrupted,
    * Song must be located in deply/midi and be a valid CHRP file
    *
    * @arg String songName - Name of CHRP file to play
    *
    * @requires Lebronavator
    * @version 1.0
    */
     public playSong(Lebronavator module, String songName) {
        this.module = module;
        addRequirements(module);
     }

    
    /** Run once at Command Start */
    @Override
    public void initialize()  {
        module.playSong(songName);
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
