package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;
import frc.robot.subsystems.arm.Lebronavator;
import frc.robot.subsystems.climber.AdultDiapers;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

/** See Constructor for details */
public class CoachCommands {
    private final SwerveSubsystem   drivebase;
    private final Lebronavator      elevator;
    private final NotSwerveSubsystem endefector;
    private final AdultDiapers      climber;
    private final Bluetooth         led;
    
    /**
     * A collecton of Commands that replicate subsystem failures, 
     * ideally useful for drive-practice and only drive-practice
     * 
     * @param drivebase - The robot's SwerveSubsystem
     * @param elevator - The robot's Lebronavator Subsystem
     * @param endefector - The robot's EndJoeBiden Subsystem
     * @param climber - The robot's AdultDiapers Subsystem
     * @param led - The robot's Bluetooth subsystem
     */
    public CoachCommands(SwerveSubsystem drivebase, Lebronavator elevator, NotSwerveSubsystem endefector, AdultDiapers climber, Bluetooth led) {
        this.drivebase = drivebase;
        this.elevator = elevator;
        this.endefector = endefector;
        this.climber = climber;
        this.led = led;
    }


    /**
    * Logs that this Command was run, typically called when
    * skibbidi_mode in {@link frc.robot.constants} is set to false
    * and therefore coach commands should not be called
    *
    * @returns Command
    * @version 1.0
    */
    private Command coachDisabled() {
        return Commands.runOnce(() -> { 
            System.out.println("[CoachCommands] Coach commands Disabled!");
          });
    }

    /**
    * Breaks the drivebase Subsystem, check SwerveSubsystem.simulateFault 
    * for details {@link frc.robot.subsystems.swervedrive.old.SwerveSubsystem.simulateFault}
    *
    * @requires SwerveSubsystem
    *
    * @returns Command
    * @version 1.0
    */
    public Command breakDrivebase() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
                  drivebase.simulateFault();
                });
    }

    /**
    * Breaks the Vision Subsystem, check Vision.simulateFault 
    * for details {@link frc.robot.subsystems.swervedrive.Vision.simulateFault}
    *
    * @requires Vision
    *
    * @returns Command
    * @version 1.0
    */
    public Command breakVision() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            Vision.simulateFault();
          });
    }

    /**
    * Breaks the Elevator Subsystem, check Lebronavator.simulateFault 
    * for details {@link frc.robot.subsystems.arm.Lebronavator.simulateFault}
    *
    * @requires Lebronavator
    *
    * @returns Command
    * @version 1.0
    */
    public Command breakElevator() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            elevator.simulateFault();
          });
    }

    /**
    * Breaks the Endefector Subsystem, check EndJoeBidenFactor.simulateFault 
    * for details {@link frc.robot.subsystems.arm.NotSwerveSubsystem.simulateFault}
    *
    * @requires EndJoeBidenFactor
    *
    * @returns Command
    * @version 1.0
    */
    public Command breakEndefector() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
          endefector.simulateFault();
          });
    }

    /**
    * Breaks the Climber Subsystem, check AdultDiapers.simulateFault 
    * for details {@link frc.robot.subsystems.climber.AdultDiapers.simulateFault}
    *
    * @requires AdultDiapers
    *
    * @returns Command
    * @version 1.0
    */
    public Command breakClimber() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            climber.simulateFault();
          });
    }

    /**
    * Breaks the Led Subsystem, check Bluetooth.simulateFault 
    * for details {@link frc.robot.subsystems.Bluetooth.simulateFault}
    *
    * @requires Bluetooth
    *
    * @returns Command
    * @version 1.0
    */
    public Command breakLed() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
           led.simulateFault();
          });
    }
}
