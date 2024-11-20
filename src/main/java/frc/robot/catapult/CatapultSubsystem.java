package frc.robot.catapult;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class CatapultSubsystem extends SubsystemBase {
    private CANSparkMax motor;
    private State state = State.Retracted;
    private ShuffleboardTab tab;
    public CatapultSubsystem() {
        tab = Shuffleboard.getTab("Catapult");

        motor = new CANSparkMax(CatapultConstants.launchMotorID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        //`motor.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 5);
        motor.setIdleMode(IdleMode.kBrake);
        motor.setInverted(true);
        tab.addDouble("Current Position", () -> {return motor.getEncoder().getPosition();});
        //Robot.instance.addPeriodic(() -> {periodic();}, 0.005);
    
    }

    @Override
    public void periodic() {
        double pos = motor.getEncoder().getPosition();
        switch(state) {
        case Retracting:
            motor.set(-0.2);
            if(pos < 1) state = State.Retracted;
            break;
        case Extending:
            motor.set(0.7);
            if(pos > 7) state = State.Extended;
            break;
        case Retracted:
        case Extended:
        default:
            motor.set(0);
            break;
        }
    }
    @Override
    public void simulationPeriodic() {}

    void SetExtending() {
        if(state != State.Retracted) return;
        state = State.Extending;
    }
    void SetRetracting() {
        if(state != State.Extended) return;
        state = State.Retracting;
    }

    public enum State {
        Retracting,
        Retracted,
        Extending,
        Extended;
    }
}
