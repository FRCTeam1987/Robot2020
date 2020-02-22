/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class Util {
    // TODO should return int if ticks
    public static double wheelRotationsToTicks(final double wheelRotations, final double reduction) {
        return (int)(wheelRotations * Constants.ticksPerRotation * reduction);
    }

    public static boolean isWithinTolerance(double currentValue, double targetValue, double tolerance){
        return Math.abs(currentValue - targetValue) <= tolerance;
    }
}
