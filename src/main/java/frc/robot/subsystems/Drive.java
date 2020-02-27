/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util;

public class Drive extends SubsystemBase {

  // TODO make LazyTalonFX - see Jon
  private final WPI_TalonFX leftMaster;
  private final WPI_TalonFX leftSlave;
  private final WPI_TalonFX rightMaster;
  private final WPI_TalonFX rightSlave;

  private TalonFXSensorCollection m_leftSensorCollection;
  private TalonFXSensorCollection m_rightSensorCollection;

  private final AHRS ahrs;
  private final DifferentialDriveOdometry m_odometry;

  private final DifferentialDrive robotDrive;

  // private static FalconDrive instance = null;

  // TODO implement auto driving methods
  /**
   * Creates a new Drive.
   */
  public Drive() {
    leftMaster = new WPI_TalonFX(Constants.leftMasterID);
    leftSlave = new WPI_TalonFX(Constants.leftSlaveID);
    rightMaster = new WPI_TalonFX(Constants.rightMasterID);
    rightSlave = new WPI_TalonFX(Constants.rightSlaveID);
    robotDrive = new DifferentialDrive(leftMaster, rightMaster);
    ahrs = new AHRS(Port.kMXP);
    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    robotDrive.setSafetyEnabled(false);
    // TODO invert right side of DifferentialDrive

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.configFactoryDefault();
    leftMaster.setNeutralMode(NeutralMode.Coast);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
    m_leftSensorCollection = leftMaster.getSensorCollection();

    rightMaster.configFactoryDefault();
    rightMaster.setNeutralMode(NeutralMode.Coast);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
    m_rightSensorCollection = rightMaster.getSensorCollection();
  }

  public void tankDrive (final double left, final double right){
    // System.out.println("aimbot-output: " + left + ", " + right);
    robotDrive.tankDrive(left, right);
  }
  
  public void arcadeDrive(final double move, final double rotate) {
    robotDrive.arcadeDrive(move, rotate, true);
  }

  public void curvatureDrive(final double move, final double rotate, final boolean isQuickTurn) {
    robotDrive.curvatureDrive(move, rotate, isQuickTurn);
  }

  public void setPercent(double percent) {
    rightMaster.set(ControlMode.PercentOutput, percent);
    leftMaster.set(ControlMode.PercentOutput, percent);
    robotDrive.feed();
  }

  public int getLeftPosition(){
    return leftMaster.getSelectedSensorPosition();
  }

  public int getLRightPosition(){
    return rightMaster.getSelectedSensorPosition();
  }

  // Begin fancy driving stuff

  public void driveTankVolts(final double left, final double right) {
    leftMaster.setVoltage(left);
    rightMaster.setVoltage(right);
    robotDrive.feed();
  }

  private double getDistance(final TalonFXSensorCollection sensorCollection) {
    return Util.ticksToDistance(sensorCollection.getIntegratedSensorPosition(), Constants.Drive.Controls.ticksPerRevolution, Constants.Drive.Controls.wheelCircumference);
  }

  public double getDistanceAverage() {
    return (getDistanceLeft() + getDistanceRight()) / 2.0;
  }

  public double getDistanceLeft() {
    return getDistance(m_leftSensorCollection);
  }

  public double getDistanceRight() {
    return -getDistance(m_rightSensorCollection);
  }

  public double getHeading() {
    return -ahrs.getYaw();
  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  public double getRotationRate() {
    return -ahrs.getRate();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
      getWheelVelocityLeft(),
      getWheelVelocityRight()
    );
  }

  private double getWheelVelocity(final TalonFXSensorCollection sensorCollection) {
    return Util.ctreVelocityToLinearVelocity(
      sensorCollection.getIntegratedSensorVelocity(),
      Constants.Drive.Controls.ticksPerRevolution,
      Constants.Drive.Controls.wheelCircumference
    );
  }

  public double getWheelVelocityLeft() {
    return getWheelVelocity(m_leftSensorCollection);
  }

  public double getWheelVelocityRight() {
    return -getWheelVelocity(m_rightSensorCollection);
  }

  public void resetOdometry(final Pose2d newPose) {
    zeroEncoders();
    m_odometry.resetPosition(newPose, Rotation2d.fromDegrees(getHeading()));
  }

  public void setBrake() {
    setNeutralMode(NeutralMode.Brake);
  }

  public void setCoast() {
    setNeutralMode(NeutralMode.Coast);
  }

  public void setNeutralMode(final NeutralMode neutralMode) {
    leftMaster.setNeutralMode(neutralMode);
    leftSlave.setNeutralMode(neutralMode);
    rightMaster.setNeutralMode(neutralMode);
    rightSlave.setNeutralMode(neutralMode);
  }

  public void zeroEncoders() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  public void zeroHeading() {
    ahrs.reset();
  }

  public void zeroSensors() {
    zeroEncoders();
    zeroHeading();
  }

  // End fancy driving stuff

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_leftSensorCollection = leftMaster.getSensorCollection();
    m_rightSensorCollection = rightMaster.getSensorCollection();

    final double heading = getHeading();
    final double distanceLeft = getDistanceLeft();
    final double distanceRight = getDistanceRight();
    final DifferentialDriveWheelSpeeds wheelSpeeds = getWheelSpeeds();

    m_odometry.update(
      Rotation2d.fromDegrees(heading),
      distanceLeft,
      distanceRight
    );

    SmartDashboard.putNumber("drive_heading", heading);
    SmartDashboard.putNumber("drive_dist_left", distanceLeft);
    SmartDashboard.putNumber("drive_dist_right", distanceRight);
    SmartDashboard.putNumber("drive_vel_left", wheelSpeeds.leftMetersPerSecond);
    SmartDashboard.putNumber("drive_vel_right", wheelSpeeds.rightMetersPerSecond);
    var translation = m_odometry.getPoseMeters().getTranslation();
    SmartDashboard.putNumber("drive_odometry_x", translation.getX());
    SmartDashboard.putNumber("drive_odometry_y", translation.getY());
  }
}
