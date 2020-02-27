/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.commands.drive.AimBot;
import frc.robot.commands.drive.TankDriveInstant;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Shoot3AndMove extends SequentialCommandGroup {
  
  public Shoot3AndMove(Drive drive, Shooter shooter) {
    super(
      new WaitCommand(1),
      new ParallelRaceGroup(
        new AimBot(shooter, drive, false),
        new WaitCommand(2)
      ),
      new TankDriveInstant(drive, .5)
        .andThen(new WaitCommand(1))
        .andThen(new TankDriveInstant(drive, 0))
    );
  }
}
