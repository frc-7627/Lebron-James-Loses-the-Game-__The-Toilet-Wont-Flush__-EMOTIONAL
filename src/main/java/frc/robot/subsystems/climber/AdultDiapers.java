package frc.robot.subsystems.climber;

import com.ctre.phoenix6.hardware.TalonFX;

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

    // Define Motor IDs
    final TalonFX m_talonFX = new TalonFX(50);

    /** Initiallizes the Climber Subsystem */
    public AdultDiapers() {
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
}