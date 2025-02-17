package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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

    public Command breakDrivebase() {
        return Commands.runOnce(() -> { 
                  drivebase.simulateFault();
                });
    }

    public Command breakVision() {
        return Commands.runOnce(() -> { 
            Vision.simulateFault();
          });
    }

    public Command breakElevator() {
        return Commands.runOnce(() -> { 
            elevator.simulateFault();
          });
    }

    public Command breakEndefector() {
        return Commands.runOnce(() -> { 
            endefector.simulateFault();
          });
    }

    public Command breakClimber() {
        return Commands.runOnce(() -> { 
            climber.simulateFault();
          });
    }

    public Command breakLed() {
        return Commands.runOnce(() -> { 
           led.simulateFault();
          });
    }
}
