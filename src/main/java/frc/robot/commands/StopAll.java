/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.CollectionReferringAccumulator;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;

public class StopAll extends CommandBase {
  
  private final Hopper m_hopper;
  private final Elevator m_elevator;
  private final Collector m_collector;
  // private final Compressor m_compressor;

  
  public StopAll(final Hopper hopper, final Elevator elevator, final Collector collector) { // final Compressor compressor
    m_hopper = hopper;
    m_elevator = elevator;
    m_collector = collector;
    // m_compressor = compressor;
    addRequirements(m_hopper);
    addRequirements(m_hopper);
    addRequirements(m_hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_collector.setNeoPercent(0.0);
    m_hopper.stop();
    m_elevator.stop();
    // m_compressor.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
