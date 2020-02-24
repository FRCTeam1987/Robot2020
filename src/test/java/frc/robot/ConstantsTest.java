/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Add your docs here.
 */
public class ConstantsTest {

    @Test
    public void interpolateTest() {
        final double value = Constants.kDistanceToShooterSpeed.getInterpolated(new InterpolatingDouble(-6.0)).value;
        System.out.println("--------");
        System.out.println(value);
        System.out.println("--------");
        assertTrue(value > 3850 && value < 4100);
    }
}
