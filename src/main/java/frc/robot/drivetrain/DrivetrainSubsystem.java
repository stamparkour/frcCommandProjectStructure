package frc.robot.drivetrain;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {
    private TalonSRX leftLeader;
    private TalonSRX leftFollower;
    private TalonSRX rightLeader;
    private TalonSRX rightFollower;
    private PIDController pidLeft;
    private PIDController pidRight;
    private DifferentialDriveKinematics kinematics;
    private ShuffleboardTab tab;
    private double forward;
    private double side;
    public DrivetrainSubsystem() {
        tab = Shuffleboard.getTab("Drivetrain");

        leftLeader = new TalonSRX(DrivetrainConstants.leftLeaderID);
        leftFollower = new TalonSRX(DrivetrainConstants.leftFollowerID);
        rightLeader = new TalonSRX(DrivetrainConstants.rightLeaderID);
        rightFollower = new TalonSRX(DrivetrainConstants.rightFollowerID);

        leftLeader.configFactoryDefault();
        rightLeader.configFactoryDefault();
        rightFollower.follow(rightLeader);  
        leftFollower.follow(leftLeader);
        leftLeader.setInverted(true);
        rightLeader.setInverted(false);
        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster);

        pidLeft = new PIDController(DrivetrainConstants.leftP, DrivetrainConstants.leftI,DrivetrainConstants.leftD);
        pidRight = new PIDController(DrivetrainConstants.rightP, DrivetrainConstants.rightI,DrivetrainConstants.rightD);

        kinematics = new DifferentialDriveKinematics(DrivetrainConstants.drivetrainWidth);
    
        tab.addDouble("Current Left Speed", () -> {return getLeftSpeed();});
        tab.addDouble("Current Right Speed", () -> {return getRightSpeed();});
    }

    @Override
    public void periodic() {
        double s = clamp(deadZone(side,DrivetrainConstants.deadzone),1) * DrivetrainConstants.maxTurnSpeed;
        double f = -clamp(deadZone(forward,DrivetrainConstants.deadzone),1) * DrivetrainConstants.maxSpeed;

        DifferentialDriveWheelSpeeds k = kinematics.toWheelSpeeds(new ChassisSpeeds(f,0,s));
        
        leftLeader.set(TalonSRXControlMode.PercentOutput,pidLeft.calculate(getLeftSpeed(), k.leftMetersPerSecond));
        rightLeader.set(TalonSRXControlMode.PercentOutput,pidRight.calculate(getRightSpeed(), k.rightMetersPerSecond));
    }
    @Override
    public void simulationPeriodic() {}

    public double getLeftSpeed() {
        return leftLeader.getSelectedSensorVelocity() * DrivetrainConstants.kTicksPerDegree * 0.0762; // pi / 180 * 4.36 cm
    }
    public double getRightSpeed() {
        return rightLeader.getSelectedSensorVelocity() * DrivetrainConstants.kTicksPerDegree * 0.0762; // pi / 180 * 4.36 cm
    }
    public double getLeftPosition() {
        return leftLeader.getSelectedSensorPosition() * DrivetrainConstants.kTicksPerDegree * 0.0762; // pi / 180 * 4.36 cm
    }
    public double getRightPosition() {
        return rightLeader.getSelectedSensorPosition() * DrivetrainConstants.kTicksPerDegree * DrivetrainConstants.wheelRadius;
    }

    void Set(double forward, double side) {
        this.forward = forward;
        this.side = side;
    }

    private double clamp(double v, double mag) {
        return (v > mag ? mag: (v < -mag ? -mag: v));
    }
    private double deadZone(double v, double mag) {
        v*= 1+mag;
        return (v > mag ? v-mag: (v < -mag ? v+mag: 0));
    }
}
