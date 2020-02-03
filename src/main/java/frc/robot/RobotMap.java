/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class RobotMap {
    
    //Object Dimensions/Specs and Gearbox Reductions
    public static double wheelRadius = 3.0;
    public static double wheelDiameter = 6.0; 
    public static double wheelCircumference = 2 * Math.PI * wheelRadius;
    public static double shooterReduction = .66666666666666666666666666666666667;
    public static double limelightAngle = 15.0; //total vision range is 49 degrees (24.5 degrees up and down from center)
    public static double limelightHeight = 37.0; //limelight can see reliably up to about 40ft
    public static double outerPortHeight = 97.0; // should be 98.25; //8' 2.25" from center

    // Motor IDs
    public static int leftMasterID = 3; 
    public static int leftSlaveID = 4;
    public static int rightMasterID = 5;
    public static int rightSlaveID = 6;
    public static int neoID = 8;

    //Velocities and PIDs
    public static double maxHighGearVelocity = 13;

    //Info and Tools
    public static int drivePIDIDX = 0;
    public static int defaultTimeout = 10;

    public static double ticksPerRotation = 2048; //for falcons
    public static double kEncoderDistancePerPulse = wheelCircumference / ticksPerRotation;

    public static boolean kGyroReversed = false;

    //Drivers
    public static int driverID = 0;

    //Driver Buttons
    public static int collectB = 3; // x

    //pneumatics
    public static int collectorOut = 0;
    public static int collectorIn = 1;
}
