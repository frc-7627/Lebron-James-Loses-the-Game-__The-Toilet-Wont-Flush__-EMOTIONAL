package frc.robot.commands.Helpers;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;

import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

/** See Constructor for details */
public class DriveBasePoseAdjust extends Command {
    private SwerveSubsystem drivebase;
    PhotonCamera camera = new PhotonCamera("Camera_Front");
    Bluetooth led;

    private double tx;
    private double y_offset;

    /**
     * Turns the Robot to face an April Tag, using PhotonVison detections
     * 
     * @requires drivebase Swerve Drivebase provided by YAGSL
     * @requires led visually indicated when an apriltag is being detected
     * @version 2.0
     */
    public DriveBasePoseAdjust(SwerveSubsystem module, Bluetooth led, double offset) {
        this.drivebase = module;
        this.led = led;
        this.y_offset = offset;
        addRequirements(drivebase);
        addRequirements(led);

        // Shuffleboard!
        //SmartDashboard.putNumber("Limelight/ArmAdjust/Mutiplyer", OperatorConstants.IntakeNoteAmps);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        //OperatorConstants.IntakeNoteAmps = SmartDashboard.getNumber("Intake/IntakeNote/Intake Note Amp Limit", OperatorConstants.IntakeNoteAmps);

        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Shuffleboard Updated");
    } 
    

    @Override
    public void initialize() {
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");
    }

    @Override
    public void execute() {
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

            int tagID = result.getBestTarget().getFiducialId();
            Pose2d new_pose = Vision.getAprilTagPose(tagID, new Transform2d(0, y_offset, new Rotation2d(0, 0)));
            System.out.println(new_pose.toString());
            drivebase.driveToPose(new_pose);
            led.color("vomitGreen");
        }
        else {
            led.bluetoothOFF();
            drivebase.drive(new Translation2d(0, 0), 0, false);
        }
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Interupted");
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
