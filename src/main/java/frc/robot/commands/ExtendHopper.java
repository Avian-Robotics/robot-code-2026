package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.HopperSubsystem;

public class ExtendHopper extends InstantCommand {

    public ExtendHopper(HopperSubsystem hopper) {
        super(hopper::extend, hopper);
    }
}