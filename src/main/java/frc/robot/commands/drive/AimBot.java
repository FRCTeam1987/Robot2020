/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DigitalDebouncer;
import frc.robot.RunningAverage;
import frc.robot.Util;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;

public class AimBot extends CommandBase {

  private static final double aimAssistMinMove = 0.3;

  private final Shooter m_shooter;
  private final Drive m_drive;
  private final boolean m_shouldFinish;
  private final DigitalDebouncer m_isOnTarget;

  /**
   * Creates a new AimBot.
   */
  public AimBot(final Shooter shooter, final Drive drive, final boolean shouldFinish) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shouldFinish = shouldFinish;
    m_shooter = shooter;
    m_drive = drive;
    m_isOnTarget = new DigitalDebouncer(.25);

    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.turnOnLEDs();
    m_isOnTarget.periodic(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_shooter.canSeeTarget()) {
      m_drive.tankDrive(0, 0);
      return;
    }
    double angleError = m_shooter.getAngleError();
    if (angleError == 100) {
      return;
    }
    m_isOnTarget.periodic(m_shooter.isOnTarget());
    if (m_shooter.isOnTarget()) {
      m_drive.tankDrive(0, 0);
      return;
    }
    final double aimAssist = Math.copySign(Math.max(Math.abs(angleError) / 25.0 * 0.7, aimAssistMinMove), -angleError);
    m_drive.tankDrive(aimAssist, -aimAssist);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.tankDrive(0, 0);
    // m_shooter.turnOffLEDs();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_shouldFinish == false) {
      return false;
    }
    return m_isOnTarget.get() && Util.isWithinTolerance(m_shooter.getAngleError(), 0, 1);
  }
}
