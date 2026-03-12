package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.HopperSubsystem;

public class RetractHopper extends InstantCommand {

    public RetractHopper(HopperSubsystem hopper) {
        super(hopper::retract, hopper);
    }
}