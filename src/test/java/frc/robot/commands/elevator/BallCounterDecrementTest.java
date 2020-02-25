/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import static org.mockito.Mockito.*;
import org.junit.*;

import frc.robot.subsystems.Elevator;

/**
 * Add your docs here.
 */
public class BallCounterDecrementTest {

  @Test
  public void resetTest() {
    Elevator elevator = mock(Elevator.class);
    BallCounterDecrement ballCounterDecrement = new BallCounterDecrement(elevator);
    verify(elevator, times(0)).decrementNumofBallsInLift();
    ballCounterDecrement.initialize();
    verify(elevator, times(1)).decrementNumofBallsInLift();
  }

}
