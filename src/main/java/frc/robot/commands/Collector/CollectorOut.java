/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;

public class CollectorOut extends CommandBase {
  /**
   * Creates a new CollectorOut.
   */
  private Collector m_collector;

  public CollectorOut(Collector collector) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_collector = collector;
    addRequirements(m_collector);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_collector.collectorOut();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_collector.isOut();  // TODO end when the collector is in position
    //TODO test
  }
}
