package frc.robot.drivetrain;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
public class DriveSetForwardCommand extends Command {
    private double distance;
    private double speed;
    private double currentPos;
    public DriveSetForwardCommand(double distance, double speed) {
        addRequirements(Robot.instance.drivetrain);
        this.distance = distance;
        if(speed < 0) speed = -speed;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        currentPos = Robot.instance.drivetrain.getLeftPosition();
    }
    @Override
    public void execute() {
        Robot.instance.drivetrain.Set((distance > 0? speed: -speed), 0);
    }
    @Override
    public void end(boolean interrupted) {}
    @Override
    public boolean isFinished() {
        if(distance == 0 || speed == 0) return true;
        else if(distance > 0) {
            return Robot.instance.drivetrain.getLeftPosition() - currentPos >= distance;
        }
        else {
            return Robot.instance.drivetrain.getLeftPosition() - currentPos <= distance;
        }
    }
}
