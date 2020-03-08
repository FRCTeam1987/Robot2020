/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonFX master;
  private final TalonFX slave;

  private double rpmSetPoint;

  private boolean isFarShot;


  public Shooter() {
    master = new WPI_TalonFX(Constants.shooterMasterID);
    slave = new TalonFX(Constants.shooterSlaveID);

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
    // addChild("slave", slave);

    isFarShot = false;
    turnOnLEDs();
  }

  public boolean canShoot() {
    // System.out.println("shooter - isOnTarget: " + isOnTarget() + ", isRPM: " + isInRPMTolerance());
    return isOnTarget() && isInRPMTolerance();
  }

  public void stop() {
    setPercent(0);
  }

  public void setRPM(final double rpm) {
    SmartDashboard.putNumber("(attemped) shooter rpm setpoint", rpm);
    if (Util.isWithinTolerance(rpm, rpmSetPoint, Constants.shooterRPMTolerance)){
      return;
    }
    double velocity = rpm * Constants.ticksPerRotation / Constants.milliPerMin; //1,000ms per sec, but robot cares about per 100ms, so then 60 sec/min 
    master.set(TalonFXControlMode.Velocity, velocity);
    rpmSetPoint = rpm;
    SmartDashboard.putNumber("(actual) shooter rpm setpoint", rpm);
  }

  public double getRPM(){
    return master.getSensorCollection().getIntegratedSensorVelocity() / Constants.ticksPerRotation * Constants.milliPerMin * Constants.shooterReduction;
  }

  public boolean canSeeTarget() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1;
  }

  public double getAngleError(){
    if (!canSeeTarget()) {
      return 100;
    }
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public double getTY(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
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

  public void toggleIsFarShot(){
    isFarShot = !isFarShot;
    if(isFarShot){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
    } else{
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    }
  }

  public boolean getIsFarShot(){
    return isFarShot;
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
    SmartDashboard.putBoolean("isFarShot", isFarShot);
  }
}

