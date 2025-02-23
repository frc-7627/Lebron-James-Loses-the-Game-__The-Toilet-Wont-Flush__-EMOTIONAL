package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Elevator.ElevatorMove;
import frc.robot.commands.Endafector.EjectCoral;
import frc.robot.commands.Endafector.IntakeCoral;
import frc.robot.commands.Endafector.WaitForCoral;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.EndJoeBidenFactor;
import frc.robot.subsystems.arm.Lebronavator;

/** See Constructor for details */
public class OperatorCommands {
    private final Lebronavator elevator;
    private final EndJoeBidenFactor outake;
    private final Bluetooth led;

    /**
    * A collection of CommandGroups, that factor in
    * Commands from different subsystems to
    * complete a single robot action,
    *
    * such as moving the elevator up, and then ejecting a
    * gamepiece in order to score a point 
    *
    */
    public OperatorCommands(Lebronavator module1, EndJoeBidenFactor module2, Bluetooth led) {
        this.elevator = module1;
        this.outake   = module2;
        this.led      = led;

        new IntakeCoral(module2, led);
    }
    
    /**
    * Stops the endefector, and stows the elevator
    *
    * @requires elevator
    * @requires endafector
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    public Command AutoStow() {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, 0),
            new WaitForCoral(outake, led),
            new IntakeCoral(outake, led)
        );
    }

    /**
    * Brings the elevtor up to a specied scoring position,
    * and then runs the endefector forward until a gamepiece
    * is scored
    *
    * @requires elevator
    * @requires endafector
    * @requires led - For visual manual control in use notification
    * @version 1.0
    */
    private Command AutoScore(int pos) {
        return new SequentialCommandGroup(
            new ElevatorMove(elevator, pos),
            new WaitCommand(0.0), // Reduce Jitter
            new EjectCoral(outake, led)
        );
    }

    /** Scores gamepeice in the L1 Position 
    * @see AutoScore
    */
    public Command AutoScoreL1() {
        return AutoScore(1);
    }

    /** Scores gamepeice in the L2 Position 
    * @see AutoScore
    */
    public Command AutoScoreL2() {
        return AutoScore(2);
    }

    /** Scores gamepeice in the L3 Position 
    * @see AutoScore
    */
    public Command AutoScoreL3() {
        return AutoScore(3);
    }

    /** Scores gamepeice in the L4 Position 
    * @see AutoScore
    */
    public Command AutoScoreL4() {
        return AutoScore(4);
    }

    public Command AutoEjectL4() {
        return new SequentialCommandGroup(
            new EjectCoral(outake, led),
            new ElevatorMove(elevator, 0),
            new ParallelRaceGroup(
                    new EjectCoral(outake, led),
                    new WaitCommand(0.5)
                ),
            new ElevatorMove(elevator, 0)
        );
    }
}
