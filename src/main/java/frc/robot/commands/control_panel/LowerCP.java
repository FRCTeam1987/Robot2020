/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.control_panel;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

public class LowerCP extends CommandBase {

  private ControlPanel m_cp;
  private double m_startTime;
  private double m_wait;

  public LowerCP(ControlPanel controlPanel) {
    m_cp = controlPanel;
    m_startTime = 0;
    m_wait = 0.25;
    addRequirements(m_cp);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_cp.retractCP();
    m_startTime = Timer.getFPGATimestamp();
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
    return Timer.getFPGATimestamp() - m_startTime >= m_wait;
  }
}
