package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.MomentOfInertia;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.lib.component.MotorComponent;
import frc.lib.io.motor.rev.SparkBaseIO;
import frc.lib.io.motor.rev.SparkBaseSimIO;
import frc.lib.io.motor.setpoints.IdleSetpoint;
import frc.lib.io.motor.setpoints.VoltageSetpoint;
import frc.lib.mechanismSim.RollerSim;
import frc.lib.mechanismSim.SimObject;
import frc.lib.util.ConfigUtil;
import frc.robot.IDs;
import frc.robot.Robot;

public class RollerConstants {
    public static final double gearing = 40/16.;
    public static DCMotor motor = DCMotor.getNEO(5);
    public static MomentOfInertia moi = KilogramSquareMeters.of(0.01);


    public static final Voltage intakeVoltage = Volts.of(8);
    public static final Voltage outputVoltage = Volts.of(-6);

    public static final VoltageSetpoint intakeSetpoint = new VoltageSetpoint(intakeVoltage);
    public static final VoltageSetpoint outputSetpoint = new VoltageSetpoint(outputVoltage);
    public static final IdleSetpoint idleSetpoint = new IdleSetpoint();

    public static final SimObject getSimObject() {
        FlywheelSim system = new FlywheelSim(
            LinearSystemId.createFlywheelSystem(
                motor, 
                moi.in(KilogramSquareMeters), 
                gearing
            ), 
            motor);
        return new RollerSim(system);
    }

    public static final SparkBaseConfig getMainConfig() {
        SparkBaseConfig config = ConfigUtil.getSafeMaxConfig(gearing);
        return config;
    }

    @SuppressWarnings("unchecked")
    public static final SparkBaseIO getMotorIO() {
        return Robot.isReal()
            ? new SparkBaseIO(
                MotorType.kBrushless,
                getMainConfig(),
                IDs.INTAKE_ROLLER.id
            )
            : new SparkBaseSimIO(
                getSimObject(), 
                motor,
                MotorType.kBrushless,
                getMainConfig(),
                IDs.INTAKE_ROLLER.id
            );
    }

    public static final MotorComponent<SparkBaseIO> getComponent() {
        SparkBaseIO io = getMotorIO();
        io.overrideLoggedUnits(Rotations, RPM, Celsius);
        return new MotorComponent<SparkBaseIO>(io);
    }
}
