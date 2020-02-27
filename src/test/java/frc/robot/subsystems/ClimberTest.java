/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.junit.*;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class ClimberTest {

  private Solenoid mockedRetainingPin;
  private WPI_VictorSPX mockedWinchMaster;
  private WPI_VictorSPX mockedWinchSlave;

  @Before
	public void before() {
    mockedRetainingPin = mock(Solenoid.class);
    mockedWinchMaster = mock(WPI_VictorSPX.class);
    mockedWinchSlave = mock(WPI_VictorSPX.class);
	}

  @Test
  public void constructorTest() {
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedRetainingPin, times(1)).set(false);
    verify(mockedWinchMaster, times(1)).set(0);
    verify(mockedWinchMaster, times(1)).configFactoryDefault();
    verify(mockedWinchSlave, times(1)).configFactoryDefault();
    verify(mockedWinchSlave, times(1)).follow(mockedWinchMaster);
  }

  @Test
  public void pinInsertTest() {
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedRetainingPin, times(1)).set(false);
    climber.pinInsert();
    verify(mockedRetainingPin, times(2)).set(false);
  }

  @Test
  public void pinPullTest() {
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedRetainingPin, times(0)).set(true);
    climber.pinPull();
    verify(mockedRetainingPin, times(1)).set(true);
  }

  @Test
  public void spoolUpTest() {
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedWinchMaster, times(1)).set(0);
    climber.spoolUp();
    verify(mockedWinchMaster, times(1)).set(-1.0);
  }

  @Test
  public void stopTest() { // TODO
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedWinchMaster, times(1)).set(0);
    climber.spoolUp();
    verify(mockedWinchMaster, times(1)).set(0);
    climber.stop();
    verify(mockedWinchMaster, times(2)).set(0);
  }

  @Test
  public void unspoolTest() { // TODO 
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedWinchMaster, times(1)).set(0);
    climber.spoolUp();
    verify(mockedWinchMaster, times(1)).set(0);
    climber.stop();
    verify(mockedWinchMaster, times(2)).set(0);
  }

  @Test
  public void lazySetTest() {
    Climber climber = new Climber(
      mockedRetainingPin,
      mockedWinchMaster,
      mockedWinchSlave
    );
    verify(mockedWinchMaster, times(1)).set(0);
    climber.stop();
    verify(mockedWinchMaster, times(1)).set(0);
    climber.spoolUp();
    verify(mockedWinchMaster, times(1)).set(-1.0);
    climber.stop();
    verify(mockedWinchMaster, times(2)).set(0);
  }
}
