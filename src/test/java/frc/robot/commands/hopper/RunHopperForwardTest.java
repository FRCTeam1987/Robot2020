/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hopper;

import static org.mockito.Mockito.*;
import org.junit.*;

import frc.robot.subsystems.Hopper;

/**
 * Add your docs here.
 */
public class RunHopperForwardTest {

    @Test
    public void executeTest() {
        Hopper hopper = mock(Hopper.class);
        RunHopperForward runHopperForward = new RunHopperForward(hopper);
        verify(hopper, times(0)).forward();
        runHopperForward.execute();
        verify(hopper, times(1)).forward();
    }

    @Test
    public void endTest() {
        Hopper hopper = mock(Hopper.class);
        RunHopperForward runHopperForward = new RunHopperForward(hopper);
        verify(hopper, times(0)).stop();
        runHopperForward.end(false);
        verify(hopper, times(1)).stop();
    }

    @Test
    public void endInterruptedTest() {
        Hopper hopper = mock(Hopper.class);
        RunHopperForward runHopperForward = new RunHopperForward(hopper);
        verify(hopper, times(0)).stop();
        runHopperForward.end(true);
        verify(hopper, times(1)).stop();
    }

}
