package frc.robot.subsystems.intake;

import frc.lib.component.ComponentSubsystem;
import frc.lib.component.HomingPositionMotorComponent;
import frc.lib.component.MotorComponent;
import frc.lib.io.motor.rev.SparkBaseIO;

public class Intake extends ComponentSubsystem {
    private final HomingPositionMotorComponent<SparkBaseIO> pivot;
    private final MotorComponent<SparkBaseIO> rollers;

    public Intake() {
        
    }
}
