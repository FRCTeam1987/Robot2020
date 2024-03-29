/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RunHopperReverse extends CommandBase {

  private final Hopper m_hopper;

  public RunHopperReverse(final Hopper hopper) {
    m_hopper = hopper;
    addRequirements(m_hopper);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hopper.reverse();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean isInterrupted) {
    m_hopper.stop();
  }
}
