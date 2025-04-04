package frc.robot.commands.Elevator;

import frc.robot.subsystems.arm.Lebronavator;

import edu.wpi.first.wpilibj2.command.Command;

/**
* Moves the Elevator to one of 5 pre defined encoder positions,
* given the int arguement passed to this command, then ends
* With all 5 Encoder Positions are defined in this class
*
* @arg int location - used to lookup encoder position to move to
*       0: Stow Elevator
*       1-4: Scoring positions for L1-L4 on the reef 
*
* @requires AdultDiapers
* @requires led - For visual manual control in use notification
* @version 1.0
*/
public class ElevatorMove extends Command {

    // Editable Constants
    private double setPoint_Down = 0;
    private double setPoint_Stow = 1.87;
    private double setPoint_L1 = 13;
    private double setPoint_L2 = 21.86;
    private double setPoint_L3 = 36.46;
    private double setPoint_L4 = 58.48;
    private double setPoint_L4_high = 61.8; // Max height extension

    private final double goal; 

    private Lebronavator module;

    public ElevatorMove(Lebronavator module, int location) {
        this.module = module;
        switch (location) {
            case -1:
                goal = setPoint_Down;
                break;
            case 0:
                goal = setPoint_Stow;
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
            case 5:
                goal = setPoint_L4_high;
                break;
            default:
                System.out.print("Unknown location set");
                goal = module.getPosition();
                break;
        }
        addRequirements(module);
     }

    /** Run once at Command Start */
    @Override
    public void initialize()  {
        module.move(goal);
    }

    /** 
      * Checks if it's time to end the Command 
      * 
      * @return True - End the Command
      *         False - Keep running Periodic
      */
    @Override 
    public boolean isFinished() {
        return module.atPosition();
    }
}
