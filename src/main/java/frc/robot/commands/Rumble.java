/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Rumble extends CommandBase {
  
  private RobotContainer m_robotContainer;
  public Rumble(RobotContainer robotContainer) {
    m_robotContainer = robotContainer;
  }

  @Override
  public void initialize() {
    m_robotContainer.getDriver().setRumble(RumbleType.kLeftRumble, 1);
    m_robotContainer.getDriver().setRumble(RumbleType.kRightRumble, 1); 
  }

  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {
    m_robotContainer.getDriver().setRumble(RumbleType.kLeftRumble, 0);
    m_robotContainer.getDriver().setRumble(RumbleType.kRightRumble, 0); 
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
