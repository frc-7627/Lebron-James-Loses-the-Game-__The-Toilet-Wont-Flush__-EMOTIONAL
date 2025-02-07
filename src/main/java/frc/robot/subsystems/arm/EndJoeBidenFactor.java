package frc.robot.subsystems.arm;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndJoeBidenFactor extends SubsystemBase {

    private static double LoadSpeed = 0.1;
    private static double EjectSpeed = 0.2;

    private static double CoralInValue = 500;
    private static double CoralOutValue = 500;

    private static final int ampLimit = 40;

    private static double shimSpeed = 0.2;

    private final SparkMax m_motor = new SparkMax(45, MotorType.kBrushless);  

    
    private final TimeOfFlight m_TOF_front = new TimeOfFlight(46);
    private final TimeOfFlight m_TOF_rear = new TimeOfFlight(47);
    
    public EndJoeBidenFactor () {

        SparkMaxConfig motor_config = new SparkMaxConfig();
        motor_config.idleMode(IdleMode.kCoast);
        motor_config.smartCurrentLimit(ampLimit);
    
        m_motor.configure(motor_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_TOF_front.setRangingMode(RangingMode.Short, 50);
        m_TOF_rear.setRangingMode(RangingMode.Short, 50);

        System.out.println("Joe Biden was here");
    }

    public void load() {
        m_motor.set(LoadSpeed);
    }

    public void eject() {
        m_motor.set(EjectSpeed);
    }

    public void stop() {
        m_motor.set(0.0);
    }

    public boolean CoralIn() {
        System.out.println( m_TOF_front.getRange());
        return (m_TOF_front.isRangeValid() && CoralInValue > m_TOF_front.getRange());
    }

    public boolean CoralOut() {
        System.out.println( m_TOF_rear.getRange());
        return (m_TOF_rear.isRangeValid() && CoralOutValue > m_TOF_rear.getRange());
    }

    public void shimForward() {
        m_motor.set(shimSpeed);
    }

    public void shimReverse() {
        m_motor.set(-shimSpeed);
    }
}
