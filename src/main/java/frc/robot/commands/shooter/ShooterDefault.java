/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

public class ShooterDefault extends CommandBase {
  
  private Shooter m_shooter;
  private Elevator m_elevator;

  public ShooterDefault(final Shooter shooter, final Elevator elevator) {
    m_shooter = shooter;
    m_elevator = elevator;
    addRequirements(m_shooter);
  }

  @Override
  public void initialize() {
    m_shooter.stop();
  }

  @Override
  public void execute() {
    if (m_elevator.getNumOfBallsInLift() >= 1 && m_shooter.shouldSpinUp()) {
      // if (m_elevator.getNumOfBallsInLift() >= 1) {
      //   System.out.println("shooter-state-balls" + m_elevator.getNumOfBallsInLift());
      // }
      // if (m_shooter.shouldSpinUp()) {
      //   System.out.println("shooter-state-spin" + m_shooter.shouldSpinUp());
      // }
      m_shooter.setRPM(3500); // Dynamically set RPM based on distance... when we get to that point
    } else {
      // System.out.println("shooter-state-stop");
      m_shooter.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
