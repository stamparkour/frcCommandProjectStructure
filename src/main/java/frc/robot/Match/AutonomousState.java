package frc.robot.Match;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.catapult.ToggleArmCommand;
import frc.robot.drivetrain.DriveSetForwardCommand;
import frc.robot.drivetrain.DriveSetSideCommand;

public class AutonomousState {
    public static Command getAutoCommand() {
        return new SequentialCommandGroup(
            new DriveSetForwardCommand(5,1),
            new DriveSetSideCommand(120,1),
            new ToggleArmCommand(),
            new DriveSetSideCommand(-30,1),
            new ToggleArmCommand(),
            new DriveSetForwardCommand(-2,0.5)
        );
    }
    public static void initialize() {}
    public static void periodic() {}
    public static void exit() {}
}
