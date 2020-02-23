/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hopper;

import static org.mockito.Mockito.*;
import com.teambroncobots.helpers.MockHardwareExtension;
import org.junit.*;
import org.mockito.Mockito;
import static org.junit.Assert.*;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloadert.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;

// @RunWith(PowerMockRunner.class)
// @PrepareForTest(Timer.class)
public class HopperDefaultTest {
    private Hopper mockedHopper;
    private Elevator mockedElevator;

    @Before
    public void before() {
		MockHardwareExtension.beforeAll();

        mockedHopper = mock(Hopper.class);
        mockedElevator = mock(Elevator.class);
    }

    @Test
    public void verifyInit() {
        HopperDefault hopperDefault = new HopperDefault(mockedHopper, mockedElevator);

        Mockito.verify(mockedHopper, times(0)).stop();
        hopperDefault.initialize();
        Mockito.verify(mockedHopper, times(1)).stop();
     }

    //  @Test
    //  public void verifyExecuteNoJam() {
    //     PowerMockito.mockStatic(Timer.class);
    //     when(Timer.getFPGATimestamp()).thenReturn(Double.MAX_VALUE);

    //     HopperDefault hopperDefault = new HopperDefault(mockedHopper, mockedElevator);
        
    //     when(hopperDefault.getCurrentTime()).thenReturn(Double.MAX_VALUE);
    //     when(mockedHopper.getCurrent()).thenReturn(0.0);
    //     when(mockedElevator.hasMaxNumberOfBalls()).thenReturn(false);

    //     verify(mockedHopper, times(0)).forward();
    //     hopperDefault.execute();
    //     verify(mockedHopper, times(1)).forward();
    //  }

    //  @Test
    //  public void verifyExecuteNoJamMaxBalls() {
    //     HopperDefault hopperDefault = new HopperDefault(mockedHopper, mockedElevator);
        
    //     when(hopperDefault.getCurrentTime()).thenReturn(Double.MAX_VALUE);
    //     when(mockedHopper.getCurrent()).thenReturn(0.0);
    //     when(mockedElevator.hasMaxNumberOfBalls()).thenReturn(true);

    //     verify(mockedHopper, times(0)).stop();
    //     hopperDefault.execute();
    //     verify(mockedHopper, times(1)).stop();
    //  }

     @Test
     public void verifyEndFalse() {
        HopperDefault hopperDefault = new HopperDefault(mockedHopper, mockedElevator);

        Mockito.verify(mockedHopper, times(0)).stop();
        hopperDefault.end(false);
        Mockito.verify(mockedHopper, times(1)).stop();
     }
}
