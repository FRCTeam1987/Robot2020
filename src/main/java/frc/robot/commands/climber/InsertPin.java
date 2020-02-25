/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class InsertPin extends CommandBase {

  private final static double durationSeconds = 0.5;

  private Climber m_climber;
  private double m_startTime;

  public InsertPin(final Climber climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = climber;
    m_startTime = Double.MAX_VALUE - 9;
    addRequirements(m_climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_startTime = Timer.getFPGATimestamp();
    m_climber.pinInsert();
  }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return Timer.getFPGATimestamp() >= m_startTime + durationSeconds;
    }
}
