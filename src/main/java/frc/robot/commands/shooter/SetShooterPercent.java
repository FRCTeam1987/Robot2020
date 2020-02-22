package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class SetShooterPercent extends InstantCommand {
  private Shooter m_shooter;
  double m_percent;
  
  public SetShooterPercent(Shooter shooter, double percent) {
    m_shooter = shooter;
    m_percent = percent;
    addRequirements(m_shooter);
  }

  @Override
  public void initialize() {
    m_shooter.setPercent(m_percent);
  }
}
