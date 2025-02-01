package frc.robot.subsystems.climber;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AdultDiapers extends SubsystemBase {

    private static double movementSpeed = 0.5;  
    private static double slowMoveSpeed = 0.5 * movementSpeed;  

    // Define Motor IDs
    final TalonFX m_talonFX = new TalonFX(41);

  
    public AdultDiapers() {
    }

    public void moveUp() {
        m_talonFX.set(movementSpeed);
    }

    public void moveDown() {
        m_talonFX.set(-movementSpeed);
    }

    public void slowMoveUp() {
        m_talonFX.set(slowMoveSpeed);
    }

    public void slowMoveDown() {
        m_talonFX.set(-slowMoveSpeed);
    }

    public void stop() {
        m_talonFX.set(0.0);
    }

    public double getPosition() {
        return m_talonFX.getPosition().getValueAsDouble();
    }
}