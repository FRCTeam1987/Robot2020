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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {

  // TODO make LazyTalonFX - see Jon
  private final WPI_TalonFX leftMaster;
  private final WPI_TalonFX leftSlave;
  private final WPI_TalonFX rightMaster;
  private final WPI_TalonFX rightSlave;

  private final AHRS ahrs;

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
    robotDrive.setSafetyEnabled(false);
    // TODO invert right side of DifferentialDrive

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.configFactoryDefault();
    leftMaster.setNeutralMode(NeutralMode.Coast);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);

    rightMaster.configFactoryDefault();
    rightMaster.setNeutralMode(NeutralMode.Coast);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
  }

  public void tankDrive (final double left, final double right){
    System.out.println("aimbot-output: " + left + ", " + right);
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
