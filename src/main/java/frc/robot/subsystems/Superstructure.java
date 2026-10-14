package frc.robot.subsystems;

import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.exampleArm.ExampleArm;
import frc.robot.subsystems.exampleClimber.ExampleClimber;
import frc.robot.subsystems.exampleIntake.ExampleIntake;
import frc.robot.subsystems.exampleShooter.ExampleShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.logging.Loggable;
import frc.lib.util.logging.Logger;

public class Superstructure implements Loggable {
    public final Drive drive = new Drive();
   
    @Override
    public void log(String path) {
        Logger.log(path, "Drive", drive);
       
    }
}
