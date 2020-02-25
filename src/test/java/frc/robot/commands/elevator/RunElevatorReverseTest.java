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
public class RunElevatorReverseTest {

  @Test
  public void executeTest() {
    Elevator elevator = mock(Elevator.class);
    RunElevatorReverse runElevatorReverse = new RunElevatorReverse(elevator);
    verify(elevator, times(0)).reverse();
    runElevatorReverse.execute();
    verify(elevator, times(1)).reverse();
  }

  @Test
  public void endTest() {
    Elevator elevator = mock(Elevator.class);
    RunElevatorReverse runElevatorReverse = new RunElevatorReverse(elevator);
    verify(elevator, times(0)).stop();
    runElevatorReverse.end(false);
    verify(elevator, times(1)).stop();
  }

  @Test
  public void endInterruptedTest() {
    Elevator elevator = mock(Elevator.class);
    RunElevatorReverse runElevatorReverse = new RunElevatorReverse(elevator);
    verify(elevator, times(0)).stop();
    runElevatorReverse.end(true);
    verify(elevator, times(1)).stop();
  }

}
