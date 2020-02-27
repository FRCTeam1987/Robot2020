/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.DigitalDebouncer;
import frc.robot.Util;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;

public class Index extends CommandBase {

  private final static double backDriveDuration = 0.05;

  private final Elevator m_elevator;
  private final Hopper m_hopper;
  private double numberOfBallsToIndex;
  private boolean isIndexing;
  private boolean isClogged;
  private double endTimeStamp;
  private DigitalDebouncer currentDebouncer;

  /**
   * Creates a new Index.
   */
  public Index(final Elevator elevator, final Hopper hopper) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
    addRequirements(hopper);
    m_elevator = elevator;
    m_hopper = hopper;
    numberOfBallsToIndex = 0;
    currentDebouncer = new DigitalDebouncer(0.25);
    isIndexing = false;
    isClogged = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    numberOfBallsToIndex = m_elevator.isDown() ? 1 : 3;
    isIndexing = false;
    endTimeStamp = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO quick stop motors as soon as not tripped, not debounced
    currentDebouncer.periodic(Util.isWithinTolerance(m_elevator.getBottomMotorCurrent(), 0, 25)); //allows for both positive and negative values
    if (endTimeStamp != 0) {
      m_elevator.topRunDown();
      m_elevator.bottomRunDown();
      return;
    }

    if(currentDebouncer.get()){
      if (m_elevator.isBallAtEntrance()) {
        isIndexing = true;
        m_elevator.bottomRun();
        if (m_elevator.isDown() && !m_elevator.isBallAtTop()) {
          m_elevator.topRun();
        }

        // m_elevator.setPercent(1);
        m_hopper.setPercent(1);
      
      } else {
        if (isIndexing == true) {
          isIndexing = false;
          m_elevator.incrementNumofBallsInLift();
          if (m_elevator.getNumOfBallsInLift() >= numberOfBallsToIndex) {
            endTimeStamp = Timer.getFPGATimestamp();
          }
        }
        m_elevator.setPercent(0);
        m_hopper.setPercent(1);
      } 
    }else if(!currentDebouncer.get()){
      isClogged = true;
      m_elevator.setPercent(-1.0);
      m_hopper.setPercent(-1.0);
    }else if(isClogged){
      isClogged = false;
      m_elevator.decrementNumofBallsInLift();
    }else if(!isClogged){
      m_elevator.setPercent(1.0);
      m_hopper.setPercent(1.0);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_elevator.setPercent(0);
    m_hopper.setPercent(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_elevator.getNumOfBallsInLift() >= numberOfBallsToIndex && Timer.getFPGATimestamp() >= endTimeStamp + backDriveDuration;
  }
}
