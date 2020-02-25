/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RunElevatorForward extends CommandBase {

  private final Elevator m_elevator;

  public RunElevatorForward(final Elevator elevator) {
    m_elevator = elevator;
    addRequirements(m_elevator);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_elevator.forward();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean isInterrupted) {
    m_elevator.stop();
  }
}
