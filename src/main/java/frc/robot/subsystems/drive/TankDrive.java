package frc.robot.subsystems.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.component.ComponentSubsystem;
import frc.lib.component.MotorComponent;
import frc.lib.io.motor.rev.SparkBaseIO;
import frc.lib.io.motor.setpoints.VoltageSetpoint;
import frc.robot.subsystems.vision.VisionConstants;
import frc.lib.util.logging.Logger;


public class TankDrive extends ComponentSubsystem {
    private final MotorComponent<SparkBaseIO> leftMotor;
    private final MotorComponent<SparkBaseIO> rightMotor;
    private final DifferentialDrive driveController;
    private final DifferentialDrivePoseEstimator poseEstimator; // Not actually calling update on loop as recommended because drivetrain doesn't have odometry
    private final DifferentialDriveKinematics kinematics;
    private Pose2d pose = new Pose2d();

    public TankDrive() {
        registerComponent("Left", leftMotor = TankDriveConstants.getComponent(true));
        registerComponent("Right", rightMotor = TankDriveConstants.getComponent(false));

        driveController = new DifferentialDrive(
            d -> leftMotor.applySetpointCommand(new VoltageSetpoint(TankDriveConstants.maxVoltage.times(d))), 
            d -> rightMotor.applySetpointCommand(new VoltageSetpoint(TankDriveConstants.maxVoltage.times(d)))
        );
        kinematics = new DifferentialDriveKinematics(TankDriveConstants.trackWidth);
        poseEstimator = new DifferentialDrivePoseEstimator(kinematics, Rotation2d.kZero, 0.0, 0.0, new Pose2d(), TankDriveConstants.stdDevs, VisionConstants.stdDevs);
    }

    public Command drive(DoubleSupplier forwardsSpeed, DoubleSupplier turnSpeed) {
        return withRequirement(
            Commands.run(() -> driveController.arcadeDrive(forwardsSpeed.getAsDouble(), turnSpeed.getAsDouble()))
        );
    }

    public void addVisionMeasurement(Pose2d pose, double timestampSeconds) {
        poseEstimator.addVisionMeasurement(pose, timestampSeconds);
    }

    @Override
    public void periodic() {
        super.periodic();
        pose = poseEstimator.getEstimatedPosition();
    }

    @Override
    public void log(String name) {
        super.log(name);
        Logger.log(name, "Drive Pose", pose);
    }

    public Pose2d getPose() {
        return pose;
    }
}
