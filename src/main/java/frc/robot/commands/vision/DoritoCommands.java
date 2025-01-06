package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.helpers.vision.DriveBaseRotationAdjust;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Constants.OperatorConstants;

public class DoritoCommands {
    
    /**  Limelight Control Commands */
    public DoritoCommands(){
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        System.out.println("[DoritoCommands] Shuffleboard Updated");

        // Update Constants of Subsystems
        DriveBaseRotationAdjust.updateConstants();
    }

    /**
     * Turns the Robot to face an April Tag
     * @param drivebase Swerve Drivebase provided by YAGSL
     * @return
     */
    public Command AdjustDriveBase(SwerveSubsystem drivebase) {
        return new DriveBaseRotationAdjust(drivebase);
    }
}
