package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperSubsystem extends SubsystemBase {

    private final Servo leftActuator = new Servo(2);
    private final Servo rightActuator = new Servo(1);

    // positions (tune these)
    private static final double EXTEND = 1.0;
    private static final double RETRACT = 0.0;

    public HopperSubsystem() {}

    public void extend() {
        leftActuator.set(EXTEND);
        rightActuator.set(EXTEND);
    }

    public void retract() {
        leftActuator.set(RETRACT);
        rightActuator.set(RETRACT);
    }
}