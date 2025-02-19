package frc.robot.subsystems.arm;

import java.io.File;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.MusicTone;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



/**
 * The Elevator Subsystem
 * 
 * Capable of audio and music playback
 * 
 * Uses two TalonFX motor controllers
 * @see com.ctre.phoenix6.Orchestra
 * @see com.ctre.phoenix6.hardware.TalonFX
 */
public class Lebronavator extends SubsystemBase {

    // Define Constants
    private static double kS = 0.25; // Add 0.25 V output to overcome static friction
    private static double kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
    private static double kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
    private static double kP = 4.8;  // A position error of 2.5 rotations results in 12 V output
    private static double kI = 0;    // no output for integrated error
    private static double kD = 0.1;  // A velocity error of 1 rps results in 0.1 V output

    private static double MotionMagicCruiseVelocity = 80; // Target cruise velocity of 80 rps
    private static double MotionMagicAcceleration = 160; // Target acceleration of 160 rps/s (0.5 seconds)
    private static double MotionMagicJerk = 1600; // Target jerk of 1600 rps/s/s (0.1 seconds)
    
    private static double maxSpeed = 0.2;
    private static double shimSpeed = 0.2;

    private static double currentLimit = 40;

    // Define Motor IDs
    final TalonFX m_talonFX_left = new TalonFX(40);
    final TalonFX m_talonFX_right = new TalonFX(41);

    // Make an orchestra
    Orchestra m_Orchestra = new Orchestra();



    /** 
     * Initializes the Elevator subsystem
     * 
     * Configures two TalonFX motor controllers
     * with a follower config
     * And puts them in an orchestra for music playback
     * 
     * @see com.ctre.phoenix6.configs.TalonFXConfiguration
     * @see com.ctre.phoenix6.Orchestra
     * 
     * @version 1.0
     */
    public Lebronavator() {
        // in init function
        var talonFXConfig_right = new TalonFXConfiguration();

        talonFXConfig_right.CurrentLimits.withStatorCurrentLimitEnable(true);
        talonFXConfig_right.CurrentLimits.withStatorCurrentLimit(currentLimit);

        talonFXConfig_right.MotorOutput.withPeakForwardDutyCycle(maxSpeed);
        talonFXConfig_right.MotorOutput.withPeakReverseDutyCycle(maxSpeed);

        talonFXConfig_right.MotorOutput.withNeutralMode(NeutralModeValue.Coast); // Set to coast

        // Music stuff
        talonFXConfig_right.Audio.withBeepOnBoot(false);
        talonFXConfig_right.Audio.withBeepOnConfig(false);
        talonFXConfig_right.Audio.withAllowMusicDurDisable(true);

        // set slot 0 gains
        var slot0Configs = talonFXConfig_right.Slot0;
        slot0Configs.kS = kS;
        slot0Configs.kV = kV;
        slot0Configs.kA = kA;
        slot0Configs.kP = kP;
        slot0Configs.kI = kI;
        slot0Configs.kD = kD;

        // set Motion Magic settings
        var motionMagicConfigs = talonFXConfig_right.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = MotionMagicCruiseVelocity;
        motionMagicConfigs.MotionMagicAcceleration = MotionMagicAcceleration;
        motionMagicConfigs.MotionMagicJerk = MotionMagicJerk;

        var talonFXConfig_left = new TalonFXConfiguration();
        talonFXConfig_left.CurrentLimits.withStatorCurrentLimitEnable(true);
        talonFXConfig_left.CurrentLimits.withStatorCurrentLimit(currentLimit);

        talonFXConfig_left.MotorOutput.withPeakForwardDutyCycle(maxSpeed);
        talonFXConfig_left.MotorOutput.withPeakReverseDutyCycle(maxSpeed);

        talonFXConfig_left.MotorOutput.withNeutralMode(NeutralModeValue.Coast); // Set to coast

        // Music stuff
        talonFXConfig_left.Audio.withBeepOnBoot(false);
        talonFXConfig_left.Audio.withBeepOnConfig(false);
        talonFXConfig_left.Audio.withAllowMusicDurDisable(true);

        //talonFXConfig_left.null

        // Save configs to motors
        m_talonFX_right.getConfigurator().apply(talonFXConfig_right);
        m_talonFX_left.getConfigurator().apply(talonFXConfig_left);

        // Setup follower mode
        resetControlMode();
    }



    /**
     * Plays a constant tone based on provided input
     * using the talonFX controllers
     * 
     * Only use this when elevator is at 0
     * 
     * @param freq the frequency in hz of the tone 
     * @return void
     * @version 1.0
     */
    public void playNote(int freq) {
        m_talonFX_right.setControl(new MusicTone(freq));
        m_talonFX_left.setControl(new MusicTone(freq));
    }

    /**
    * Plays a CHRP file using Pheonix Orchestra using both
    * TalonFX motor controllers, limited to the amount of talonFXs
    * used by subsystem
    *
    * Only use this when elevator is at 0
    *
    * MIDI files can be converted to CHRP files in the
    * Pheonix Tuner X utilites
    * 
    * @param filename the name of the CHRP file as String (without the extension)
    * @return void
    * @version 1.0
    */
    public void playSong(String filename) {
        // Add motors
        m_Orchestra.addInstrument(m_talonFX_left);
        m_Orchestra.addInstrument(m_talonFX_right);

        filename = "sus"; // Bypass filename due to some unknown argument passing issue TODO: fix

        // Load song and play
        String filePath = Filesystem.getDeployDirectory() + "/midi/" + filename + ".chrp";
        System.out.println(filePath);
        System.out.println(new File(filePath).exists());
        m_Orchestra.loadMusic(filePath);
        m_Orchestra.play();
    }



    /**
     * Resets the control modes of both TalonFXs
     * Must use after playing audio on the Motor Controllers
     * To revert them back to position control for elevator use
     * 
     * @return void
     * @version 1.0
     */
    public void resetControlMode() {
         // Clean up orchestra
        m_Orchestra.stop();
        m_Orchestra.clearInstruments();

        // create a Motion Magic request, voltage output
        final MotionMagicVoltage m_request = new MotionMagicVoltage(0);
        m_talonFX_right.setControl(m_request.withPosition(getPosition()));

        // Setup follower config
        m_talonFX_left.setControl(new Follower(m_talonFX_right.getDeviceID(), true));
    }

    /**
     * Moves the elevator to the provided encoder positon, 
     * using the MotionMagic motion controller built into 
     * the talonFXs
     * 
     * @param position the goal encoder positon
     * @return void
     * 
     * @version 1.0
     * 
     * @see com.ctre.phoenix6.controls.MotionMagicVoltage
     */
    public void move(double position) {
        // create a Motion Magic request, voltage output
        final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

        // set target position
        m_talonFX_right.setControl(m_request.withPosition(position));
    }

    /**
     * Slowly moves the Elevator upwards at a constant speed,
     * defined in the instance variable shimSpeed, used for
     * manual control and fine adjustments
     * 
     * @return void
     * @version 1.0
     */
    public void shimUp() {
        final DutyCycleOut m_request = new DutyCycleOut(shimSpeed);

        m_talonFX_right.setControl(m_request);
    }

    /**
     * Slowly moves the Elevator downwards at a constant speed,
     * defined in the instance variable shimSpeed, used for 
     * manual control and fine adjustments
     * 
     * @return void
     * @version 1.0
     */
    public void shimDown() {
        final DutyCycleOut m_request = new DutyCycleOut(-shimSpeed);

        m_talonFX_right.setControl(m_request);
    }

    /**
     * Stops all motors, and sets the left motor to follow 
     * the right motor again
     * 
     * @return void
     * @version 1.0
     */
    public void StopMotor() {
        // Setup follower config
        m_talonFX_left.setControl(new Follower(m_talonFX_right.getDeviceID(), true));


        final DutyCycleOut m_request = new DutyCycleOut(0.0);

        m_talonFX_right.setControl(m_request);
    }

    /**
     * Returns the current encoder postion of the right motor,
     * 0 is it's initial position on startup (down)
     * 
     * @return Endcoder positon as double
     * @version 1.0
     */
    public double getPosition() {
        return m_talonFX_right.getPosition().getValueAsDouble();
    }

    public void init_shuffleboard() {
        // PID Values
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/PID/kS", kS);

        // Magic Motion Values
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/MotionMagicCruiseVelocity", MotionMagicCruiseVelocity);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/MotionMagicAcceleration", MotionMagicAcceleration);
        SmartDashboard.putNumber("Subsystems/Arm/Elevator/MotionMagicJerk", MotionMagicJerk);
    }

    public void update_shuffleboard() {
        // PID Values
        kS = SmartDashboard.getNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        kV = SmartDashboard.getNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        kA = SmartDashboard.getNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        kP = SmartDashboard.getNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        kI = SmartDashboard.getNumber("Subsystems/Arm/Elevator/PID/kS", kS);
        kD = SmartDashboard.getNumber("Subsystems/Arm/Elevator/PID/kS", kS);

        // Magic Motion Values
        MotionMagicCruiseVelocity = SmartDashboard.getNumber("Subsystems/Arm/Elevator/MotionMagicCruiseVelocity", MotionMagicCruiseVelocity);
        MotionMagicAcceleration = SmartDashboard.getNumber("Subsystems/Arm/Elevator/MotionMagicAcceleration", MotionMagicAcceleration);
        MotionMagicJerk = SmartDashboard.getNumber("Subsystems/Arm/Elevator/MotionMagicJerk", MotionMagicJerk);
    }

    /**
    * Similates an issue with the current subsystem
    * Only works if skibbidi-mode is enabled
    *
    * Resets elevator positions, making AutoOP not function
    * 
    * @return void
    * @version 1.0
    */
    public void simulateFault() {
        // Check for Coach Mode
        if(!Constants.skibbidi_mode) {
            System.out.println("[Endefector] Coach Controller Disabled!");
            return; // Do not finish running method
        }

        // Danger Zone
        m_talonFX_right.setPosition(getPosition() + (Math.random() * 5));
        System.out.println("[Elevator] broken");

    }

}
