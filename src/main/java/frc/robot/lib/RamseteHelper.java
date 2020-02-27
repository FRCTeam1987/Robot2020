/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib;

import com.ctre.phoenix.motion.TrajectoryPoint;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.Constants;

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
}
