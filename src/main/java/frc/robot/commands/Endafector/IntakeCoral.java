package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class IntakeCoral extends Command {
    private NotSwerveSubsystem module;
    private Bluetooth led;
    private CoralIntakeState state;

    /**
    * Ensures the coral is in position for scoring.
    * 
    * 1. Waits for the coral to reach the forward TOF sensor. When it does,
    * loads the coral forwards.
    * 2. Once the coral is forward enough that it leaves the back TOF sensor,
    * loads the coral backwards.
    * 3. Once the coral touches the back TOF sensor, the coral's position
    * is guranteed, and the command finishes.
    *
    * Uses leds to indicate the status of the coral, 
    * the statuses are as follows:
    *   vomitGreen: Command finished, and coral is secure.
    *   led off: Command interupted.
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

    /** Run once at Command Start */
    @Override
    public void initialize()  {
        module.load();

        state = CoralIntakeState.CORAL_ENTERING;

        led.initProgressBar("yellow", 3);
    }

    /**
     * States for coral intake.
     */
    public enum CoralIntakeState {
        /**
         * Coral is entering the endafector.
         */
        CORAL_ENTERING,
        /**
         * Loading the coral forward until it reaches the forward TOF sensor.
         */
        CORAL_LOADING_FORWARD,
        /**
         * Loading the coral backward until it reaches the back TOF sensor.
         */
        CORAL_LOADING_BACK,
        /**
         * Coral is in position.
         */
        CORAL_IN_POSITION,
    }

    @Override
    public void execute() {
        switch (state) {
            case CORAL_ENTERING:
                if (module.CoralTouchFront()) {
                    System.out.println("\nCoral reached front sensor, now loading forward.");
                    led.stepProgressBar();

                    state = CoralIntakeState.CORAL_LOADING_FORWARD;
                    module.loadSlow();
                }
                break;
            case CORAL_LOADING_FORWARD:
                if (module.CoralLeaveBack()) {
                    System.out.println("\nCoral left back sensor, now loading back.");
                    led.stepProgressBar();

                    state = CoralIntakeState.CORAL_LOADING_BACK;
                    module.loadSlowReverse(); 
                }
                break;
            case CORAL_LOADING_BACK:
                if (module.CoralTouchBack()) {
                    System.out.println("\nCoral in position!");
                    led.stepProgressBar();

                    state = CoralIntakeState.CORAL_IN_POSITION;
                }
                break;
            case CORAL_IN_POSITION:
                break;
        }
    }


     /** 
      * Run once at Command End 
      * 
      * @param interupted - False if Command ended gracefully.
      *                     True if interrupted by something else.
      */
    @Override
    public void end(boolean interrupted) {
        module.stop();

        if (interrupted) {
            System.out.println("Intaking coral interrupted!");
            led.interruptProgressBar();
        } else {
            led.completeProgressBar();
        }
    }

    /** 
      * Checks if it's time to end the Command.
      * 
      * This is exactly when the coral is in position.
      * 
      * @return True - End the Command
      *         False - Keep running Periodic
      */
    @Override 
    public boolean isFinished() {
        return (state == CoralIntakeState.CORAL_IN_POSITION);
    }
}
