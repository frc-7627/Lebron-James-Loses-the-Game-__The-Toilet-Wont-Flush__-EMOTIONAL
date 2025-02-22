package frc.robot.subsystems.arm;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.lang.constant.Constable;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** 
 * Our beloved Endefector Subsystem
 * 
 * Uses:
 *  2 Revrobotics SparkMAX motor controllers with neos to move the gamepiece
 *  2 playingwithfusion TOF sensors to check whether we are contacting
 *    the gamepiece
 * 
 * @see com.revrobotics.spark.SparkMax
 * @see com.playingwithfusion.TimeOfFlight
 * 
 * @version 1.0
 */
public class EndJoeBidenFactor extends SubsystemBase {

    private static double LoadSpeed = 0.1;
    private static double EjectSpeed = 0.2;

    private static double CoralInValue = 500;
    private static double CoralOutValue = 500;

    private static final int ampLimit = 40;

    private static double shimSpeed = 0.6;

    private final SparkMax m_motor = new SparkMax(45, MotorType.kBrushless);  

    
    private final TimeOfFlight m_TOF_front = new TimeOfFlight(46);
    private final TimeOfFlight m_TOF_rear = new TimeOfFlight(47);
    
    /**
     * Initializes the Endefector Subsystem
     * 
     * Configures two SparkMAX motor controllers
     * with a follower config
     * 
     * @see com.revrobotics.spark.config.SparkMaxConfig
     * 
     * @version 1.0
     */
    public EndJoeBidenFactor () {

        SparkMaxConfig motor_config = new SparkMaxConfig();
        motor_config.idleMode(IdleMode.kCoast);
        motor_config.smartCurrentLimit(ampLimit);
    
        m_motor.configure(motor_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_TOF_front.setRangingMode(RangingMode.Short, 50);
        m_TOF_rear.setRangingMode(RangingMode.Short, 50);

        System.out.println("Joe Biden was here");
    }

    /**
     * Runs the endefector at a constant speed
     * defined in the instance variable LoadSpeed
     * In order to load a gamepiece into the endefector
     * 
     * @return void
     * @version 1.0
     */
    public void load() {
        m_motor.set(LoadSpeed);
    }

    /**
     * Runs the endefector at a constant speed
     * defined in the instance variable EjectSpeed
     * In order to eject the gamepiece out the back
     * of the endefector
     * 
     * @return void
     * @version 1.0
     */
    public void eject() {
        m_motor.set(EjectSpeed);
    }

    /**
     * Stops all motors in the endefector
     * 
     * @return void
     * @version 1.0
     */
    public void stop() {
        m_motor.set(0.0);
    }

    /**
     * A simple check using TOF sensors to detected whether
     * a gamepeice is currently in front of the Front TOF 
     * sensor, to find out when we securely are holding
     * a gamepiece
     * 
     * @return boolean - True if gamepiece is detected
     * 
     * @see com.playingwithfusion.TimeOfFlight.getRange
     * @version 1.0
     */
    public boolean CoralIn() {
        System.out.println( m_TOF_front.getRange());
        return (m_TOF_front.isRangeValid() && CoralInValue > m_TOF_front.getRange());
    }

    /**
     * A simple check using TOF sensors to detected whether
     * a gamepeice is currently in front of the Rear TOF 
     * sensor, to find out when we are no longer in 
     * possession of a gamepiece
     * 
     * @return boolean - True if gamepiece is detected
     * 
     * @see com.playingwithfusion.TimeOfFlight.getRange
     * @version 1.0
     */
    public boolean CoralOut() {
        System.out.println( m_TOF_rear.getRange());
        return (m_TOF_rear.isRangeValid() && CoralOutValue > m_TOF_rear.getRange());
    }

    /**
    * Slowly moves the Endefector forward at a slower constant speed,
    * defined in the instance variable shimSpeed, used for 
    * manual control and fine adjustments
    * 
    * @return void
    * @version 1.0
    */
    public void shimForward() {
        m_motor.set(shimSpeed);
    }

    /**
    * Slowly moves the Endefector backward at a slower constant speed,
    * defined in the instance variable shimSpeed, used for 
    * manual control and fine adjustments
    * 
    * @return void
    * @version 1.0
    */
    public void shimReverse() {
        m_motor.set(-shimSpeed);
    }

    /**
    * Similates an issue with the current subsystem
    * Only works if skibbidi-mode is enabled
    *
    * Prevents TOF from working
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
        CoralInValue = 100000;
        CoralOutValue = 100000;
        System.out.println("[Endefector] broken");

    }
}
