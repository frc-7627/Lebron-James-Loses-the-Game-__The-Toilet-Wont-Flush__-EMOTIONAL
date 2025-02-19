// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.io.File;

import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.EndJoeBidenFactor;
import frc.robot.subsystems.arm.Lebronavator;
import frc.robot.subsystems.climber.AdultDiapers;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.commands.Endafector.*;
import frc.robot.commands.Climber.*;
import frc.robot.commands.Elevator.*;
import frc.robot.helpers.vision.*;
import frc.robot.commands.teleop.*;
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
  //private final CaprisonCommands visionCommands = new CaprisonCommands();
  // private final EndJoeBidenFactor BidenFactor = new EndJoeBidenFactor(); TODO: PUT BACK
  private final Lebronavator elevator = new Lebronavator();
  private final AdultDiapers climber = new AdultDiapers();
  private final Bluetooth led = new Bluetooth();

  // Command Classes
 // private final OperatorCommands opCommands = new OperatorCommands(elevator, BidenFactor, led); TODO: PUT THIS BACK
  private final CoachCommands chCommands = new CoachCommands(drivebase, elevator, climber, led);


  //private final PhotonCamera photon_camera = new PhotonCamera("Camera_Front");

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

    // Blink color
    led.scroll("orange");

    //photon_camera.

    // Build an Pathplanner auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    configureNamedCommands(); // Setup Pathplanner Commands

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
    Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    //Command driveSetpointGenKeyboard = drivebase.driveWithSetpointGeneratorFieldRelative(
    //    driveDirectAngleKeyboard);

    if (RobotBase.isSimulation())
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocityKeyboard);
      driverXbox.start().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      driverXbox.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());
    } else
    {
      /**  driver Xbox */
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);

      driverXbox.start().whileTrue(new playSong(elevator, "sus"));
      driverXbox.back().whileTrue(Commands.runOnce(elevator::resetControlMode));

      driverXbox.a().whileTrue(Commands.runOnce(drivebase::zeroGyroWithAlliance));
      driverXbox.b().whileTrue(new horn(elevator)); //rainbow
      driverXbox.x().whileTrue(Commands.none());
      driverXbox.y().whileTrue(Commands.none());

      // driverXbox.leftTrigger().whileTrue(opCommands.AutoScoreL2()); TODO: PUT BACK
      driverXbox.leftBumper().whileTrue(Commands.none());
      driverXbox.rightTrigger().whileTrue(Commands.none());
      driverXbox.rightBumper().whileTrue(Commands.none());

      /** Operator Xbox TODO: PUT BACK
      operatorXbox.start().whileTrue(Commands.none());
      operatorXbox.back().whileTrue(Commands.runOnce(elevator::resetControlMode));

      operatorXbox.a().whileTrue(opCommands.AutoScoreL1());
      operatorXbox.b().whileTrue(opCommands.AutoScoreL3());
      operatorXbox.x().whileTrue(opCommands.AutoScoreL2());
      operatorXbox.y().whileTrue(opCommands.AutoScoreL4());

      operatorXbox.leftStick().whileTrue(Commands.none());
      operatorXbox.rightStick().whileTrue(Commands.none());

      operatorXbox.pov(0).whileTrue(new ManElevatorUp(elevator, led));
      operatorXbox.pov(90).whileTrue(new ManCoralForward(BidenFactor, led));
      operatorXbox.pov(180).whileTrue(new ManElevatorDown(elevator, led));
      operatorXbox.pov(270).whileTrue(new ManCoralReverse(BidenFactor, led));

      operatorXbox.leftTrigger().whileTrue(opCommands.AutoStow());
      operatorXbox.leftBumper().whileTrue(new IntakeCoral(BidenFactor, led));
      operatorXbox.rightTrigger().whileTrue(Commands.none());
      operatorXbox.rightBumper().whileTrue(new EjectCoral(BidenFactor, led));
*/
      /** Coach Xbox */
      coachXbox.start().whileTrue(Commands.none());
      coachXbox.back().whileTrue(Commands.none());
      coachXbox.a().whileTrue(chCommands.breakDrivebase());
      coachXbox.b().whileTrue(chCommands.breakVision());
      coachXbox.x().whileTrue(chCommands.breakElevator()); // TODO: CHANGE THIS ONE TO BE SAFER
      coachXbox.y().whileTrue(chCommands.breakLed());

      coachXbox.leftStick().whileTrue(chCommands.breakClimber());
      coachXbox.rightStick().whileTrue(chCommands.breakClimber());

      coachXbox.leftTrigger().whileTrue(chCommands.breakEndefector());
      coachXbox.leftBumper().whileTrue(Commands.none());
      coachXbox.rightTrigger().whileTrue(Commands.none());
      coachXbox.rightBumper().whileTrue(Commands.none());
    
    } 
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
   * Defines Named Commands for use with Pathplanner
   * Should include all available commands on in this code
   * 
   * @return void
   * @version 1.0
   * 
   * @see com.pathplanner.lib.auto.NamedCommands
   */
  private void configureNamedCommands() {
    /* Vision - Requires vision readings from either apriltags or gamepeices, use near middle through end of auto*/
    /* Manual - Only for certain edge cases, use Automated controls for most autos */
    /* Rare Use Case - There is no thinkable reason to use these */

    // AutoOperator
    /*  NamedCommands.registerCommand("AutoStow", opCommands.AutoStow()); TODO: PUT BACK
    NamedCommands.registerCommand("AutoScoreL1", opCommands.AutoScoreL1()); 
    NamedCommands.registerCommand("AutoScoreL2", opCommands.AutoScoreL2()); 
    NamedCommands.registerCommand("AutoScoreL3", opCommands.AutoScoreL3()); 
    NamedCommands.registerCommand("AutoScoreL4", opCommands.AutoScoreL4()); */

    // Climber
    NamedCommands.registerCommand("ClimberDown", new ClimberDown(climber, led)); // Manual
    NamedCommands.registerCommand("ClimberUp", new ClimberUp(climber, led)); // Manual
    NamedCommands.registerCommand("ClimberSlowDown", new ClimberSlowDown(climber, led)); // Manual
    NamedCommands.registerCommand("ClimberSlowUp", new ClimberSlowUp(climber, led)); // Manual

    // Elevator
    //NamedCommands.registerCommand("ArmToStow", new ElevatorMove(climber));
    NamedCommands.registerCommand("Horn", new horn(elevator));
    NamedCommands.registerCommand("playSong", new playSong(elevator, "sus")); 
    NamedCommands.registerCommand("ManElevatorDown", new ManElevatorDown(elevator, led)); // Manual
    NamedCommands.registerCommand("ManElevatorUp", new ManElevatorUp(elevator, led)); // Manual

    /*  Endafector
    NamedCommands.registerCommand("EjectCoral", new EjectCoral(BidenFactor, led));
    NamedCommands.registerCommand("IntakeCoral", new IntakeCoral(BidenFactor, led));
    NamedCommands.registerCommand("ManCoralForward", new ManCoralForward(BidenFactor, led)); // Manual
    NamedCommands.registerCommand("ManCoralReverse", new ManCoralReverse(BidenFactor, led)); // Manual
    NamedCommands.registerCommand("WaitForCoral", new WaitForCoral(BidenFactor)); // Rare use case TODO: PUT BACK
    */

    // Drivebase
    NamedCommands.registerCommand("DriveBaseRotationAdjust", new DriveBaseRotationAdjust(drivebase)); // Vision
  } // TODO: Create a lateral adjustment cmd and replace this ^ with that in pathplanner

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
    led.scroll("default");
    elevator.StopMotor();
    elevator.resetControlMode();
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

  /** 
   * Changes the Led Color to match the selected alliance
   * in driverstation. Should be called periodically when disabled
   * 
   * @return void
   * @version 1.0
   * 
   * @see edu.wpi.first.wpilibj.DriverStation.getAlliance
   * @see frc.robot.subsystems.Bluetooth
   * */
  public void matchLedWithAlliance() {
    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent())
    {
      if(alliance.get() == DriverStation.Alliance.Red) {
        led.color("red");
        led.setDefaultColor("red");
      }
      else {
        led.color("blue");
        led.setDefaultColor("blue");
      }
    }
  }

  /**
   * Run once when Robot is disabled in driverstation
   *  
   * @return void
   */
  public void disabledInit() {
    elevator.playSong("amoungus"); // Play Amoung us theme to pass the time
  }

  /**
   * Run every cycle when the robot is disabled in driverstation
   *
   *  - Changes Led color depending on which alliance the robot is on
   * 
   * @return void
   */
  public void disabledPeriodic() {
    matchLedWithAlliance();
  }
}
