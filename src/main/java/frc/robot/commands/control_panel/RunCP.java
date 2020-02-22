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

public class RunCP extends CommandBase {

  private ControlPanel m_cp;
  private boolean hasRunB4;
  private double m_startTime;
  private double m_wait;

  public RunCP(ControlPanel controlPanel) {
    m_cp = controlPanel;
    addRequirements(m_cp);
    m_wait = 0.5;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hasRunB4 = m_cp.getHasRunBefore();
    m_cp.deployCP();
    m_startTime = Timer.getFPGATimestamp();
    if(hasRunB4){
      m_cp.spinToGameColor();
    } else{
      m_cp.spin3Times();
      m_cp.toggleHasRunBefore();
    }
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
    return (Timer.getFPGATimestamp() - m_startTime >= m_wait) && (hasRunB4 ? m_cp.shouldSpinToGameColor() : m_cp.shouldSpinCP3Times());
  }
}