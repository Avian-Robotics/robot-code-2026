// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.auto.AutoBuilder;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.commands.ExtendHopper;
import frc.robot.commands.RetractHopper;
import frc.robot.commands.ShootCommand;

public class RobotContainer {

    // ================= Swerve Config =================

    private final double MaxSpeed =
            1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);

    private final double MaxAngularRate =
            RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    private final SwerveRequest.FieldCentric drive =
            new SwerveRequest.FieldCentric()
                    .withDeadband(MaxSpeed * 0.1)
                    .withRotationalDeadband(MaxAngularRate * 0.1)
                    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.SwerveDriveBrake brake =
            new SwerveRequest.SwerveDriveBrake();

    private final SwerveRequest.PointWheelsAt point =
            new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    public final CommandSwerveDrivetrain drivetrain =
            TunerConstants.createDrivetrain();

    // ================= Controllers =================

    private final CommandXboxController joystick =
            new CommandXboxController(0);

    private final CommandXboxController operatorController =
            new CommandXboxController(1);

    // ================= Subsystems =================

    private final ShooterSubsystem shooterSubsystem =
            new ShooterSubsystem();

    private final IntakeSubsystem intakeSubsystem =
            new IntakeSubsystem();

    private final HopperSubsystem hopperSubsystem = 
            new HopperSubsystem();

    // ================= Auto Chooser =================

    private final SendableChooser<Command> autoChooser =
            new SendableChooser<>();

    // ================= Constructor =================

    public RobotContainer() {

        configureNamedCommands();

        drivetrain.configureAutoBuilder();

        configureAutos();

        configureBindings();
    }

    // ================= Named Commands (Event Markers) =================

    private void configureNamedCommands() {

        NamedCommands.registerCommand(
                "Intake",
                intakeSubsystem.intakeInCommand()
        );

        NamedCommands.registerCommand(
                "Shoot",
                new ShootCommand(
                        shooterSubsystem,
                        Constants.Shooter.SHOOT_SPEED,
                        Constants.Shooter.SHOOT_TIME_SECONDS
                )
        );
    }

    // ================= Load Autos =================

    private void configureAutos() {

        Command leftAuto =
                AutoBuilder.buildAuto("Intake and Shoot Left");

        Command rightAuto =
                AutoBuilder.buildAuto("Intake and Shoot Right");

        autoChooser.setDefaultOption("Left Auto", leftAuto);
        autoChooser.addOption("Right Auto", rightAuto);

        Shuffleboard.getTab("Autonomous")
                .add("Auto Selector", autoChooser)
                .withWidget(BuiltInWidgets.kComboBoxChooser)
                .withSize(3, 1)
                .withPosition(0, 0);
    }

    // ================= Button Bindings =================

    private void configureBindings() {

        drivetrain.setDefaultCommand(
                drivetrain.applyRequest(() ->
                        drive.withVelocityX(-joystick.getLeftY() * MaxSpeed)
                                .withVelocityY(-joystick.getLeftX() * MaxSpeed)
                                .withRotationalRate(-joystick.getRightX() * MaxAngularRate)
                )
        );

        final var idle = new SwerveRequest.Idle();

        RobotModeTriggers.disabled().whileTrue(
                drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));

        joystick.b().whileTrue(drivetrain.applyRequest(() ->
                point.withModuleDirection(
                        new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX())
                )
        ));

        joystick.back().and(joystick.y())
                .whileTrue(drivetrain.sysIdDynamic(Direction.kForward));

        joystick.back().and(joystick.x())
                .whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));

        joystick.start().and(joystick.y())
                .whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));

        joystick.start().and(joystick.x())
                .whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        joystick.leftBumper()
                .onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);

        // Shooter
        operatorController.y().onTrue(
                new ShootCommand(
                        shooterSubsystem,
                        Constants.Shooter.SHOOT_SPEED,
                        Constants.Shooter.SHOOT_TIME_SECONDS
                )
        );

        // Intake
        operatorController.rightTrigger()
                .whileTrue(intakeSubsystem.intakeInCommand());

        operatorController.leftTrigger()
                .whileTrue(intakeSubsystem.intakeOutCommand());
        // Hopper
        operatorController.a().onTrue(new ExtendHopper(hopperSubsystem));
        operatorController.b().onTrue(new RetractHopper(hopperSubsystem));
    }
        
        

    // ================= Autonomous =================

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}