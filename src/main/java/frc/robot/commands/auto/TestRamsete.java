/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.TankDriveInstant;
import frc.robot.lib.RamseteHelper;
import frc.robot.subsystems.Drive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TestRamsete extends SequentialCommandGroup {
  /**
   * Creates a new TestRamsete.
   */
  public TestRamsete(final Drive drive) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      RamseteHelper.runRamsete(
          drive,
          RamseteHelper.createTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(
              new Translation2d(1, 1),
              new Translation2d(2, -1)
            ),
            new Pose2d(3, 0, new Rotation2d(0)),
            false
          )
        ),
        new TankDriveInstant(drive, 0)
    );
  }
}
