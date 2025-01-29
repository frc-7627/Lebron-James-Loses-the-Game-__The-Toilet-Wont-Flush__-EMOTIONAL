package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Lebronavator;

public class playSong extends Command{
    private Lebronavator module;
    private String songName;
    
     public playSong(Lebronavator module, String songName) {
        this.module = module;
        addRequirements(module);
     }

    
    @Override
    public void initialize() {
        module.playSong(songName);
    }

    @Override
    public void end(boolean interrupted) {
        module.resetControlMode();
    }
}
