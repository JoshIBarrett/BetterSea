package frc.robot.subsystems;

import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.TankDrive;
import frc.robot.subsystems.vision.Vision;
import frc.lib.util.logging.Loggable;
import frc.lib.util.logging.Logger;
import frc.lib.vision.VisionSubsystem;

public class Superstructure implements Loggable {
    // public final Drive drive = new Drive();
    public final TankDrive tankDrive = new TankDrive();
    public final Vision vision = new Vision(tankDrive::addVisionMeasurement);

    @Override
    public void log(String path) {
        // Logger.log(path, "Drive", drive);
       Logger.log(path, "Tank Drive", tankDrive);
    }
}
