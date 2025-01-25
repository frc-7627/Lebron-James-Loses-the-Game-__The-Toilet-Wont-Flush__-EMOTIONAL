package frc.robot.subsystems.arm;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

    private static double currentLimit = 40;

    // Define Motor IDs
    final TalonFX m_talonFX_left = new TalonFX(40);
    final TalonFX m_talonFX_right = new TalonFX(41);

  
    public Lebronavator() {
        // in init function
        var talonFXConfig_right = new TalonFXConfiguration();

        talonFXConfig_right.CurrentLimits.withStatorCurrentLimitEnable(true);
        talonFXConfig_right.CurrentLimits.withStatorCurrentLimit(40);

        talonFXConfig_right.MotorOutput.withPeakForwardDutyCycle(0.2);
        talonFXConfig_right.MotorOutput.withPeakReverseDutyCycle(0.2);

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
        talonFXConfig_left.CurrentLimits.withStatorCurrentLimit(40);

        talonFXConfig_left.MotorOutput.withPeakForwardDutyCycle(0.2);
        talonFXConfig_left.MotorOutput.withPeakReverseDutyCycle(0.2);

        //

        //talonFXConfig_left.null


        m_talonFX_right.getConfigurator().apply(talonFXConfig_right);
        m_talonFX_left.getConfigurator().apply(talonFXConfig_left);
    }

    public void move(double position) {
        // create a Motion Magic request, voltage output
        final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

        // set target position
        m_talonFX_right.setControl(m_request.withPosition(position));
    }


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

}
