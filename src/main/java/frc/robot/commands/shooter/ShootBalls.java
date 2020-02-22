/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DigitalDebouncer;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

public class ShootBalls extends CommandBase {

  private Shooter m_shooter;
  private Elevator m_elevator;
  private double m_rpm;
  private DigitalDebouncer rpmDebouncer;
  private boolean isShooting;


  public ShootBalls(Shooter shooter, Elevator elevator, double rpm) {
    addRequirements(m_shooter);
    addRequirements(m_elevator);
    m_shooter = shooter;
    m_elevator = elevator;
    m_rpm = rpm;
    rpmDebouncer = new DigitalDebouncer(0.25);
    isShooting = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setRPM(m_rpm); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    rpmDebouncer.periodic(m_shooter.isInRPMTolerance());
    if(!rpmDebouncer.get()){
      isShooting = true;
      m_elevator.setPercent(0);
    }else if(isShooting ){
      isShooting = false;
      m_elevator.decrementNumofBallsInLift();
    }else if (!isShooting){
      m_elevator.topRun();
      m_elevator.bottomRun();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setPercent(0);
    m_elevator.setPercent(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_elevator.getNumOfBallsInLift() == 0;
  }
}
