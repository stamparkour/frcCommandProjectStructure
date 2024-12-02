package frc.robot.catapult;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
public class ToggleArmCommand extends Command {
    
    public ToggleArmCommand() {
        addRequirements(Robot.instance.catapult);
    }

    @Override
    public void initialize() {
        Robot.instance.catapult.SetExtending();
        Robot.instance.catapult.SetRetracting();
    }
    @Override
    public void execute() {}
    @Override
    public void end(boolean interrupted) {}
    @Override
    public boolean isFinished() {
        return true;
    }
}
