package frc.robot.Match;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.catapult.ToggleArmCommand;
import frc.robot.drivetrain.*;

public class RobotState {
    public static XboxController controller;
    public static void Initialize() {
        controller = new XboxController(0);
        new JoystickButton(controller, XboxController.Button.kA.value).onTrue(new ToggleArmCommand());
        Robot.instance.drivetrain.setDefaultCommand(new DriveCommand(controller::getLeftY,controller::getLeftX));
    }
    public static void Periodic() {}
}
