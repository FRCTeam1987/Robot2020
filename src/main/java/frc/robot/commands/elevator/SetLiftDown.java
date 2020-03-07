package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.DigitalDebouncer;
import frc.robot.subsystems.Elevator;

public class SetLiftDown extends CommandBase {
  
  private Elevator m_elevator;
  private final double m_wait = 0.8;
  private double m_completionTimeStamp;
  private boolean ballIsAtEntrance;
  private boolean ballWasAtEntrance;

  public SetLiftDown(Elevator elevator) {
    m_elevator = elevator;
    m_completionTimeStamp = 0;
    addRequirements(m_elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ballWasAtEntrance = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ballIsAtEntrance = m_elevator.isBallAtEntrance();

    // TODO if reversing flow breaks things, take out if/else statements, don't lower elevator if(!canGoDown) , and add rumble as a conditional command  with toggle trench config
    if(m_elevator.canGoDown()){
      m_elevator.setLiftDown();
      m_completionTimeStamp = Timer.getFPGATimestamp();
    } 
    else{
      m_elevator.reverse(); 

      if(ballIsAtEntrance){ // if balls are at entrance, just update the wasAtEntrance bool
        ballWasAtEntrance = ballIsAtEntrance;
      } 
      else{ // if there used to be a ball but there isn't anymore, then decrement balls in lift
        if(ballWasAtEntrance && !ballIsAtEntrance){
          m_elevator.decrementNumofBallsInLift();
        }
        ballWasAtEntrance = ballIsAtEntrance;
      }

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() - m_completionTimeStamp >= m_wait;
  }
}
