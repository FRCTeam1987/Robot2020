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
public class UnspoolTest {

  @Test
  public void unspoolExecuteTest() {
    Climber climber = mock(Climber.class);
    Unspool unspool = new Unspool(climber);
    verify(climber, times(0)).unspool();
    unspool.execute();
    verify(climber, times(1)).unspool();
  }
    
  @Test
  public void unspoolEndTest() {
    Climber climber = mock(Climber.class);
    Unspool unspool = new Unspool(climber);
    verify(climber, times(0)).stop();
    unspool.end(false);
    verify(climber, times(1)).stop();
  }

  @Test
  public void unspoolEndInterruptedTest() {
    Climber climber = mock(Climber.class);
    Unspool unspool = new Unspool(climber);
    verify(climber, times(0)).stop();
    unspool.end(true);
    verify(climber, times(1)).stop();
  }

}
