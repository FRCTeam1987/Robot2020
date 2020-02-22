/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;

public class TeleopDrive extends CommandBase {

  // TOGGLE ME FOR EXPERIMENTATION
  // true for curvature drive, false for normal drive
  private static final boolean shouldCurvatureDrive = false;
  // TOGGLE ME!!!!!!!!!

  private static final double aimAssistMinMove = 0.3;
  private XboxController m_driver;
  private Drive m_driveSubsystem;
  private final Shooter m_shooter;

  /**
   * Creates a new TeleopDrive.
   */
  public TeleopDrive(final Drive driveSubsystem, final XboxController driver, final Shooter shooter) {
    addRequirements(driveSubsystem);
    m_driveSubsystem = driveSubsystem;

    m_driver = driver;
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO fix move negative forward
    double move = (m_driver.getTriggerAxis(Hand.kRight) - m_driver.getTriggerAxis(Hand.kLeft)) * -1;
    double rotate = -m_driver.getX(Hand.kLeft); //invert turning

    // TODO make this aim assist code not hacky
    if (m_driver.getBackButton() && Math.abs(m_shooter.getAngleError()) > 1) { //1 degree is tolerance, change if need to
      double aimAssist = 0.0;
      double angleError = m_shooter.getAngleError();
      aimAssist = Math.copySign(Math.max(Math.abs(angleError) / 25.0 * 0.7, aimAssistMinMove), -angleError);
      m_driveSubsystem.arcadeDrive(move, aimAssist);
      return;
    }

    // TODO this is to try out, remove this if we don't want it
    if (shouldCurvatureDrive) {
      boolean isQuickTurn = m_driver.getStickButton(Hand.kLeft);
      m_driveSubsystem.curvatureDrive(move, rotate, isQuickTurn);
      return;
    }

    m_driveSubsystem.arcadeDrive(move, rotate);
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
