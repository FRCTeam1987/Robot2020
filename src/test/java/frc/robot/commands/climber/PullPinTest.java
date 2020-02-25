/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import static org.mockito.Mockito.*;
import org.junit.*;

import frc.robot.subsystems.Climber;

/**
 * Add your docs here.
 */
public class PullPinTest {

  @Test
  public void pullPinTest() {
    Climber climber = mock(Climber.class);
    PullPin pullPin = new PullPin(climber);
    verify(climber, times(0)).pinPull();
    pullPin.initialize();
    verify(climber, times(1)).pinPull();
  }

}
