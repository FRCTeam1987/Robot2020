package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hopper extends SubsystemBase {

  private final PowerDistributionPanel m_pdp;
  private final WPI_VictorSPX m_motor;
  private double m_lastSet;

  public Hopper(final PowerDistributionPanel pdp, final WPI_VictorSPX motor) {
    m_lastSet = 999;
    m_pdp = pdp;
    m_motor = motor;
    m_motor.setInverted(true);
    stop();
    addChild("hopper", m_motor);
  }

  public void forward() {
    setPercent(1);
  }

  public void reverse() {
    setPercent(-1);
  }

  public void setPercent(double percent) {
    if (m_lastSet == percent) {
      return;
    }
    m_lastSet = percent;
    m_motor.set(percent);
  }

  public void stop() {
    setPercent(0);
  }

  public double getCurrent() {
    return m_pdp.getCurrent(Constants.PDPMap.hopperMotor);
  }

  @Override
  public void periodic() {
  }
}
