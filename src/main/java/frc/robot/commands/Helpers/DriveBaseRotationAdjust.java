package frc.robot.commands.Helpers;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class DriveBaseRotationAdjust extends Command {
    private SwerveSubsystem drivebase;
    PhotonCamera camera = new PhotonCamera("Camera_Front");
    Bluetooth led;

    private double tx;

    public DriveBaseRotationAdjust(SwerveSubsystem module, Bluetooth led) {
        this.drivebase = module;
        this.led = led;
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
            System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Rotating...");
            tx = result.getBestTarget().getYaw();
            drivebase.drive(new Translation2d(0, 0), -tx * Math.PI / 180, false);
            led.vomitGreen();
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
