package frc.robot.commands.Helpers;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.Helpers.DriveBaseRotationAdjust;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Constants.OperatorConstants;

public class CaprisonCommands {
    
    /**  Limelight Control Commands */
    public CaprisonCommands(){
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        System.out.println("[CaprisonCommands] Shuffleboard Updated");

        // Update Constants of Subsystems
        DriveBaseRotationAdjust.updateConstants();
    }


    /**
     * Moves Arm up a certain amount depending on distance from an apriltag 
     * @param drivebase Swerve Drivebase provided by YAGSL
     * @return
     */
    public Command getPose(SwerveSubsystem drivebase) {     
        return Commands.runOnce(() -> {
          //  Pose2d pose = LimelightHelpers.getBotPose2d("");
           // drivebase.addVisionReading(pose,  Timer.getFPGATimestamp());
        }, drivebase);
    }

    /**
     * Moves Arm up a certain amount depending on distance from an apriltag 
     * @param drivebase Swerve Drivebase provided by YAGSL
     * @return
     */
   
    /**
     * Turns the Robot to face an April Tag
     * @param drivebase Swerve Drivebase provided by YAGSL
     * @return
     */
    public Command AdjustDriveBase(SwerveSubsystem drivebase, Bluetooth led) {
        System.out.println("Adjusting drivebase");
        return new DriveBaseRotationAdjust(drivebase, led);
    }
}
