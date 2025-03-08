package frc.robot.subsystems.climber;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** 
 * Climber Subsystem
 * 
 * Uses a singe TalonFX motor controller
 * 
 * @see com.ctre.phoenix6.hardware.TalonFX
 */
public class AdultDiapers extends SubsystemBase {

    // Instance variables
    private static double movementSpeed = 0.5;  
    private static double slowMoveSpeed = 0.5 * movementSpeed; 
    
    private static double rampUpPeriod = 0.5;

    private static double currentLimit = 40;

    // Define Motor IDs
    final TalonFX m_talonFX = new TalonFX(50);

    /** Initiallizes the Climber Subsystem */
    public AdultDiapers() {
        // in init function
        var talonFXConfig = new TalonFXConfiguration();
        
        // Current limit
        talonFXConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        talonFXConfig.CurrentLimits.withStatorCurrentLimit(currentLimit);

        // Speed limit
        talonFXConfig.MotorOutput.withPeakForwardDutyCycle(movementSpeed);
        talonFXConfig.MotorOutput.withPeakReverseDutyCycle(movementSpeed);

        talonFXConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake); // Set to brake mode

        // Ramp Up speed
        talonFXConfig.OpenLoopRamps.withDutyCycleOpenLoopRampPeriod(rampUpPeriod);
        talonFXConfig.ClosedLoopRamps.withDutyCycleClosedLoopRampPeriod(rampUpPeriod);

        // Music stuff
        talonFXConfig.Audio.withBeepOnBoot(false);
        talonFXConfig.Audio.withBeepOnConfig(false);
        talonFXConfig.Audio.withAllowMusicDurDisable(true);

        m_talonFX.getConfigurator().apply(talonFXConfig); // Apply Motor Config


    }
    

    /**
     * Moves the climber up
     * at a speed specified in the instance variable
     * MoveSpeed for general control
     * 
     * @return void
     * @version 1.0
     */
    public void moveUp() {
        m_talonFX.set(movementSpeed);
    }

    /**
     * Moves the climber down
     * at a speed specified in the instance variable
     * MoveSpeed for general control
     * 
     * @return void
     * @version 1.0
     */
    public void moveDown() {
        m_talonFX.set(-movementSpeed);
    }

    /**
     * Slowly moves the climber up
     * at a speed specified in the instance variable
     * slowMoveSpeed for fine control
     * 
     * @return void
     * @version 1.0
     */
    public void slowMoveUp() {
        m_talonFX.set(slowMoveSpeed);
    }

    /**
     * Slowly moves the climber down
     * at a speed specified in the instance variable
     * slowMoveSpeed for fine control
     * 
     * @return void
     * @version 1.0
     */
    public void slowMoveDown() {
        m_talonFX.set(-slowMoveSpeed);
    }

    /**
     * Stops all Motors
     * 
     * @return void
     * @version 1.0
     */
    public void stop() {
        m_talonFX.set(0.0);
    }

    /**
     * Returns the current position of the climber
     * 
     * @return double - the reported encoder position
     * @version 1.0
     */
    public double getPosition() {
        return m_talonFX.getPosition().getValueAsDouble();
    }

    /**
     * Gets the output current of the Endefector Motor
     * 
     * @version 1.0
     * @return Output amperage (double)
     */
    public double getCurrent() {
        return m_talonFX.getSupplyCurrent(false).getValueAsDouble();
    }

    /**
     * Gets the current Endefector Motor Velocity (NOT RPM)
     * 
     * @version 1.0
     * @return Motor Velocity as a double
     */
    public double getVelocity() {
        return m_talonFX.getVelocity().getValueAsDouble();
    }


    /**
    * Similates an issue with the current subsystem
    * Only works if skibbidi-mode is enabled
    *
    * Slows climber motors down to nothing
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
        movementSpeed = 0.01;
        slowMoveSpeed = 0.01;
        System.out.println("[Climber] broken");
    }


    /**
     * Gets all fields and getter methods in this class and 
     * places their values from shuffleboard
     * 
     * @return void
     * @version 1.0
     */
    public void pushData() {
        String shuffleboardName = this.getClass().getCanonicalName().replace('.', '/').substring(10);

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method:methods)
        {
            if(method.getName().substring(0, 3).equals("get")) {
                try {
                    Object value = method.invoke(this);
                    if(value == null) value = 0.0; // Set to zero in case we can't run method
                    SmartDashboard.putNumber(shuffleboardName + "/" + method.getName().substring(3), Double.parseDouble(value.toString()));
                    //System.out.println(method.getName().substring(3) + " value:" + Double.parseDouble(value.toString()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + method.getName());
                } catch(InvocationTargetException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + method.getName());
                }
            }
        }
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {  
            if (field.getType().isPrimitive()) {
                try {
                    SmartDashboard.putNumber(shuffleboardName + "/" + field.getName(), field.getDouble(this.getClass()));
                    //System.out.println(field.getName() + " value: " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }
        }   
    }

    /**
     * Gets all feilds in this class and updates their values from shuffleboard
     * !! Make sure to run pushData first !!
     * 
     * @return void
     * @version 1.0
     */
    public void pullData() {
        String shuffleboardName = this.getClass().getCanonicalName().replace('.', '/').substring(10);
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType().isPrimitive() && !Modifier.isStatic(field.getModifiers())) {
                try {
                    field.setDouble(this, SmartDashboard.getNumber(shuffleboardName + "/const/" + field.getName(), field.getDouble(this.getClass())));
                    System.out.println(field.getName() + " set " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }
        }   
    }

    /** Run once every periodic call */
    /** 
     *  Run once every periodic call as
     *  long as the Command is running 
     */
    @Override
    public void periodic() {
        if(Constants.verbose_shuffleboard_logging) {
            pushData();
            pullData();
        }
    }
}