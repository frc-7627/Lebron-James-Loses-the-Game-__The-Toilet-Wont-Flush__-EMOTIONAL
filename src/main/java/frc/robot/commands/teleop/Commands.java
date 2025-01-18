package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Elevator.ElevatorMove;
import frc.robot.commands.Endafector.EjectCoral;
import frc.robot.commands.Endafector.IntakeCoral;
import frc.robot.commands.Endafector.WaitForCoral;
import frc.robot.subsystems.arm.EndJoeBidenFactor;
import frc.robot.subsystems.arm.Lebronavator;

public class Commands {
    private final Lebronavator elevator;
    private final EndJoeBidenFactor outake;


    public Commands(Lebronavator module1, EndJoeBidenFactor module2) {
        this.elevator = module1;
        this.outake   = module2;
    }
    
    public Command ElevatorDown() {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, 0),
            new WaitForCoral(outake),
            new IntakeCoral(outake)
        );
    }

    private Command AutoScore(int pos) {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, pos),
            new WaitCommand(0.0), // Reduce Jitter
            new EjectCoral(outake)
        );
    }

    public Command AutoScoreL1() {
        return AutoScore(1);
    }
    public Command AutoScoreL2() {
        return AutoScore(2);
    }
    public Command AutoScoreL3() {
        return AutoScore(3);
    }
    public Command AutoScoreL4() {
        return AutoScore(4);
    }



}
