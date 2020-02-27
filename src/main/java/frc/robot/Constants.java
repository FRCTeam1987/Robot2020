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
import frc.robot.lib.InterpolatingDouble;
import frc.robot.lib.InterpolatingTreeMap;

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
    public final static double shooterRPMTolerance = 25.0;

    public final static double ty1 = -6.41;
    public final static double ty2 = -5.85;
    public final static double ty3 = -5.02;
    public final static double ty4 = -4.68;
    public final static double ty5 = -4.4;
    public final static double ty6 = -3.33;
    public final static double ty7 = -3.04;
    public final static double ty8 = -1.94;
    public final static double ty9 = -0.98;
    public final static double ty10 = -0.83;
    public final static double ty11 = 0.78;
    public final static double ty12 = 1.57;
    public final static double ty13 = 3.21;
    public final static double ty14 = 5.19;
    public final static double ty15 = 8.08;
    public final static double ty16 = 12.55;
    public final static double ty17 = 16.74;
    public final static double ty18 = 18.88;

    public final static double rpm1 = 4100;
    public final static double rpm2 = 3850;
    public final static double rpm3 = 3700;
    public final static double rpm4 = 3650;
    public final static double rpm5 = 3575;
    public final static double rpm6 = 3550;
    public final static double rpm7 = 3525;
    public final static double rpm8 = 3420;
    public final static double rpm9 = 3325;
    public final static double rpm10 = 3325;
    public final static double rpm11 = 3200;
    public final static double rpm12 = 3200;
    public final static double rpm13 = 3100;
    public final static double rpm14 = 3040;
    public final static double rpm15 = 3040;
    public final static double rpm16 = 3040;
    public final static double rpm17 = 3035;
    public final static double rpm18 = 3025;

    public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kDistanceToShooterSpeed = new InterpolatingTreeMap<>();
    static {
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty1), new InterpolatingDouble(rpm1));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty2), new InterpolatingDouble(rpm2));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty3), new InterpolatingDouble(rpm3));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty4), new InterpolatingDouble(rpm4));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty5), new InterpolatingDouble(rpm5));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty6), new InterpolatingDouble(rpm6));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty7), new InterpolatingDouble(rpm7));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty8), new InterpolatingDouble(rpm8));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty9), new InterpolatingDouble(rpm9));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty10), new InterpolatingDouble(rpm10));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty11), new InterpolatingDouble(rpm11));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty12), new InterpolatingDouble(rpm12));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty13), new InterpolatingDouble(rpm13));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty14), new InterpolatingDouble(rpm14));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty15), new InterpolatingDouble(rpm15));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty16), new InterpolatingDouble(rpm16));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty17), new InterpolatingDouble(rpm17));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(ty18), new InterpolatingDouble(rpm18));
    }

    public static final double talonFXTicksPerRevolution = 2048.0;

    public static class Climber {
        public static class Actuators {
            public static class Motors {
                public static final int winchMaster = 1;  // TODO update
                public static final int winchSlave = 2;   // TODO update
            }
            public static class Solenoids {
                public static final int releasePinID = 4;
            }
        }
    }

    public static class Drive {
        public static class Controls {
            public static final double autoMaxVoltage = 10;
            public static final double aVoltSecondsSquaredPerMeter = 0.313;
            public static final double maxAccelerationMetersPerSecondSquared = 1.5;
            public static final double maxSpeedMetersPerSecond = 1.5;
            public static final double pDriveVel = 0.44;
            public static final double postEncoderGearing = 9.091;
            public static final double sVolts = 0.399;
            public static final double ticksPerRevolution = talonFXTicksPerRevolution * postEncoderGearing;
            public static final double trackWidth = 0.7021;
            public static final double vVoltSecondsPerMeter = 1.99;
            public static final double wheelDiameter = 0.1524;
            public static final double wheelCircumference = wheelDiameter * Math.PI;
        }
    }

    public static class Elevator {
        public static final int maxNumberOfBallsWhileDown = 1;
        public static final int maxNumberOfBallsWhileUp = 3;
        public static final int startingBallCount = 3;
    }

    public static class PDPMap {
        public static final int elevatorBottomMotor = 4;
        public static final int elevatorTopMotor = 5;
        public static final int hopperMotor = 11;
    }
}
