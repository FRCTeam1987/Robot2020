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
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonFX master;
  private final WPI_TalonFX slave;

  private double rpmSetPoint;

  public Shooter() {
    master = new WPI_TalonFX(Constants.shooterMasterID);
    slave = new WPI_TalonFX(Constants.shooterSlaveID);

    master.configFactoryDefault();
    master.configOpenloopRamp(0.5);
    // master.setInverted(true);
    master.setNeutralMode(NeutralMode.Coast);
    master.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
    master.configClosedloopRamp(0.5);
    master.config_kF(0, 0.0513); //get started umf (increases the actual base rpm exponentially or something) was.052 old BAT, new bat .0498, then was .055 for 3550 rpm, then .052
    master.config_kP(0, 0.25); //p = push and oscillating once it gets there
    master.config_kI(0, 0.0);
    master.config_kD(0, 0.0); //d =  dampening for the oscillation

    slave.configFactoryDefault();
    slave.follow(master);
    slave.setInverted(TalonFXInvertType.OpposeMaster);

    stop();

    addChild("master", master);
    addChild("slave", slave);
  }

  public boolean canShoot() {
    System.out.println("shooter - isOnTarget: " + isOnTarget() + ", isRPM: " + isInRPMTolerance());
    return isOnTarget() && isInRPMTolerance();
  }

  public void stop() {
    setPercent(0);
  }

  public void setRPM(final double rpm) {
    if (rpmSetPoint == rpm) { // don't set the same RPM again
      return;
    }
    double velocity = rpm * Constants.ticksPerRotation / Constants.milliPerMin; //1,000ms per sec, but robot cares about per 100ms, so then 60 sec/min 
    master.set(TalonFXControlMode.Velocity, velocity);
    rpmSetPoint = rpm;
  }

  public double getRPM(){
    return master.getSensorCollection().getIntegratedSensorVelocity() / Constants.ticksPerRotation * Constants.milliPerMin * Constants.shooterReduction;
  }

  public double getAngleError(){
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 0) {
      return 100;
    }
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public boolean isOnTarget() {
    return Util.isWithinTolerance(getAngleError(), 0, Constants.shooterAngleTolerance);
  }

  /**
   * Determines whether or not the shooter should start spinning up to the desired RPM.
   * @return True if the shooter is aimed near the target. False otherwise.
   */
  public boolean shouldSpinUp() {
    return Util.isWithinTolerance(getAngleError(), 0, Constants.shooterAngleErrorToSpinUp);
  }

  /**
   * Determines whether or not the shooter RPM is within tolerance of the setpoint.
   * @return True if the flywheel is running at a RPM close to the setpoint. False otherwise.
   */
  public boolean isInRPMTolerance() {
    return Util.isWithinTolerance(getRPM(), rpmSetPoint, Constants.shooterRPMTolerance);
  }

  public void turnOnLEDs(){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
  }

  public void turnOffLEDs(){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

  }

  public void setPercent(double percent) {
    master.set(percent);
    rpmSetPoint = -9999;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter RPM", getRPM());
  }
}
