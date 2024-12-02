package frc.robot.drivetrain;

public class DrivetrainConstants {
    final public static double kTicksPerDegree = 4096.0/360;
    final public static double drivetrainWidth = 0.65; // meters
    final public static double drivetrainPivotDepth = 0.2; // meters
    final public static double wheelRadius = 0.0762; // 3in in meters

    final public static int leftLeaderID = 4;
    final public static int leftFollowerID = 3;
    final public static int rightLeaderID = 1;
    final public static int rightFollowerID = 2;

    final public static double maxSpeed = 0.8; // meters / second
    final public static double maxTurnSpeed = 1.3; // rad / second
    final public static double deadzone = 0.25;

    final public static double leftP = 0.4;
    final public static double leftI = 0;
    final public static double leftD = 0;
    final public static double rightP = 0.4;
    final public static double rightI = 0;
    final public static double rightD = 0;
}
