package frc.robot.subsystems;

import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLight extends SubsystemBase {
    private double tx;
    private double ty;
    private boolean tv;
    private double area;
    private int num = 0;


    public LimeLight() {
        LimelightHelpers.setLEDMode_PipelineControl("");
        LimelightHelpers.setCropWindow("", -1, 1, -1, 1);

        System.out.println("[LimeLight] Bofa Mode Loaded");
    }


  

  
        public void doLimelightThing(){
        num++;
         if((num % 50) == 0){
            tv = LimelightHelpers.getTV("");
            tx = LimelightHelpers.getTX("");
            ty = LimelightHelpers.getTY("");
            area = LimelightHelpers.getTA("");

            //  if(num % 50 == 0){
            //System.out.println(area + ", " + tv);
            
            //post to smart dashboard periodically
            SmartDashboard.putNumber("Limelight/TX", tx);
            SmartDashboard.putNumber("Limelight/TY", ty);
            SmartDashboard.putNumber("Limelight/Area", area);
        }
    }

    @Override
    public void periodic() {
        doLimelightThing();
    }



    
}