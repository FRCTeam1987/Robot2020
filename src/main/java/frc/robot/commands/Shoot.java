/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.SetElevatorPercent;
import frc.robot.commands.elevator.SetLiftUp;
import frc.robot.commands.hopper.SetHopperPercent;
import frc.robot.commands.shooter.SetRPMreal;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Shoot extends SequentialCommandGroup {
  /**
   * Creates a new Shoot.
   */
  public Shoot(final Shooter shooter, final Elevator elevator, final Hopper hopper) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    // TODO only allow this to run if elevator is up
    super(
      new SetRPMreal(shooter, 3600),  // 4500 behind control panel, 3550 for in front of CP spot, 3000 for init line
      // new SetRPMreal(shooter),  // get the number from the smart dashboard
      new SetLiftUp(elevator),
      new SetElevatorPercent(elevator, 1.0),
      new SetHopperPercent(hopper, 1.0)
    );
  }
}
