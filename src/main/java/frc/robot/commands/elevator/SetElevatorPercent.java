package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Elevator;

public class SetElevatorPercent extends InstantCommand {
  private final Elevator m_elevator;
  private final double m_percent;
  
  public SetElevatorPercent(final Elevator elevator, final double percent) {
    addRequirements(elevator);
    m_elevator = elevator;
    m_percent = percent;
  }

  @Override
  public void initialize() {
    m_elevator.setPercent(m_percent);
  }
}
