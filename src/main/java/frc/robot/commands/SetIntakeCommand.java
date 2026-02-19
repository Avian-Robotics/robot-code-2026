package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class SetIntakeCommand extends Command{
    private final IntakeSubsystem groundIntake;
    private final double intakeSpeed;

    public SetIntakeCommand(double intakeSpeed, IntakeSubsystem groundIntake){
        this.groundIntake = groundIntake;
        this.intakeSpeed = intakeSpeed;
        addRequirements(groundIntake);
    }

    @Override
    public void initialize(){
        groundIntake.setIntakeSpeed(intakeSpeed);
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
