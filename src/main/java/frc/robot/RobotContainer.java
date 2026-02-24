// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.commands.ShootCommand;

public class RobotContainer {

  private final ShooterSubsystem shooterSubsystem;
  private final CommandXboxController operatorController = new CommandXboxController(1);

  public RobotContainer() {
    shooterSubsystem = new ShooterSubsystem();
    configureBindings();
  }

  private void configureBindings() {

    operatorController.y().onTrue(new ShootCommand(shooterSubsystem, Constants.Shooter.SHOOT_SPEED, Constants.Shooter.SHOOT_TIME_SECONDS));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
