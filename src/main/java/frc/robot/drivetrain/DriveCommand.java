package frc.robot.drivetrain;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
public class DriveCommand extends Command {
    private Supplier<Double> forward;
    private Supplier<Double> side;
    public DriveCommand(Supplier<Double> forward, Supplier<Double> side) {
        addRequirements(Robot.instance.drivetrain);
        this.forward = forward;
        this.side = side;
    }

    @Override
    public void initialize() {}
    @Override
    public void execute() {
        Robot.instance.drivetrain.Set(forward.get(), side.get());
    }
    @Override
    public void end(boolean interrupted) {}
    @Override
    public boolean isFinished() {
        return false;
    }
}
