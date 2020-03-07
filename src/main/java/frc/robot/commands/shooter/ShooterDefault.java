/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.lib.DigitalDebouncer;
import frc.robot.lib.InterpolatingDouble;
import frc.robot.Util;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

public class ShooterDefault extends CommandBase {
  
  private Shooter m_shooter;
  private Elevator m_elevator;
  private DigitalDebouncer rpmDebouncer;
  private boolean isShooting;
  private boolean topWasTripped;
  private boolean topIsTripped;
  private double m_rpm;


  public ShooterDefault(final Shooter shooter, final Elevator elevator) {
    m_shooter = shooter;
    m_elevator = elevator;
    addRequirements(m_shooter);

    rpmDebouncer = new DigitalDebouncer(.1);
    isShooting = false;
    topIsTripped = m_elevator.isBallAtTop();
    topWasTripped = false;
  }

  @Override
  public void initialize() {
    m_shooter.stop();
    topWasTripped = false;
  }

  @Override
  public void execute() {
    rpmDebouncer.periodic(m_shooter.isInRPMTolerance());
    if (m_elevator.getNumOfBallsInLift() > 0 && m_elevator.getNumOfBallsInLift() < 4 && m_shooter.shouldSpinUp()) {

      if (topWasTripped && !m_elevator.isBallAtTop()) {
        m_elevator.decrementNumofBallsInLift();
      }

      // if(!rpmDebouncer.get() ){
      //   isShooting = true;
      // }else if(isShooting ){
      //   isShooting = false;
      //   if (topWasTripped && !topIsTripped){
      //     m_elevator.decrementNumofBallsInLift();
      //     topWasTripped = false;
      //   }else {
      //     topWasTripped = topIsTripped;
      //   }
      // }
      if(m_shooter.getIsFarShot()){
        m_shooter.setRPM(Constants.kDistanceToShooterSpeedFar.getInterpolated(new InterpolatingDouble(m_shooter.getTY())).value);
      } else{
        m_shooter.setRPM(Constants.kDistanceToShooterSpeedClose.getInterpolated(new InterpolatingDouble(m_shooter.getTY())).value);
      }
      // m_shooter.setRPM(3225);
      SmartDashboard.putNumber("default shooter rpm", m_rpm);
    } else {
      m_shooter.stop();
    }
    topWasTripped = m_elevator.isBallAtTop();
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
