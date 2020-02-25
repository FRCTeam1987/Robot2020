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
public class RunElevatorForwardTest {

  @Test
  public void executeTest() {
    Elevator elevator = mock(Elevator.class);
    RunElevatorForward runElevatorForward = new RunElevatorForward(elevator);
    verify(elevator, times(0)).forward();
    runElevatorForward.execute();
    verify(elevator, times(1)).forward();
  }

  @Test
  public void endTest() {
    Elevator elevator = mock(Elevator.class);
    RunElevatorForward runElevatorForward = new RunElevatorForward(elevator);
    verify(elevator, times(0)).stop();
    runElevatorForward.end(false);
    verify(elevator, times(1)).stop();
  }

  @Test
  public void endInterruptedTest() {
    Elevator elevator = mock(Elevator.class);
    RunElevatorForward runElevatorForward = new RunElevatorForward(elevator);
    verify(elevator, times(0)).stop();
    runElevatorForward.end(true);
    verify(elevator, times(1)).stop();
  }

}
