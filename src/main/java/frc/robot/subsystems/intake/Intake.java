package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.component.ComponentSubsystem;
import frc.lib.component.HomingPositionMotorComponent;
import frc.lib.component.MotorComponent;
import frc.lib.io.motor.rev.SparkBaseIO;

public class Intake extends ComponentSubsystem {
    private final HomingPositionMotorComponent<SparkBaseIO> pivot;
    private final MotorComponent<SparkBaseIO> rollers;

    public Intake() {
        pivot = registerComponent("Intake Pivot", PivotConstants.getComponent());
        rollers = registerComponent("Intake Rollers", RollerConstants.getComponent());
    }

    public Command intake() {
        return withRequirement(
            Commands.sequence(
                pivot.applyPositionSetpointCommandWithWait(PivotConstants.deploySetpoint),
                rollers.applySetpointCommand(RollerConstants.intakeSetpoint)
            )
        );
    }

    public Command stow() {
        return withRequirement(
            Commands.parallel(
                pivot.applyPositionSetpointCommandWithWait(PivotConstants.stowSetpoint),
                rollers.applySetpointCommand(RollerConstants.idleSetpoint)
            )
        );
    }
}
