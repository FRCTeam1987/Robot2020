/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Rumble extends CommandBase {
  
  private XboxController m_controller;
  public Rumble(XboxController controller) {
    m_controller = controller;
  }

  @Override
  public void initialize() {
    m_controller.setRumble(RumbleType.kLeftRumble, 1);
    m_controller.setRumble(RumbleType.kRightRumble, 1); 
  }

  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {
    m_controller.setRumble(RumbleType.kLeftRumble, 0);
    m_controller.setRumble(RumbleType.kRightRumble, 0); 
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
