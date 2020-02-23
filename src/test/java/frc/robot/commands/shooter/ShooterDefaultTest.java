/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import static org.junit.Assert.*;
import java.util.*;
import com.teambroncobots.helpers.*;
import frc.robot.subsystems.*;
import frc.robot.commands.shooter.*;

public class ShooterDefaultTest {
    private Shooter mockedShooter;
    private Elevator mockedElevator;
 
    @Before
	public void before() {
        TestWithScheduler.schedulerStart();
		TestWithScheduler.schedulerClear();
		MockHardwareExtension.beforeAll();

        mockedShooter = mock(Shooter.class);
        mockedElevator = mock(Elevator.class);
	}

     @Test
     public void verifyInitialize() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);
        CommandScheduler.getInstance().schedule(shooterDefault);

		// Verify that stop was called
		verify(mockedShooter).stop();

		// Clear the scheduler
		TestWithScheduler.schedulerClear();
     }

     @Test
     public void verifyNoBalls() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);
        CommandScheduler.getInstance().schedule(shooterDefault);

		// Verify that stop is called when there are no balls
        when(mockedElevator.getNumOfBallsInLift()).thenReturn(0);

        CommandScheduler.getInstance().run();

        verify(mockedShooter, atLeast(2)).stop();

		// Clear the scheduler
        TestWithScheduler.schedulerClear();
     }

     @Test
     public void verifyShooterShouldSpinUp() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);
        CommandScheduler.getInstance().schedule(shooterDefault);

        // Verify that it should not spin
        when(mockedElevator.getNumOfBallsInLift()).thenReturn(1);
        when(mockedShooter.shouldSpinUp()).thenReturn(false);

        CommandScheduler.getInstance().run();

        verify(mockedShooter, atLeast(2)).stop();

		// Clear the scheduler
        TestWithScheduler.schedulerClear();
     }

     @Test
     public void verifyShooterSetRPM() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);
        CommandScheduler.getInstance().schedule(shooterDefault);

        // Verify that the shooter spins
        when(mockedElevator.getNumOfBallsInLift()).thenReturn(1);
        when(mockedShooter.shouldSpinUp()).thenReturn(true);

        CommandScheduler.getInstance().run();

        verify(mockedShooter, times(1)).stop();
        verify(mockedShooter).setRPM(anyDouble());

		// Clear the scheduler
        TestWithScheduler.schedulerClear();
     }

     @Test
     public void verifyShooterDefaultEnd() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);
        CommandScheduler.getInstance().schedule(shooterDefault);

        CommandScheduler.getInstance().cancel(shooterDefault);

        verify(mockedShooter, times(2)).stop();

		// Clear the scheduler
        TestWithScheduler.schedulerClear();
     }

//-------------------------------------------------------------------------------------------

     @Test
     public void verifyInit() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.initialize();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteZeroBallsAndNoSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(0);
        when(mockedShooter.shouldSpinUp()).thenReturn(false);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteOneBallAndNoSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(1);
        when(mockedShooter.shouldSpinUp()).thenReturn(false);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteTwoBallsAndNoSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(2);
        when(mockedShooter.shouldSpinUp()).thenReturn(false);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteThreeBallsAndNoSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(3);
        when(mockedShooter.shouldSpinUp()).thenReturn(false);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteFourBallsAndNoSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(4);
        when(mockedShooter.shouldSpinUp()).thenReturn(false);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteZeroBallsAndSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(0);
        when(mockedShooter.shouldSpinUp()).thenReturn(true);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyExecuteOneBallAndSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(1);
        when(mockedShooter.shouldSpinUp()).thenReturn(true);

        verify(mockedShooter, times(0)).setRPM(anyDouble());
        shooterDefault.execute();
        verify(mockedShooter, times(1)).setRPM(anyDouble());
     }

     @Test
     public void verifyExecuteTwoBallsAndSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(2);
        when(mockedShooter.shouldSpinUp()).thenReturn(true);

        verify(mockedShooter, times(0)).setRPM(anyDouble());
        shooterDefault.execute();
        verify(mockedShooter, times(1)).setRPM(anyDouble());
     }

     @Test
     public void verifyExecuteThreeBallsAndSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(3);
        when(mockedShooter.shouldSpinUp()).thenReturn(true);

        verify(mockedShooter, times(0)).setRPM(anyDouble());
        shooterDefault.execute();
        verify(mockedShooter, times(1)).setRPM(anyDouble());
     }

     @Test
     public void verifyExecuteFourBallsAndSpin() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        when(mockedElevator.getNumOfBallsInLift()).thenReturn(4);
        when(mockedShooter.shouldSpinUp()).thenReturn(true);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.execute();
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyEndTrue() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.end(true);
        verify(mockedShooter, times(1)).stop();
     }

     @Test
     public void verifyEndFalse() {
        ShooterDefault shooterDefault = new ShooterDefault(mockedShooter, mockedElevator);

        verify(mockedShooter, times(0)).stop();
        shooterDefault.end(false);
        verify(mockedShooter, times(1)).stop();
     }
} 