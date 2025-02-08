// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;

//import org.photonvision.PhotonCamera;

import swervelib.SwerveInputStream;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */

public class RobotContainer
{

  // Replace with CommandPS4Controller or CommandJoystick if needed
  final         CommandXboxController driverXbox = new CommandXboxController(0);
  final         CommandXboxController operatorXbox = new CommandXboxController(1);
  final         CommandXboxController coachXbox = new CommandXboxController(2);

  
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem       drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                                "swerve/"));

  //private final LimeLight Limelight = new LimeLight();
  //private final PhotonCamera apriltagCam = new PhotonCamera("Camera_Front");
  // Create pathplanner auto chooser                                                                              
  private final SendableChooser<Command> autoChooser;



  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> driverXbox.getLeftY() * -1,
                                                                () -> driverXbox.getLeftX() * -1)
                                                            .withControllerRotationAxis(driverXbox::getRightX)
                                                            .deadband(OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(driverXbox::getRightX,
                                                                                            driverXbox::getRightY)
                                                           .headingWhile(true);

  /**
   * Clone's the angular velocity input stream and converts it to a robotRelative input stream.
   */
  SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
                                                             .allianceRelativeControl(false);

  SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                        () -> -driverXbox.getLeftY(),
                                                                        () -> -driverXbox.getLeftX())
                                                                    .withControllerRotationAxis(() -> driverXbox.getRawAxis(
                                                                        2))
                                                                    .deadband(OperatorConstants.DEADBAND)
                                                                    .scaleTranslation(0.8)
                                                                    .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleKeyboard     = driveAngularVelocityKeyboard.copy()
                                                                               .withControllerHeadingAxis(() ->
                                                                                                              Math.sin(
                                                                                                                  driverXbox.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2),
                                                                                                          () ->
                                                                                                              Math.cos(
                                                                                                                  driverXbox.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2))
                                                                               .headingWhile(true);
 
  /**

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   * @see edu.wpi.first.wpilibj2.command.Commands
   * @see com.pathplanner.lib.auto.AutoBuilder;
   * 
   * @version 1.1
   */
  public RobotContainer()
  {
    // Rizz up the ops
    Rizzler.rizz();

    //photon_camera.

    // Build an Pathplanner auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   * 
   * @return void
   * @version 1.0
   */
  private void configureBindings()
  {
    //Command driveFieldOrientedDirectAngle      = drivebase.driveFieldOriented(driveDirectAngle);
    Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    //Command driveRobotOrientedAngularVelocity  = drivebase.driveFieldOriented(driveRobotOriented);
    //Command driveSetpointGen = drivebase.driveWithSetpointGeneratorFieldRelative(
    //    driveDirectAngle);
    //Command driveFieldOrientedDirectAngleKeyboard      = drivebase.driveFieldOriented(driveDirectAngleKeyboard);
    //Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    //Command driveSetpointGenKeyboard = drivebase.driveWithSetpointGeneratorFieldRelative(
    //    driveDirectAngleKeyboard);

  
    /**  driver Xbox */
    drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);

    //driverXbox.start().whileTrue(new playSong(elevator, "sus"));
    //driverXbox.start().whileTrue(Commands.runOnce(elevator::resetControlMode));

    driverXbox.a().whileTrue(Commands.runOnce(drivebase::zeroGyroWithAlliance));
    //driverXbox.b().whileTrue(new horn(elevator)); //rainbow
    driverXbox.x().whileTrue(Commands.none());
    driverXbox.y().whileTrue(Commands.none());

    //driverXbox.leftTrigger().whileTrue(opCommands.AutoScoreL2());
    driverXbox.leftBumper().whileTrue(Commands.none());
    driverXbox.rightTrigger().whileTrue(Commands.none());
    driverXbox.rightBumper().whileTrue(Commands.none());

    /** Operator Xbox 
    operatorXbox.start().whileTrue(Commands.none());
    operatorXbox.start().whileTrue(Commands.runOnce(elevator::resetControlMode));

    operatorXbox.a().whileTrue(opCommands.AutoScoreL1());
    operatorXbox.b().whileTrue(opCommands.AutoScoreL3()); //rainbow
    operatorXbox.x().whileTrue(opCommands.AutoScoreL2());
    operatorXbox.y().whileTrue(opCommands.AutoScoreL4());

    operatorXbox.pov(0).whileTrue(new ManElevatorUp(elevator, led));
    operatorXbox.pov(90).whileTrue(new ManCoralForward(BidenFactor, led));
    operatorXbox.pov(180).whileTrue(new ManElevatorDown(elevator, led));
    operatorXbox.pov(270).whileTrue(new ManCoralReverse(BidenFactor, led));

    operatorXbox.leftTrigger().whileTrue(opCommands.ElevatorDown());
    operatorXbox.leftBumper().whileTrue(new IntakeCoral(BidenFactor, led));
    operatorXbox.rightTrigger().whileTrue(Commands.none());
    operatorXbox.rightBumper().whileTrue(new EjectCoral(BidenFactor, led)); */
     
    /*
    if (DriverStation.isTest())
    {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngle); // Overrides drive command above!

      driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.y().whileTrue(drivebase.driveToDistanceCommand(1.0, 0.2));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.back().whileTrue(drivebase.centerModulesCommand());
      driverXbox.leftBumper().onTrue(Commands.none());
      driverXbox.rightBumper().onTrue(Commands.none());
    } else
    {
      driverXbox.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
      driverXbox.b().whileTrue(
          drivebase.driveToPose(
              new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                              );
      driverXbox.start().whileTrue(Commands.none());
      driverXbox.back().whileTrue(Commands.none());
      driverXbox.leftBumper().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.rightBumper().onTrue(Commands.none());
    } */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   * @version 1.1
   */
  public Command getAutonomousCommand()
  {
    // Pathplanner selected auto will be run in autonomous
    return autoChooser.getSelected();
  }

  /**
   * Run when robot is ready to drive
   *
   * @return void
   * @version 1.1
   */
  public void setDriveMode()
  {
    configureBindings();
  }

  /**
   * Sets all motors in the drivebase to brake mode or coast mode
   * @see frc.robot.subsystems.swervedrive.SwerveSubsystem.setMotorBrake
   * 
   * @param boolean turn brake mode on or off
   * @return void
   * @version 1.0
   */
  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }

  public boolean isRedAlliance() {
    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent())
    {
      return alliance.get() == DriverStation.Alliance.Red;
    }
    return false;
  }

  public void disabledInit() {
  }

  public void disabledPeriodic() {
  }
}