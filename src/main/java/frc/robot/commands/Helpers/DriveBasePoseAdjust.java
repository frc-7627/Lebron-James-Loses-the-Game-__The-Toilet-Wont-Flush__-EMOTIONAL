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
import edu.wpi.first.wpilibj2.command.Commands;
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
    private Command driveCommand;

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
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

            int tagID = result.getBestTarget().getFiducialId();
            //Transform2d pose = new Transform2d(drivebase.getPose().getX(), drivebase.getPose().getY(), drivebase.getPose().getRotation());
            Pose2d new_pose = Vision.getAprilTagPose(tagID, new Transform2d(0.40, 0.2, new Rotation2d(Math.toRadians(180))));
            System.out.println(new_pose.toString());
            led.color("vomitGreen");
            driveCommand = drivebase.driveToPose(new_pose);
        }
        else {
            led.bluetoothOFF();
            driveCommand = Commands.none();
        }

        driveCommand.schedule();
    }

    @Override
    public void execute() {
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Interupted");
        driveCommand.cancel();
        driveCommand.end(true);
        driveCommand.end(false);
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
