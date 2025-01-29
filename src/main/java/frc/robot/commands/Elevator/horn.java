package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Lebronavator;

public class horn extends Command{
    private Lebronavator module;
    
     public horn(Lebronavator module) {
        this.module = module;
        addRequirements(module);
     }

    
    @Override
    public void initialize() {
        module.playNote(440);
    }

    @Override
    public void end(boolean interrupted) {
        module.resetControlMode();
    }
}
