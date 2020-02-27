/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.control_panel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

public class FindColor extends CommandBase {

  private ControlPanel m_cp;

  public FindColor(ControlPanel controlPanel) {
    m_cp = controlPanel;
    addRequirements(m_cp);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_cp.deployCP();
    m_cp.spinToGameColor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_cp.setPercent(0.0);
    m_cp.retractCP();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !m_cp.isOnCP() &&  m_cp.shouldSpinToGameColor();
  }
}
