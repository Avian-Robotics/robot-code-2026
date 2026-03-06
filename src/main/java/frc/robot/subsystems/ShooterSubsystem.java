package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;
import com.ctre.phoenix6.hardware.TalonFX;


public class ShooterSubsystem extends SubsystemBase {

    private final TalonFX topShooterMotor;
    private final SparkMax bottomShooterMotor;


public ShooterSubsystem(){
    topShooterMotor = new TalonFX(Shooter.TOP_SHOOTER_MOTOR_CAN_ID);
    bottomShooterMotor = new SparkMax(Shooter.BOTTOM_SHOOTER_MOTOR_CAN_ID, MotorType.kBrushless);
}

public void setShooterSpeed(double motorSpeed) {
        topShooterMotor.set(-1 * motorSpeed);
        bottomShooterMotor.set(-1 * motorSpeed);
    }

    
}

