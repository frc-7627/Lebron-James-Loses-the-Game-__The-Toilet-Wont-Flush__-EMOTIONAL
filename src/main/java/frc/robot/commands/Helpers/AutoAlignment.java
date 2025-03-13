package frc.robot.commands.Helpers;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;

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
        PhotonCamera camera_right = new PhotonCamera("Camera_Right");
        PhotonCamera camera_left = new PhotonCamera("Camera_Right");
        private final SwerveSubsystem drivebase;
        private final Vision vision;
        private final Bluetooth led;
        private boolean leftcam;
        private double user_offset;
    
        private Command driveCommand;
    
        public AutoAlignment(SwerveSubsystem module, Bluetooth led, double offset, boolean leftcam) {
                this.drivebase = module;
                this.vision = drivebase.getVision();
                this.led = led;
                user_offset = offset;
                this.leftcam = leftcam;
                addRequirements(drivebase);
                addRequirements(led);
        }
    
        /** Run once at Command Start */
        @Override
        public void initialize() {
                DrivebaseConstants.x_offset = SmartDashboard.getNumber("Vision/x_offset", DrivebaseConstants.x_offset);
                DrivebaseConstants.y_offset = SmartDashboard.getNumber("Vision/y_offset", DrivebaseConstants.y_offset);
                DrivebaseConstants.y_offset_left = SmartDashboard.getNumber("Y_Offset_Left", DrivebaseConstants.y_offset_left);
                DrivebaseConstants.y_offset_right = SmartDashboard.getNumber("Y_Offset_Right", DrivebaseConstants.y_offset_right);
                System.out.println("x_offset: " + DrivebaseConstants.x_offset + " y_offset: " + DrivebaseConstants.y_offset);
                System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");
                
                @SuppressWarnings("removal")
                //var resultL = camera_left.getLatestResult();
                var resultR = camera_right.getLatestResult();

                /* PhotonPipelineResult result = resultR;
                // default to R if something happens
                if (resultL.hasTargets() && resultR.hasTargets()) {
                    if(resultL.getBestTarget().getPoseAmbiguity() > resultR.getBestTarget().getPoseAmbiguity()) {
                        result = resultR;
                    }
                    else if(resultL.getBestTarget().getPoseAmbiguity() <= resultR.getBestTarget().getPoseAmbiguity()) {
                        result = resultL;
                    }
                }
                else if(resultL.hasTargets() && !resultR.hasTargets()) {
                    result = resultL;
                } */
                PhotonPipelineResult result = resultR;
                if(leftcam) result = resultR; 

                if (result.hasTargets()) {
                        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

                        PhotonTrackedTarget bestTarget = result.getBestTarget();
                        for(PhotonTrackedTarget r : result.getTargets()) {
                            if(vision.getDistanceFromAprilTag(r.getFiducialId()) < 
                                    vision.getDistanceFromAprilTag(bestTarget.getFiducialId())) {
                                System.out.println("tag:" + r.getFiducialId());
                                bestTarget = r;
                            }
                        }
                        int tagID = bestTarget.getFiducialId();
                        //Transform2d pose = new Transform2d(drivebase.getPose().getX(), drivebase.getPose().getY(), drivebase.getPose().getRotation());
                        Pose2d newPose = Vision.getAprilTagPose(tagID, new Transform2d(DrivebaseConstants.x_offset, DrivebaseConstants.y_offset + user_offset, new Rotation2d(Math.toRadians(180))));
                        System.out.println(newPose.toString());
                        led.color("orange");
                        System.out.println("Goal Pose: " + newPose);
                        driveCommand = drivebase.driveToPose(newPose); //, drivebase.getPose());
                }
                else {
                        led.bluetoothOFF();
                        driveCommand = Commands.none();//new PathPlannerAuto(Commands.none(), drivebase.getPose());
                }    
            driveCommand.initialize();
        }
    
        @Override
        public void execute() {
            driveCommand.execute();
    
        }
    
        /**
         * Moves the Climber Up, but at a lower speed until interrupted
         *
         * @requires AdultDiapers
         * @requires led - For visual manual control in use notification
         * @version 1.0
         */
        @Override
        public void end(boolean interrupted) {
            System.out.println("interupted!: " + interrupted + " Robot Pose: " + drivebase.getPose());
            led.bluetoothOFF();
            driveCommand.end(interrupted);
        }
        
        /** 
          * Checks if it's time to end the Command 
          * 
          * @return True - End the Command
          *         False - Keep running Periodic
          */
        @Override
        public boolean isFinished() {
            return driveCommand.isFinished();
        }
    }