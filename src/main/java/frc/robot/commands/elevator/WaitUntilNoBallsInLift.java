/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class WaitUntilNoBallsInLift extends CommandBase {

  private final Elevator m_elevator;

  /**
   * Creates a new WaitUntilNoBallsInLift.
   */
  public WaitUntilNoBallsInLift(final Elevator elevator) {
    m_elevator = elevator;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_elevator.getNumOfBallsInLift() <= 0;
  }
}
