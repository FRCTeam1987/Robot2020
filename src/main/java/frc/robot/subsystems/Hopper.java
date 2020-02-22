package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hopper extends SubsystemBase {

  private final PowerDistributionPanel m_pdp;
  private final WPI_VictorSPX motor;
  private double lastSet;

  public Hopper(final PowerDistributionPanel pdp) {
    m_pdp = pdp;
    motor = new WPI_VictorSPX(Constants.hopperMotorID);
    motor.setInverted(true);
    stop();
    addChild("hopper", motor);
  }

  public void forward() {
    setPercent(1);
  }

  public void reverse() {
    setPercent(-1);
  }

  public void setPercent(double percent) {
    if (lastSet == percent) {
      return;
    }
    lastSet = percent;
    motor.set(ControlMode.PercentOutput, percent);
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
