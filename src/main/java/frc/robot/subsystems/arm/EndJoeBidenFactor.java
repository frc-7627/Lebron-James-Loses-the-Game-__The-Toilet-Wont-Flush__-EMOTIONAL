package frc.robot.subsystems.arm;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndJoeBidenFactor extends SubsystemBase {

    private static double LoadSpeed = 0.5;
    private static double EjectSpeed = 0.5;

    private static double CoralInValue = 10;
    private static double CoralOutValue = 10;

    private static final int ampLimit = 40;

    private final SparkMax m_motor_front = new SparkMax(30, MotorType.kBrushless);  
    private final SparkMax m_motor_rear = new SparkMax(31, MotorType.kBrushless);  

    
    private final TimeOfFlight m_TOF_front = new TimeOfFlight(32);
    private final TimeOfFlight m_TOF_rear = new TimeOfFlight(33);
    
    public EndJoeBidenFactor () {

        SparkMaxConfig motor_config = new SparkMaxConfig();
        motor_config.idleMode(IdleMode.kCoast);
        motor_config.smartCurrentLimit(ampLimit);
    
        m_motor_front.configure(motor_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_motor_rear.configure(motor_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        System.out.println("Joe Biden was here");
    }

    public void load() {
        m_motor_front.set(LoadSpeed);
        m_motor_rear.set(0.0);
    }

    public void eject() {
        m_motor_front.set(EjectSpeed);
        m_motor_rear.set(EjectSpeed);
    }

    public void stop() {
        m_motor_front.set(0.0);
        m_motor_rear.set(0.0);
    }

    public boolean CoralIn() {
        return (m_TOF_front.isRangeValid() && CoralInValue > m_TOF_front.getRange());
    }

    public boolean CoralOut() {
        return (m_TOF_rear.isRangeValid() && CoralOutValue > m_TOF_rear.getRange());
    }
}
