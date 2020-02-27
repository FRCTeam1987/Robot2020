/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.teambroncobots.helpers.MockHardwareExtension;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Add your docs here.
 */
public class CollectorTest {

  // TODO Rev robotics apparently doesn't support x86-64 out of the box.  It'll take more effort to make testing work for this.
  private CANSparkMax mockMotor;
  private DoubleSolenoid mockPiston;

  @Before
  public void before() {
    mockMotor = mock(CANSparkMax.class);
    mockPiston = mock(DoubleSolenoid.class);
    MockHardwareExtension.beforeAll();
  }

  // @Test
  // public void constructorTest() {
  //   when(mockMotor.restoreFactoryDefaults()).thenReturn(CANError.kOk);
  //   when(mockMotor.setInverted(anyBoolean())).thenReturn(CANError.kOk);
  //   Collector collector = new Collector(mockMotor, mockPiston);
  //   verify(mockMotor, times(1)).restoreFactoryDefaults();
  //   verify(mockMotor, times(1)).setInverted(true);
  // }
}
