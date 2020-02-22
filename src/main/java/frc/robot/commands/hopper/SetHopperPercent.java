package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hopper;

public class SetHopperPercent extends InstantCommand {
  private final Hopper m_hopper;
  private final double m_percent;

  public SetHopperPercent(Hopper hopper, double percent) {
    m_hopper = hopper;
    m_percent = percent;
    addRequirements(m_hopper);
  }

  @Override
  public void initialize() {
  m_hopper.setPercent(m_percent);
  }
}
