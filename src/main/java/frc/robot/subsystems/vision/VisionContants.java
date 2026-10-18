package frc.robot.subsystems.vision;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.vision.PhotonComponent;

public class VisionContants {
    public static final String cameraName = "cam1";
    public static AprilTagFieldLayout fieldLayout;
    public static Transform3d robotToCam = new Transform3d(new Translation3d(), new Rotation3d());

    static {
        try {
            fieldLayout = new AprilTagFieldLayout("/field_layout.json");
            SmartDashboard.putString("Vision Found File: ", "SUCCESS");

        } catch (Exception e) {
            fieldLayout = null;
            SmartDashboard.putString("Vision Found File: ", "FAIL");
        }
    }
    



    public static PhotonComponent getComponent() {
        return new PhotonComponent(cameraName, fieldLayout, robotToCam, () -> Rotation2d.kZero);
        
        
    }
}
