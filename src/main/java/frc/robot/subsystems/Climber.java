/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private final Solenoid m_retainingPin;
  private final WPI_VictorSPX m_winchMaster;
  private final WPI_VictorSPX m_winchSlave;
  private double m_lastSetPercent;


  /**
   * Creates a new Climber.
   */
  public Climber(final Solenoid retainingPin, final WPI_VictorSPX winchMaster, final WPI_VictorSPX winchSlave) {
    m_retainingPin = retainingPin;
    m_winchMaster = winchMaster;
    m_winchSlave = winchSlave;
    m_lastSetPercent = 1;
    m_winchMaster.configFactoryDefault();
    m_winchSlave.configFactoryDefault();
    m_winchSlave.follow(m_winchMaster);
    stop();
    pinInsert();
  }

  public void pinInsert() {
    m_retainingPin.set(false);
  }

  public void pinPull() {
    m_retainingPin.set(true);
  }

  private void setWinch(final double percent) {
    if (m_lastSetPercent == percent) {
      return;
    }
    m_winchMaster.set(percent);
    m_lastSetPercent = percent;
  }

  public void spoolUp() {
    setWinch(0.25);
  }

  public void stop() {
    setWinch(0);
  }

  public void unspool() {
    setWinch(-0.25);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
