package frc.robot.subsystems.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.component.ComponentSubsystem;
import frc.lib.component.MotorComponent;
import frc.lib.io.motor.rev.SparkBaseIO;
import frc.lib.io.motor.setpoints.VoltageSetpoint;


public class TankDrive extends ComponentSubsystem {
    private final DifferentialDrive driveController;
    private final MotorComponent<SparkBaseIO> leftMotor;
    private final MotorComponent<SparkBaseIO> rightMotor;

    public TankDrive() {
        registerComponent("Left", leftMotor = TankDriveConstants.getComponent(true));
        registerComponent("Right", rightMotor = TankDriveConstants.getComponent(false));

        driveController = new DifferentialDrive(
            d -> leftMotor.applySetpoint(new VoltageSetpoint(TankDriveConstants.maxVoltage.times(d))), 
            d -> rightMotor.applySetpoint(new VoltageSetpoint(TankDriveConstants.maxVoltage.times(d)))
        );
    }

    public Command drive(DoubleSupplier forwardsSpeed, DoubleSupplier turnSpeed) {
        return withRequirement(
            Commands.run(() -> driveController.arcadeDrive(forwardsSpeed.getAsDouble(), turnSpeed.getAsDouble()))
        );
    }
}
