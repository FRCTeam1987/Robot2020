/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DigitalGlitchFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DigitalDebouncer;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

public class ElevatorDefault extends CommandBase {

  private final Elevator m_elevator;
  private final Shooter m_shooter;
  private DigitalDebouncer entranceDebouncer;
  private boolean m_isLoading;
  /**
   * Creates a new ElevatorDefault.
   */
  public ElevatorDefault(final Elevator elevator, final Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_elevator = elevator;
    m_shooter = shooter;
    m_isLoading = false;
    addRequirements(m_elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_elevator.stop();
    m_isLoading = isLoading(); // TODO test the auto to tele transition while loading a ball
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (isLoading()){
      m_isLoading = true;
    } else{
      if (m_isLoading){
        m_elevator.incrementNumofBallsInLift();
        SmartDashboard.putString("elevator", "increment ball: " + m_elevator.getNumOfBallsInLift());
        if(m_elevator.isBallAtTop()){
          // m_elevator.topRunDown();
          m_elevator.stop();
        }
        // System.out.println("elevator - increment ball: " + m_elevator.getNumOfBallsInLift());
        m_isLoading = false;
      }
    }
    if (m_shooter.canShoot()) {
      SmartDashboard.putString("elevator", "can shoot");
      // System.out.println("elevator - can shoot");
      m_elevator.forward();
      return;
    }
    // if (m_elevator.isBallAtTop()) {
    //   SmartDashboard.putString("elevator", "ball at top");
    //   m_elevator.topRunDown();
    //   return;
    // }
    if (m_elevator.hasMaxNumberOfBalls()) {
      SmartDashboard.putString("elevator", "has max number of balls");
      // System.out.println("elevator - has max number of balls");
      m_elevator.stop();
      return;
    }
    if (m_elevator.isBallAtEntrance()){
      SmartDashboard.putString("elevator", "is ball at entrance");
      // System.out.println("elevator - is ball at entrance");
      if (m_elevator.getNumOfBallsInLift() >= 1 ) {
        m_elevator.bottomRunSlow();
      } else {
        m_elevator.forward();
      }
      return;
    }
    SmartDashboard.putString("elevator", "default stop");
    // System.out.println("elevator - default stop");
    m_elevator.stop();
  }

  public boolean getIsLoadingForTest() {
    return m_isLoading;
  }

  public boolean isLoading(){
    return !m_elevator.hasMaxNumberOfBalls() && m_elevator.isBallAtEntrance();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_elevator.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
