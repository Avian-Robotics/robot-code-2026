package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command{

    private final ShooterSubsystem shooterSubsystem;
    private final double speed;
    private final double timeSeconds;
    private final Timer timer = new Timer();

    public ShootCommand(ShooterSubsystem shooterSubsystem, double speed, double timeSeconds) {

        this.shooterSubsystem = shooterSubsystem;
        this.speed = speed;
        this.timeSeconds = timeSeconds;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        shooterSubsystem.setShooterSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeSeconds);
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.setShooterSpeed(0);
        timer.stop();
    }
    
}
