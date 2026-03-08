package frc.robot;

public final class Constants {

    public static final class Shooter {
        public static final int TOP_SHOOTER_MOTOR_CAN_ID = 16;
        public static final int BOTTOM_SHOOTER_MOTOR_CAN_ID = 9;

        public static final double SHOOT_SPEED = 0.95;
        public static final double SHOOT_TIME_SECONDS = 10.0;
    }

    public static final class Intake {
        public static final int INTAKE_SPARK_MAX_CAN_ID = 7;
        public static final int ROLLER_SPARK_MAX_CAN_ID = 10;
        public static final double MAX_INTAKE_SPEED = 0.65;
    }
}
