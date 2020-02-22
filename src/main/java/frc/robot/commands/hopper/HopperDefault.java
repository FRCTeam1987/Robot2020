/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DigitalDebouncer;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;

public class HopperDefault extends CommandBase {

  private final static double motorStallAmps = 25.0;
  private final static double allowableStallDuration = 1.0;
  private final static double defaultJamStartTime = 0;

  private final Hopper m_hopper;
  private final Elevator m_elevator;
  private final DigitalDebouncer isJammed;
  private double jamStartTime;

  /**
   * Creates a new HopperDefault.
   */
  public HopperDefault(final Hopper hopper, final Elevator elevator) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_hopper = hopper;
    m_elevator = elevator;
    isJammed = new DigitalDebouncer(0.2);  // ignore instantaneous fluctuations
    jamStartTime = defaultJamStartTime;
    addRequirements(m_hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_hopper.stop();
    jamStartTime = defaultJamStartTime;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double currentTime = Timer.getFPGATimestamp();
    isJammed.periodic(m_hopper.getCurrent() > motorStallAmps);
    if (currentTime <= jamStartTime + allowableStallDuration) {
      // TODO notify the driver of a jam once
      System.out.println("hopper - Stopping for jam.");
      m_hopper.stop();
      return;
    }
    if (isJammed.get()) {
      System.out.println("hopper - jammed");
      if (jamStartTime != defaultJamStartTime) {
        jamStartTime = currentTime;
      }
      m_hopper.reverse();
      return;
    }
    if (m_elevator.hasMaxNumberOfBalls()) {
      System.out.println("hopper - max number of balls in elevator");
      m_hopper.stop();
      return;
    }
    System.out.println("hopper - default run");
    m_hopper.forward();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_hopper.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
