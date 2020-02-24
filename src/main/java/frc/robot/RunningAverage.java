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
public class RunningAverage {
    private double currentAverage;
    private boolean hasBeenUpdated;

    public RunningAverage(){
        currentAverage = 0;
        hasBeenUpdated = false;
    }

    public double getRunningAverage(){
        return currentAverage;
    }

    public boolean getHasBeenUpdated(){
        return hasBeenUpdated;
    }

    public void add(double value){
        currentAverage = (value + currentAverage) / 2;
        if(!hasBeenUpdated){
            hasBeenUpdated = true;
        }
    }

    public void reset(){
        currentAverage = 0;
        hasBeenUpdated = false;
    }
}
