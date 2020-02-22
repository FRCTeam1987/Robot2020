package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Collector;


public class SetCollectorPercent extends InstantCommand {
  
  private final Collector m_collector;
  private final double m_percent;

  public SetCollectorPercent(Collector collector, double percent) {
  
    m_collector = collector;
    m_percent = percent;
    addRequirements(m_collector);
  }


  @Override
  public void initialize() {
    m_collector.setNeoPercent(m_percent);
  }
}
