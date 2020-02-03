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

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

  private final WPI_TalonFX leftMaster;
  private final WPI_TalonFX leftSlave;
  private final WPI_TalonFX rightMaster;
  private final WPI_TalonFX rightSlave;

  private final DifferentialDrive robotDrive;

  // private static FalconDrive instance = null;
  /**
   * Creates a new Drive.
   */
  public Drive() {
    leftMaster = new WPI_TalonFX(3);
    leftSlave = new WPI_TalonFX(4);
    rightMaster = new WPI_TalonFX(5);
    rightSlave = new WPI_TalonFX(6);
    robotDrive = new DifferentialDrive(leftMaster, rightMaster);
    robotDrive.setSafetyEnabled(false);

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.configFactoryDefault();
    leftMaster.setNeutralMode(NeutralMode.Coast);

    rightMaster.configFactoryDefault();
    rightMaster.setNeutralMode(NeutralMode.Coast);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
  }

  public void xBoxDrive (XboxController xBox){
    double move = xBox.getTriggerAxis(Hand.kRight) - xBox.getTriggerAxis(Hand.kLeft);
    double rotate = -xBox.getX(Hand.kLeft); //invert turning

    robotDrive.arcadeDrive(move, rotate, true);
  }

  public void tankDrive (final double left, final double right){
    robotDrive.tankDrive(left, right);
  }

  public void setPercent(double percent) {
    rightMaster.set(ControlMode.PercentOutput, percent);
    leftMaster.set(ControlMode.PercentOutput, percent);
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
