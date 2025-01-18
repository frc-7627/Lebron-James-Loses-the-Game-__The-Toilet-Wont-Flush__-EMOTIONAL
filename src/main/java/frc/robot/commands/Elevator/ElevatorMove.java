package frc.robot.commands.Elevator;

import frc.robot.subsystems.arm.Lebronavator;

import edu.wpi.first.wpilibj2.command.Command;

public class ElevatorMove extends Command {

    private double setPoint_Down = 10;
    private double setPoint_L1 = 10;
    private double setPoint_L2 = 10;
    private double setPoint_L3 = 10;
    private double setPoint_L4 = 10;

    private final double goal; 

    private Lebronavator module;

    public ElevatorMove(Lebronavator module, int location) {
        this.module = module;
        switch (location) {
            case 0:
                goal = setPoint_Down;
                break;
            case 1:
                goal = setPoint_L1;
                break;
            case 2:
                goal = setPoint_L2;
                break;
            case 3:
                goal = setPoint_L3;
                break;
            case 4:
                goal = setPoint_L4;
                break;
            default:
                System.out.print("Unknown location set");
                goal = module.getPosition();
                break;
        }
        addRequirements(module);
     }

    @Override
    public void initialize() {
        module.move(goal);
    }

    @Override 
    public boolean isFinished() {
        return (goal == module.getPosition()); 
    }
}
