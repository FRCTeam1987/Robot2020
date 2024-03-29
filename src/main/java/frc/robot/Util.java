/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class Util {

    public static double ctreVelocityToLinearVelocity(final double ctreVelocity, final double ticksPerRevolution, final double circumference) {
        return ctreVelocityToRps(ctreVelocity, ticksPerRevolution) * circumference;
    }

    public static double ctreVelocityToRps(final double ctreVelocity, final double ticksPerRevolution) {
        return ctreVelocity * 10.0 / ticksPerRevolution;
    }

    // TODO should return int if ticks
    public static double wheelRotationsToTicks(final double wheelRotations, final double reduction) {
        return (int)(wheelRotations * Constants.ticksPerRotation * reduction);
    }

    public static boolean isWithinTolerance(double currentValue, double targetValue, double tolerance){
        return Math.abs(currentValue - targetValue) <= tolerance;
    }

    public static double ticksToDistance(final double ticks, final double ticksPerRevolution, final double circumference) {
        return ticks / ticksPerRevolution * circumference;
    }
}
