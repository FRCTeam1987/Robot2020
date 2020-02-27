/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.*;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * Add your docs here.
 */
public class HopperTest {

  private PowerDistributionPanel mockPdp;
  private WPI_VictorSPX mockMotor;

  @Before
	public void before() {
    mockPdp = mock(PowerDistributionPanel.class);
    mockMotor = mock(WPI_VictorSPX.class);
	}

  @Test
  public void constructorTest() {
    Hopper hopper = new Hopper(mockPdp, mockMotor);
    verify(mockMotor, times(1)).setInverted(true);
    verify(mockMotor, times(1)).set(0);
  }

  @Test
  public void forwardTest() {
    Hopper hopper = new Hopper(mockPdp, mockMotor);
    verify(mockMotor, times(0)).set(1);
    hopper.forward();
    verify(mockMotor, times(1)).set(1);
  }

  @Test
  public void reverseTest() {
    Hopper hopper = new Hopper(mockPdp, mockMotor);
    verify(mockMotor, times(0)).set(-1);
    hopper.reverse();
    verify(mockMotor, times(1)).set(-1);
  }

  @Test
  public void setPercentTest() {
    Hopper hopper = new Hopper(mockPdp, mockMotor);
    verify(mockMotor, times(0)).set(0.5);
    hopper.setPercent(0.5);
    verify(mockMotor, times(1)).set(0.5);
    hopper.setPercent(0.5);
    verify(mockMotor, times(1)).set(0.5);
    verify(mockMotor, times(1)).set(0);
    hopper.setPercent(0);
    verify(mockMotor, times(2)).set(0);
  }

  @Test
  public void stopTest() {
    Hopper hopper = new Hopper(mockPdp, mockMotor);
    verify(mockMotor, times(1)).set(0);
    hopper.stop();
    verify(mockMotor, times(1)).set(0);
    hopper.forward();
    verify(mockMotor, times(1)).set(0);
    hopper.stop();
    verify(mockMotor, times(2)).set(0);
  }

  @Test
  public void getCurrentTest() {
    final double expectedCurrent = 15.0;
    Hopper hopper = new Hopper(mockPdp, mockMotor);
    when(mockPdp.getCurrent(Constants.PDPMap.hopperMotor)).thenReturn(expectedCurrent);
    verify(mockPdp, times(0)).getCurrent(Constants.PDPMap.hopperMotor);
    assertEquals("get current from pdp slot", expectedCurrent, hopper.getCurrent(), 0);
    verify(mockPdp, times(1)).getCurrent(Constants.PDPMap.hopperMotor);
  }
}
