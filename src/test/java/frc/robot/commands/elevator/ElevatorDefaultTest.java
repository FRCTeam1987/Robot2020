/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import static org.junit.Assert.*;
import java.util.*;
import com.teambroncobots.helpers.*;
import frc.robot.subsystems.*;
import frc.robot.commands.elevator.ElevatorDefault;
import frc.robot.commands.hopper.*;

public class ElevatorDefaultTest {
    private Shooter mockedShooter;
    private Elevator mockedElevator;
    private Hopper mockedHopper;
 
    @Before
	public void before() {
        TestWithScheduler.schedulerStart();
		TestWithScheduler.schedulerClear();
		MockHardwareExtension.beforeAll();

        mockedShooter = mock(Shooter.class);
        mockedElevator = mock(Elevator.class);
        mockedHopper = mock(Hopper.class);
	}

     @Test
     public void verifyInitialize() {
        ElevatorDefault elevatorDefault = new ElevatorDefault(mockedElevator, mockedShooter);
        CommandScheduler.getInstance().schedule(elevatorDefault);

		// Verify that stop was called
		verify(mockedElevator).stop();

		// Clear the scheduler
		TestWithScheduler.schedulerClear();
     }

     @Test
     public void verifyShooterDefaultEnd() {
        ElevatorDefault elevatorDefault = new ElevatorDefault(mockedElevator, mockedShooter);
        CommandScheduler.getInstance().schedule(elevatorDefault);

        CommandScheduler.getInstance().cancel(elevatorDefault);

        verify(mockedElevator, times(2)).stop();

		// Clear the scheduler
        TestWithScheduler.schedulerClear();
     }

//-------------------------------------------------------------------------------------------

     @Test
     public void verifyInit() {
        ElevatorDefault elevatorDefault = new ElevatorDefault(mockedElevator, mockedShooter);

        when(elevatorDefault.isLoading()).thenReturn(true);

        assertFalse(elevatorDefault.getIsLoadingForTest());

        verify(mockedElevator, times(0)).stop();
        elevatorDefault.initialize();
        verify(mockedElevator, times(1)).stop();

        assertTrue(elevatorDefault.getIsLoadingForTest());
     }

     @Test
     public void verifyExecuteCanShoot() {
         
     }
     // up - load 3
     // down - load 1
     // check if the number of balls in the elevator is stored when loading is stopped before it is done
     // check the canShoot logic
     // check the default stop
     // check that the number of balls in lift actually increments when we want it to

     @Test
     public void verifyEndFalse() {
        ElevatorDefault elevatorDefault = new ElevatorDefault(mockedElevator, mockedShooter);

        verify(mockedElevator, times(0)).stop();
        elevatorDefault.end(false);
        verify(mockedElevator, times(1)).stop();
     }
}