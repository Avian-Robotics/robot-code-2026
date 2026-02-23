package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;


public class ShooterSubsystem extends SubsystemBase {

    private final SparkMax topShooterMotor;
    private final SparkMax bottomShooterMotor;


public ShooterSubsystem(){
    topShooterMotor = new SparkMax(Shooter.TOP_SHOOTER_MOTOR_CAN_ID, MotorType.kBrushless);
    bottomShooterMotor = new SparkMax(Shooter.BOTTOM_SHOOTER_MOTOR_CAN_ID, MotorType.kBrushless);
}

public void setShooterSpeed(double motorSpeed) {
        topShooterMotor.set(motorSpeed);
        bottomShooterMotor.set(-1 * motorSpeed);
    }

    
}

