package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.EndJoeBidenFactor;
import frc.robot.subsystems.arm.Lebronavator;
import frc.robot.subsystems.climber.AdultDiapers;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

public class CoachCommands {
    private final SwerveSubsystem   drivebase;
    private final Lebronavator      elevator;
    private final EndJoeBidenFactor endefector;
    private final AdultDiapers      climber;
    private final Bluetooth         led;
    
    CoachCommands(SwerveSubsystem drivebase, Lebronavator elevator, EndJoeBidenFactor endefector, AdultDiapers climber, Bluetooth led) {
        this.drivebase = drivebase;
        this.elevator = elevator;
        this.endefector = endefector;
        this.climber = climber;
        this.led = led;
    }

    public Command coachDisabled() {
        return Commands.runOnce(() -> { 
            System.out.println("[CoachCommands] Coach commands Disabled!");
          });
    }

    public Command breakDrivebase() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
                  drivebase.simulateFault();
                });
    }

    public Command breakVision() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            Vision.simulateFault();
          });
    }

    public Command breakElevator() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            elevator.simulateFault();
          });
    }

    public Command breakEndefector() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            endefector.simulateFault();
          });
    }

    public Command breakClimber() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
            climber.simulateFault();
          });
    }

    public Command breakLed() {
        if(!Constants.skibbidi_mode) return coachDisabled(); // Check for Coach Mode 
        else return Commands.runOnce(() -> { 
           led.simulateFault();
          });
    }
}
