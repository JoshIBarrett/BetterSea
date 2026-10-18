package frc.robot;

public enum IDs {
    // CAN IDs

    // IDs 0-9 Reserved for drivetrain
    TANK_DRIVE_LEFT_MAIN(10, "rio"),
    TANK_DRIVE_LEFT_FOLLOWER(11, "rio"),
    TANK_DRIVE_RIGHT_MAIN(12, "rio"),
    TANK_DRIVE_RIGHT_FOLLOWER(13, "rio");
    
    // Rio Digital Ports
    

    public final int id;
    public final String bus;

    private IDs(int id, String bus) {
        this.id = id;
        this.bus = bus;
    }
}
