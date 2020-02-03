/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Collector.CollectorIn;
import frc.robot.commands.Collector.SetNeoPercent;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DeployCollector extends SequentialCommandGroup {
  /**
   * Creates a new DeployCollector.
   */
  public DeployCollector() {
    super(
      new CollectorIn(),
      new SetNeoPercent(0.65)
    );
  }
}
