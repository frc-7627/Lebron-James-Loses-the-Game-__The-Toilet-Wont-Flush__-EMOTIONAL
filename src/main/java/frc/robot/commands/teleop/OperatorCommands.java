package frc.robot.commands.teleop;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Elevator.ElevatorMove;
import frc.robot.commands.Endafector.EjectCoral;
import frc.robot.commands.Endafector.IntakeCoral;
import frc.robot.commands.Helpers.AutoAlignment;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;
import frc.robot.subsystems.arm.Lebronavator;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

/** See Constructor for details */
public class OperatorCommands {
    private final Lebronavator elevator;
    private final NotSwerveSubsystem outake;
    private final Bluetooth led;
    private final SwerveSubsystem drivebase;
    PhotonCamera camera = new PhotonCamera("Camera_Front");

    /**
    * A collection of CommandGroups, that factor in
    * Commands from different subsystems to
    * complete a single robot action,
    *
    * such as moving the elevator up, and then ejecting a
    * gamepiece in order to score a point 
    *
    */
    public OperatorCommands(Lebronavator module1, NotSwerveSubsystem module2, Bluetooth led, SwerveSubsystem drivebase) {
        this.elevator = module1;
        this.outake   = module2;
        this.led      = led;
        this.drivebase = drivebase;

        new IntakeCoral(module2, led);
    }
    
    /**
    * Stops the endefector, and stows the elevator
    *
    * @requires elevator
    * @requires endafector
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public Command AutoStow() {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, 0),
            new IntakeCoral(outake, led)
        );
    }

    /**
    * Brings the elevtor up to a specied scoring position,
    * and then runs the endefector forward until a gamepiece
    * is scored
    *
    * @requires elevator
    * @requires endafector
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    private Command AutoScore(int pos) {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, pos),
            new WaitCommand(0.0), // Reduce Jitter
            new EjectCoral(outake, led) 
        );
    }

    /** Scores gamepeice in the L1 Position 
    * @see AutoScore
    */
    public Command AutoScoreL1() {
        return AutoScore(1);
    }

    /** Scores gamepeice in the L2 Position 
    * @see AutoScore
    */
    public Command AutoScoreL2() {
        return AutoScore(2);
    }

    /** Scores gamepeice in the L3 Position 
    * @see AutoScore
    */
    public Command AutoScoreL3() {
        return AutoScore(3);
    }

    /** Scores gamepeice in the L4 Position 
    * @see AutoScore
    */
    public Command AutoScoreL4() {
        return AutoScore(4);
    }

    public Command AutoEjectL4() {
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                new EjectCoral(outake, led),
                new ElevatorMove(elevator, 5),
                new WaitCommand(1.0)
            ),
            //new ElevatorMove(elevator, 5),
            new ParallelRaceGroup(
                    new EjectCoral(outake, led),
                    new WaitCommand(0.75)
                ),
            new ElevatorMove(elevator, 0)
        );
    }

    public Command AutoEjectL4ForAuto() {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, 4),
            new ParallelRaceGroup(
                new EjectCoral(outake, led),
                new ElevatorMove(elevator, 5),
                new WaitCommand(.75)
            ),
            //new ElevatorMove(elevator, 5),
            new ParallelRaceGroup(
                    new EjectCoral(outake, led),
                    new WaitCommand(0.75)
                )
        );
    }

    public Command AutoFullEjectL4(){
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, 4),
            new ParallelRaceGroup(
                
                new EjectCoral(outake, led),
                new WaitCommand(.25)
            ),
            AutoEjectL4()
        );
    }

    public Command driveToPoseOffset() {
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");
        @SuppressWarnings("removal")
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

            int tagID = result.getBestTarget().getFiducialId();
            //Transform2d pose = new Transform2d(drivebase.getPose().getX(), drivebase.getPose().getY(), drivebase.getPose().getRotation());
            Pose2d new_pose = Vision.getAprilTagPose(tagID, new Transform2d(0.40, 0.2, new Rotation2d(Math.toRadians(180))));
            System.out.println(new_pose.toString());
            led.color("vomitGreen");
            return(drivebase.driveToPose(new_pose));
        }
        else {
            led.bluetoothOFF();
            return Commands.none();
        }
    }

    public Command RIPOperatorLeft() {
        return new SequentialCommandGroup(
            new AutoAlignment(drivebase, led, Constants.DrivebaseConstants.y_offset_left, true),
            new ElevatorMove(elevator, 4),
            AutoEjectL4()
        );
    }

    public Command RIPOperatorRight() {
        return new SequentialCommandGroup(
            new AutoAlignment(drivebase, led, Constants.DrivebaseConstants.y_offset_right, false),
            new ElevatorMove(elevator, 4),
            AutoEjectL4()
        );
    }
}
