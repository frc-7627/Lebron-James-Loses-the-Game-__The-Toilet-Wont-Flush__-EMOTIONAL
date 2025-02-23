package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.helpers.vision.DriveBaseRotationAdjust;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

/**
 * A set of Commands {@link edu.wpi.first.wpilibj2.command.Command}
 * to be called either by automoous code or a button press
 * 
 * This class specifically contains Commands related to apriltag
 * and object detections provided by wpilibpi
 * 
 * @version 1.0
 */
public class DoritoCommands {
    
    public DoritoCommands(){
    }

    /**
     *  Updates Motor Speeds and limits from shuffleboard
     *  and makes all related Helper commands update as well 
     * 
     * @return void
     * @version 1.0
    */
    public void updateConstants() {
        System.out.println("[DoritoCommands] Shuffleboard Updated");

        // Update Constants of Subsystems
        DriveBaseRotationAdjust.updateConstants();
    }

    /**
     * Turns the Robot to face an April Tag
     * @param drivebase Swerve Drivebase {@link frc.robot.subsystems.swervedrive.old.SwerveSubsystem} provided by YAGSL
     * @return edu.wpi.first.wpilibj2.command.Command
     * 
     * @version 1.0
     */
    public Command AdjustDriveBase(SwerveSubsystem drivebase) {
        return new DriveBaseRotationAdjust(drivebase);
    }
}
