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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.collector.DeployCollector;
import frc.robot.commands.collector.RetractCollector;
import frc.robot.commands.drive.AimBot;
import frc.robot.commands.drive.TankDriveInstant;
import frc.robot.commands.elevator.WaitUntilNoBallsInLift;
import frc.robot.lib.RamseteHelper;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Shoot3AndGet3FromTrenchAndShoot extends SequentialCommandGroup {
  /**
   * Creates a new Shoot3AndGet3FromTrenchAndShoot.
   */
  public Shoot3AndGet3FromTrenchAndShoot(final Drive drive, final Shooter shooter, final Elevator elevator, final Collector collector) {
    super(
      new ParallelRaceGroup(
        new AimBot(shooter, drive, false),
        new WaitUntilNoBallsInLift(elevator),
        new WaitCommand(2)
      ),
      new ParallelCommandGroup(
        new DeployCollector(collector),
        RamseteHelper.runRamsete(
          drive,
          RamseteHelper.createTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(
              new Translation2d(1, 0),
              new Translation2d(2.5, 1.75)
            ),
            new Pose2d(4.5, 1.75, new Rotation2d(0)),
            false
          )
        )
      ),
      new ParallelCommandGroup(
        new RetractCollector(collector),
        new TankDriveInstant(drive, -0.5)
          .andThen(new WaitCommand(0.5))
          .andThen(new TankDriveInstant(drive, 0))
      ),
      new ParallelRaceGroup(
        new AimBot(shooter, drive, false),
        new WaitUntilNoBallsInLift(elevator)
      )
    );
  }
}
