package frc.robot.commands.Endafector;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.ProgressBar;
import frc.robot.subsystems.arm.NotSwerveSubsystem;

/** See Constructor for details */
public class IntakeCoral extends Command {
    private NotSwerveSubsystem module;
    private Bluetooth led;
    private CoralIntakeState state;
    private ProgressBar progressBar;

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
    * Uses progress bar to indicate state:
    *   yellow: Command in progress.
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
        this.progressBar = new ProgressBar(3, "yellow");
        addRequirements(module);
        addRequirements(led);
     }

    /** Run once at Command Start */
    @Override
    public void initialize()  {
        module.load();

        state = CoralIntakeState.CORAL_ENTERING;
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

    /**
     * Step the progress bar.
     */
    public void stepProgressBar() {
        progressBar.stepProgressBar();
        led.updateWithProgressBar(progressBar);
    }

    /**
     * Interrupt the progress bar.
     */
    public void interruptProgressBar() {
        progressBar.interrupt();
        led.updateWithProgressBar(progressBar);
    }

    /**
     * Complete the progress bar.
     */
    public void completeProgressBar() {
        progressBar.complete();
        led.updateWithProgressBar(progressBar);
    }

    @Override
    public void execute() {
        switch (state) {
            case CORAL_ENTERING:
                if (module.CoralTouchFront()) {
                    System.out.println("\nCoral reached front sensor, now loading forward.");
                    stepProgressBar();

                    state = CoralIntakeState.CORAL_LOADING_FORWARD;
                    module.loadSlow();
                }
                break;
            case CORAL_LOADING_FORWARD:
                if (module.CoralLeaveBack()) {
                    System.out.println("\nCoral left back sensor, now loading back.");
                    stepProgressBar();

                    state = CoralIntakeState.CORAL_LOADING_BACK;
                    module.loadSlowReverse(); 
                }
                break;
            case CORAL_LOADING_BACK:
                if (module.CoralTouchBack()) {
                    System.out.println("\nCoral in position!");
                    stepProgressBar();

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
            System.out.println("\nIntaking coral interrupted!");
            interruptProgressBar();
        } else {
            System.out.println("\nIntaking coral completed!");
            completeProgressBar();
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
