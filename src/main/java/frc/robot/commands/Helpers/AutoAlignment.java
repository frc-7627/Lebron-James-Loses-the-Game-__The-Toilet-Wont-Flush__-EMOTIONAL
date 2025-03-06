package frc.robot.commands.Helpers;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.DrivebaseConstants;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

public class AutoAlignment extends Command {
        PhotonCamera camera = new PhotonCamera("Camera_Front");
        private final SwerveSubsystem drivebase;
        private final Bluetooth led;
        private double user_offset;
    
        private Command driveCommand;
    
        public AutoAlignment(SwerveSubsystem module, Bluetooth led, double offset) {
                this.drivebase = module;
                this.led = led;
                user_offset = offset;
                addRequirements(drivebase);
                addRequirements(led);
        }
    
        @Override
        public void initialize() {
                DrivebaseConstants.x_offset = SmartDashboard.getNumber("Vision/x_offset", DrivebaseConstants.x_offset);
                DrivebaseConstants.y_offset = SmartDashboard.getNumber("Vision/y_offset", DrivebaseConstants.y_offset);
                System.out.println("x_offset: " + DrivebaseConstants.x_offset + " y_offset: " + DrivebaseConstants.y_offset);
                System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");
                
                @SuppressWarnings("removal")
                var result = camera.getLatestResult();
                if (result.hasTargets()) {
                        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

                        int tagID = result.getBestTarget().getFiducialId();
                        //Transform2d pose = new Transform2d(drivebase.getPose().getX(), drivebase.getPose().getY(), drivebase.getPose().getRotation());
                        Pose2d newPose = Vision.getAprilTagPose(tagID, new Transform2d(DrivebaseConstants.x_offset, DrivebaseConstants.y_offset + user_offset, new Rotation2d(Math.toRadians(180))));
                        System.out.println(newPose.toString());
                        led.color("vomitGreen");
                        driveCommand = drivebase.driveToPose(newPose);
                }
                else {
                        led.bluetoothOFF();
                        driveCommand = Commands.none();
                }    
            driveCommand.initialize();
        }
    
        @Override
        public void execute() {
            driveCommand.execute();
    
        }
    
        @Override
        public void end(boolean interrupted) {
            driveCommand.end(interrupted);
        }
        
        @Override
        public boolean isFinished() {
            return driveCommand.isFinished();
        }
    }