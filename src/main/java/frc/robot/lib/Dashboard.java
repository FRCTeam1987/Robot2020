/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Dashboard {
  private ShuffleboardTab m_driverTab;
  private ShuffleboardTab m_troubleShooting;
  private Map<String, NetworkTableEntry> m_widgets;

  public Dashboard(){
    m_driverTab = Shuffleboard.getTab("Driver View");
    m_troubleShooting = Shuffleboard.getTab("Trouble Shooting");
    m_widgets = new HashMap<String, NetworkTableEntry>();
  }

  
}
