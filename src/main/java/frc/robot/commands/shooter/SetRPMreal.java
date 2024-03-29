/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SetRPMreal extends CommandBase {
  private Shooter m_shooter;
  private double m_rpm;

  public SetRPMreal(Shooter shooter) {
    m_shooter = shooter;
    m_rpm = SmartDashboard.getNumber("Shooter RPM", 4000);
    addRequirements(m_shooter);
  }

  public SetRPMreal(Shooter shooter, double rpm) {
    m_shooter = shooter;
    m_rpm = rpm;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setRPM(m_rpm);
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
    return m_shooter.isInRPMTolerance();
  }
}
