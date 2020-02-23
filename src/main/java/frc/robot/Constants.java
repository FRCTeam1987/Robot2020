/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */

public final class Constants {
    // TODO organize these by using static sub-classes

    //Object Dimensions/Specs and Gearbox Reductions
    public static double wheelRadius = 3.0;
    public static double wheelDiameter = 6.0; 
    public static double wheelCircumference = 2 * Math.PI * wheelRadius;
    public static double driveReduction = 9.09;
    public static double cpReduction = .0625;
    public static double shooterReduction = 1.0;
    public static double limelightAngle = 15.0; //total vision range is 49 degrees (24.5 degrees up and down from center)
    public static double limelightHeight = 37.0; //limelight can see reliably up to about 40ft
    public static double outerPortHeight = 97.0; // should be 98.25; //8' 2.25" from center

    // Motor IDs
    public static int leftMasterID = 6;
    public static int leftSlaveID = 9;
    public static int rightMasterID = 3;
    public static int rightSlaveID = 10;

    public static int neoID = 1;//TODO rename to Collector front and make a collector back
    public static int collectorBack = 2;

    public static int shooterMasterID = 8;
    public static int shooterSlaveID = 7;

    public static int hopperMotorID = 6;

    public static int elevatorTopMotorsID = 7;
    public static int elevatorBottomMotorsID = 5;

    public static int climberMasterID = 1;
    public static int climberSlaveID = 2;

    public static int controlPanelMotorID = 4;



    //Velocities and PIDs
    public static double maxHighGearVelocity = 13;

    //Info and Tools
    public static int primaryPIDIDX = 0;
    public static int AuxPIDIDX = 1;
    public static int defaultTimeout = 10;

    public static double ticksPerRotation = 2048; //for falcons
    public static double kEncoderDistancePerPulse = wheelCircumference / ticksPerRotation;
    public static double milliPerMin = 600;

    public static boolean kGyroReversed = false;


    //Drivers
    public static int driverID = 0;
    public static int coDriverID = 1;

    //Driver Buttons
    public static int collectB = 3; // x
    // public static int shootB = ; //

    //pneumatics
    public static int collectorOut = 3;
    public static int collectorIn = 2;

    public static int elevatorLiftIn = 1;
    public static int elevatorLiftOut = 0;


    public static int climberDeploy = 4;

    public static int controlPanelDeploy = 5;
    public static int controlPanelRetract = 6;

    //sensors
    public final static I2C.Port i2cPort = I2C.Port.kOnboard;
    public static int pdpID = 0;

    //Control Panel
        //colors
        public final static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        public final static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        public final static Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        public final static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

        // Expected color transitions when spinning clockwise
        public final static String fromBlue = "Y";
        public final static String fromGreen = "B";
        public final static String fromRed = "G";
        public final static String fromYellow = "R";

        // Expected color transitions when spinning counter-clockwise
        // public final static String fromBlue = "G";
        // public final static String fromGreen = "R";
        // public final static String fromRed = "Y";
        // public final static String fromYellow = "B";

        // When the field gives us a target color of ..., we look for ...
        public final static String gameColorTargetYellow = "G";
        public final static String gameColorTargetBlue = "R";
        public final static String gameColorTargetGreen = "Y";
        public final static String gameColorTargetRed = "B";

    // Shooter
    public final static double shooterAngleTolerance = 2.0;
    public final static double shooterAngleErrorToSpinUp = 5.0;
    public final static double shooterRPMTolerance = 15.0;

    public static class PDPMap {
        public static final int elevatorBottomMotor = 4;
        public static final int elevatorTopMotor = 5;
        public static final int hopperMotor = 11;
    }

    public static class Elevator {
        public static final int maxNumberOfBallsWhileDown = 1;
        public static final int maxNumberOfBallsWhileUp = 3;
    }
}
