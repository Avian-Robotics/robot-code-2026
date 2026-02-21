package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Intake;
import frc.robot.commands.SetIntakeCommand;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkMax intakeMotor;
    private final SparkMax rollerMotor;
    
    public IntakeSubsystem(){
        intakeMotor = new SparkMax(Constants.Intake.INTAKE_SPARK_MAX_CAN_ID, MotorType.kBrushless);
        rollerMotor = new SparkMax(Constants.Intake.ROLLER_SPARK_MAX_CAN_ID, MotorType.kBrushless);
    }

    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
        rollerMotor.set(speed);
    }

    public Command intakeInCommand(){
        return new SetIntakeCommand(Intake.MAX_INTAKE_SPEED, this);
    }

    public Command intakeOutCommand() {
        return new SetIntakeCommand(-Intake.MAX_INTAKE_SPEED, this);
    }

    public Command stopIntakeCommand() {
        return new SetIntakeCommand(0, this);
    }

}
