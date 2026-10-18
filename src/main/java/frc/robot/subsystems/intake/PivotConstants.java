package frc.robot.subsystems.intake;


import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.MomentOfInertia;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.lib.component.HomingPositionMotorComponent;
import frc.lib.component.HomingPositionMotorComponent.HomingConfig;
import frc.lib.io.motor.rev.SparkBaseIO;
import frc.lib.io.motor.rev.SparkBaseSimIO;
import frc.lib.io.motor.setpoints.PositionSetpoint;
import frc.lib.mechanismSim.PivotSim;
import frc.lib.mechanismSim.SimObject;
import frc.lib.util.ConfigUtil;
import frc.robot.IDs;
import frc.robot.Robot;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;

public class PivotConstants {
    public static final Angle epsilonThreshold = Degrees.of(5);
    public static final double gearing = 34/16.; 
    public static final MomentOfInertia moi = KilogramSquareMeters.of(0.2);
    public static final Distance length = Meters.of(0.3);
    public static final Angle minAngle = Degrees.of(-10);
    public static final Angle maxAngle = Degrees.of(100); // there is no way we getting to 100

    public static final Angle deployAngle = Degrees.of(0);
    public static final Angle stowAngle = maxAngle;

    public static final PositionSetpoint deploySetpoint = new PositionSetpoint(deployAngle);
    public static final PositionSetpoint stowSetpoint = new PositionSetpoint(stowAngle);

    public static final DCMotor motor = DCMotor.getNEO(1);

    public static final SimObject getSimObject() {
        SingleJointedArmSim system = new SingleJointedArmSim(
            motor, 
            gearing, 
            moi.in(KilogramSquareMeters), 
            length.in(Meters), 
            minAngle.in(Radians), 
            maxAngle.in(Radians), 
            true, 
            0, 
            0
        );
        return new PivotSim(system);
    }

    public static final HomingConfig getHomingConfig() {
        HomingConfig config = new HomingConfig();
        config.homePosition = stowAngle;
        config.homeSetpoint = stowSetpoint;
        config.homingDebouce = Seconds.of(0.1);
        config.homingVelocity = DegreesPerSecond.of(1);
        config.homingVoltage = Volts.of(2.0);
        return config;
    }

    public static final SparkBaseConfig getMainConfig() {
        SparkBaseConfig config = ConfigUtil.getSafeMaxConfig(gearing);
        ConfigUtil.withSoftLimits(config, maxAngle, minAngle);
        config.closedLoop.p(10);
        config.closedLoop.d(10);
        return config;
    }

    @SuppressWarnings("unchecked")
    public static final SparkBaseIO getMotorIO() {
        return Robot.isReal()
            ? new SparkBaseIO(
                MotorType.kBrushless, 
                getMainConfig(), 
                IDs.INTAKE_PIVOT.id
            )
            : new SparkBaseSimIO(
                getSimObject(), 
                motor, 
                MotorType.kBrushless, 
                getMainConfig(), 
                IDs.INTAKE_PIVOT.id
            );
    }

    public static final HomingPositionMotorComponent<SparkBaseIO> getComponent() {
        SparkBaseIO io = getMotorIO();
        io.overrideLoggedUnits(Degrees, DegreesPerSecond, Celsius);
        return new HomingPositionMotorComponent<SparkBaseIO>(io, epsilonThreshold, stowAngle, getHomingConfig());
    }
}
