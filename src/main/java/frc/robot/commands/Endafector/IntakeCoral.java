package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class IntakeCoral extends Command {
    private NotSwerveSubsystem module;
    private Bluetooth led;
    private int state;

    /**
    * Runs the Endafector Forwards, in order to push the gampiece
    * out the back for scoring. Automatticlly ends when a gamepiece crosses 
    * the Front TOF sensor inside the Endafector, indicating it has 
    * left contact with the robot. 
    *
    * Uses leds to indicate the status of the gamepiece, 
    * the statuses are as follows:
    *   blinking eggPlant: Endafector Running: No gamepiece detected
    *   vomitGreen: Endafector Stopped: Gamepiece is secure
    *   led off: Command interupted
    *
    * @requires AdultDiapers
    * @requires led - For Visual notifications
    * @version 1.0
    */
    public IntakeCoral(NotSwerveSubsystem module, Bluetooth led) {
        this.module = module;
        this.led = led;
        addRequirements(module);
        addRequirements(led);
     }

    @Override
    public void initialize() {
        System.out.println("init");
        module.load();

        state = 0;
    }

    @Override
    public void execute() {
        System.out.print("run");
        if(state == 0 && module.CoralTouchFront()) {
            System.out.println("state 1");
            module.loadSlow(); 
            state = 1;
        }
        else if(state == 1 && module.CoralLeaveBack()) {
            System.out.println("state 2");
            module.loadSlowReverse(); 
            state = 2;
        }
        else if(state == 2 && module.CoralTouchBack()) {
            state = 3;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("state f: " + interrupted);
        module.stop();
        if(!interrupted) led.color("vomitGreen");
        else led.bluetoothOFF();
    }

    @Override 
    public boolean isFinished() {
        return (state == 3);
    }
}
