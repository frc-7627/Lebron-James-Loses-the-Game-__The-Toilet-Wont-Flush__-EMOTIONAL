package frc.robot;

import java.lang.reflect.Field;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LoggedSubsystemBase extends SubsystemBase{
    
    public void putData() {
    String shuffleboardName = this.getClass().getCanonicalName().replace('.', '/').substring(10);
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.getType().equals(com.revrobotics.spark.SparkMax.class)) {
                try {
                    SmartDashboard.putNumber(shuffleboardName + "/SparkMax" + field.getName(), field.getDouble(this.getClass()));
                    System.out.println(field.getName() + " set " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }
            if(field.getType().equals(com.playingwithfusion.TimeOfFlight.class)) {
                try {
                    SmartDashboard.putNumber(shuffleboardName + "/TOF/" + field.getName(), field.getDouble(this.getClass()));
                    System.out.println(field.getName() + " set " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }
            if(field.getType().equals(com.ctre.phoenix6.hardware.TalonFX.class)) {
                try {
                    //SmartDashboard.putNumber(shuffleboardName + "/" + field.getName() + "/position", field.get(this.getClass()));
                    System.out.println(field.getName() + " set " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }


            if (field.getType().isPrimitive()) {
                try {
                    SmartDashboard.putNumber(shuffleboardName + "/" + field.getName(), field.getDouble(this.getClass()));
                    System.out.println(field.getName() + " value: " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }
        }   
    }

    public void getData() {
        String shuffleboardName = this.getClass().getCanonicalName().replace('.', '/').substring(10);
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getType());
            if (field.getType().isPrimitive()) {
                try {
                    field.setDouble(this, SmartDashboard.getNumber(shuffleboardName + "/" + field.getName(), field.getDouble(this.getClass())));
                    System.out.println(field.getName() + " set " + field.getDouble(this.getClass()));
                } catch(IllegalAccessException e) {
                    System.out.println("[" + shuffleboardName + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
                }
            }
        }   
    }


}
