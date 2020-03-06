/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib;

import java.util.List;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

/**
 * Add your docs here.
 */
public class RamseteHelper {

  private static final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(Constants.Drive.Controls.trackWidth);

  private static final SimpleMotorFeedforward motorFeedforward = new SimpleMotorFeedforward(
    Constants.Drive.Controls.sVolts,
    Constants.Drive.Controls.vVoltSecondsPerMeter,
    Constants.Drive.Controls.aVoltSecondsSquaredPerMeter
  );

  private static final DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
    motorFeedforward,
    driveKinematics,
    Constants.Drive.Controls.autoMaxVoltage
  );

  private static TrajectoryConfig createTrajectoryConfig(final boolean isReversed) {
    return new TrajectoryConfig(
      Constants.Drive.Controls.maxSpeedMetersPerSecond,
      Constants.Drive.Controls.maxAccelerationMetersPerSecondSquared
    ).setKinematics(driveKinematics)
    .addConstraint(voltageConstraint)
    .setReversed(isReversed);
  }

  public static Trajectory createTrajectory(final Pose2d beginning, final List<Translation2d> middle, final Pose2d end, final boolean isReversed) {
    return TrajectoryGenerator.generateTrajectory(beginning, middle, end, createTrajectoryConfig(isReversed));
  }

  public static Command runRamsete(final Drive drive, final Trajectory trajectory) {
    return new RamseteCommand(
      trajectory,
      drive::getPose,
      new RamseteController(
        Constants.Drive.Controls.ramseteB,
        Constants.Drive.Controls.ramseteZeta
      ),
      motorFeedforward,
      driveKinematics,
      drive::getWheelSpeeds,
      new PIDController(Constants.Drive.Controls.pDriveVel, 0, 0),
      new PIDController(Constants.Drive.Controls.pDriveVel, 0, 0),
      drive::driveTankVolts,
      drive
    ).andThen(() -> drive.driveTankVolts(0, 0));
  }
}
