package frc.robot.drivetrain;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
public class DriveSetSideCommand extends Command {
    private double rad;
    private double speed;
    private double currentPos;
    private double coefficent = (DrivetrainConstants.drivetrainWidth*DrivetrainConstants.drivetrainWidth+DrivetrainConstants.drivetrainPivotDepth*DrivetrainConstants.drivetrainPivotDepth) / DrivetrainConstants.drivetrainWidth;
    public DriveSetSideCommand(double degrees, double speed) {
        addRequirements(Robot.instance.drivetrain);
        this.rad = Math.toRadians(degrees);
        if(speed < 0) speed = -speed;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        currentPos = Robot.instance.drivetrain.getLeftPosition();
    }
    @Override
    public void execute() {
        Robot.instance.drivetrain.Set(0, (rad > 0? speed: -speed));
    }
    @Override
    public void end(boolean interrupted) {}
    @Override
    public boolean isFinished() {
        if(rad == 0 || speed == 0) return true;
        else if(rad > 0) {
            return Robot.instance.drivetrain.getLeftPosition() - currentPos >= rad * coefficent;
        }
        else {
            return Robot.instance.drivetrain.getLeftPosition() - currentPos <= rad * coefficent;
        }
    }
}
