package frc.robot.helpers.vision;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.swervedrive.SwerveSubsystem;


/**
 * Rotates the drivebase {@link frc.robot.subsystems.swervedrive.SwerveSubsystem}
 * to face the direction of an apriltag or detected object
 * given its position relative to the camera on Networktables 
 * {@link edu.wpi.first.networktables.NetworkTable}
 * 
 * @see edu.wpi.first.wpilibj2.command.Command;
 * @version 1.0
 */
public class DriveBaseRotationAdjust extends Command {
    private SwerveSubsystem drivebase;

    private NetworkTableEntry table_tx;
    private NetworkTableEntry table_status;
    private double tx;

    public DriveBaseRotationAdjust(SwerveSubsystem module) {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table =inst.getTable("Vision");

        table_tx = table.getEntry("NoteDetect/target_x");
        table_status = table.getEntry("NoteDetect/note_found");

        this.drivebase = module;
        addRequirements(drivebase);

        // Shuffleboard!
        //SmartDashboard.putNumber("Limelight/ArmAdjust/Mutiplyer", OperatorConstants.IntakeNoteAmps);
    }

    /** Updates Motor Speeds and limits from shuffleboard 
     * 
     * @see 
     * 
     * @return void
     * @version 1.0
    */
    public static void updateConstants() {
        //OperatorConstants.IntakeNoteAmps = SmartDashboard.getNumber("Intake/IntakeNote/Intake Note Amp Limit", OperatorConstants.IntakeNoteAmps);

        System.out.println("[DoritoCommands/DriveBaseRotationAdjust] Shuffleboard Updated");
    } 
    

    @Override
    public void initialize() {
        System.out.println("[DoritoCommands/DriveBaseRotationAdjust]] Rotating...");
    }

    @Override
    public void execute() {
        if(table_status.getDouble(0) == 1.0) {
            tx = table_tx.getDouble(0); 
            tx -= 80;
            System.out.println(tx);
            drivebase.drive(new Translation2d(0, 0), -tx * Math.PI / 180, false);
        }
        else {
            drivebase.drive(new Translation2d(0, 0), 0, false);
        }
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("[DoritoCommands/DriveBaseRotationAdjust] Interupted");
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
