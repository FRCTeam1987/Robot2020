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
public class SpoolUpTest {

  @Test
  public void spoolUpExecuteTest() {
    Climber climber = mock(Climber.class);
    SpoolUp spoolUp = new SpoolUp(climber);
    verify(climber, times(0)).spoolUp();
    spoolUp.execute();
    verify(climber, times(1)).spoolUp();
  }

  @Test
  public void spoolUpEndTest() {
    Climber climber = mock(Climber.class);
    SpoolUp spoolUp = new SpoolUp(climber);
    verify(climber, times(0)).stop();
    spoolUp.end(false);
    verify(climber, times(1)).stop();
  }

  @Test
  public void spoolUpEndInterruptedTest() {
    Climber climber = mock(Climber.class);
    SpoolUp spoolUp = new SpoolUp(climber);
    verify(climber, times(0)).stop();
    spoolUp.end(true);
    verify(climber, times(1)).stop();
  }

}
