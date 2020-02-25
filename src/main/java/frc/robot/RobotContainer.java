/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.Shoot;
import frc.robot.commands.climber.PullPin;
import frc.robot.commands.climber.SpoolUp;
import frc.robot.commands.climber.Unspool;
import frc.robot.commands.collector.DeployCollector;
import frc.robot.commands.collector.RetractCollector;
import frc.robot.commands.collector.SetCollectorPercent;
import frc.robot.commands.control_panel.LowerCP;
import frc.robot.commands.control_panel.RunCP;
import frc.robot.commands.drive.AimBot;
// import frc.robot.commands.drive.AutoAim;
import frc.robot.commands.drive.TeleopDrive;
import frc.robot.commands.elevator.BallCounterDecrement;
import frc.robot.commands.elevator.BallCounterIncrement;
import frc.robot.commands.elevator.BallCounterReset;
import frc.robot.commands.elevator.ElevatorDefault;
import frc.robot.commands.elevator.Index;
import frc.robot.commands.elevator.RunElevatorForward;
import frc.robot.commands.elevator.RunElevatorReverse;
import frc.robot.commands.elevator.SetElevatorPercent;
import frc.robot.commands.elevator.SetLiftDown;
import frc.robot.commands.elevator.SetLiftUp;
import frc.robot.commands.hopper.HopperDefault;
import frc.robot.commands.hopper.RunHopperForward;
import frc.robot.commands.hopper.RunHopperReverse;
import frc.robot.commands.hopper.SetHopperPercent;
import frc.robot.commands.shooter.SetRPMreal;
import frc.robot.commands.shooter.SetShooterPercent;
import frc.robot.commands.shooter.ShooterDefault;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final XboxController driver;
  private final XboxController coDriver;
  private final JoystickButton collect;
  private final XboxDPad testRunAll;
  private final XboxDPad testStopAll;
  private final JoystickButton shoot;
  private final JoystickButton toggleTrenchConfig;
  private final JoystickButton runCollectorMotors;
  // private final JoystickButton reverseHopper;
  // private final JoystickButton index;
  private final JoystickButton controlPanel;
  private final JoystickButton autoAim;
  private final JoystickButton climberRelease;
  private final JoystickButton climberPull;
  private final POVButton coBallCounterDecrement;
  private final POVButton coBallCounterIncrement;
  private final POVButton coBallCounterReset;
  private final JoystickButton coElevatorRunForward;
  private final JoystickButton coElevatorRunReverse;
  private final JoystickButton coHopperRunForward;
  private final JoystickButton coHopperRunReverse;
  private final JoystickButton coClimberSpoolUp;
  private final JoystickButton coClimberUnspool;
  // private final JoystickButton autoAim;

  private final PowerDistributionPanel m_pdp = new PowerDistributionPanel();
  private final Drive m_drive = new Drive();
  private final Shooter m_shooter = new Shooter();
  private final Collector m_collector = new Collector();
  private final Hopper m_hopper = new Hopper(m_pdp);
  private final Elevator m_elevator = new Elevator(m_pdp);
  private final ControlPanel m_controlPanel = new ControlPanel();
  private final Climber m_climber = new Climber(
    new Solenoid(Constants.Climber.Actuators.Solenoids.releasePinID),
    new WPI_VictorSPX(Constants.Climber.Actuators.Motors.winchMaster),
    new WPI_VictorSPX(Constants.Climber.Actuators.Motors.winchSlave)
  );

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    
    driver = new XboxController(Constants.driverID);
    coDriver = new XboxController(Constants.coDriverID);

    collect = new JoystickButton(driver, XboxController.Button.kX.value);
    shoot = new JoystickButton(driver, XboxController.Button.kY.value);
    toggleTrenchConfig = new JoystickButton(driver, XboxController.Button.kB.value);
    controlPanel = new JoystickButton(driver, XboxController.Button.kA.value);
    runCollectorMotors = new JoystickButton(driver, XboxController.Button.kStart.value);
    // index = new JoystickButton(driver, XboxController.Button.kStickRight.value);
    testRunAll = new XboxDPad(driver, XboxDPad.Direction.Up);
    testStopAll = new XboxDPad(driver, XboxDPad.Direction.Down);
    autoAim = new JoystickButton(driver, XboxController.Button.kBack.value);
    climberRelease = new JoystickButton(driver, XboxController.Button.kBumperLeft.value);
    climberPull = new JoystickButton(driver, XboxController.Button.kBumperRight.value);
    // reverseHopper = new JoystickButton(coDriver, XboxController.Button.kB.value);

    coBallCounterDecrement = new POVButton(coDriver, XboxDPad.Direction.Down.get());
    coBallCounterIncrement = new POVButton(coDriver, XboxDPad.Direction.Up.get());
    coBallCounterReset = new POVButton(coDriver, XboxDPad.Direction.Right.get());
    coElevatorRunReverse = new JoystickButton(coDriver, XboxController.Button.kA.value);
    coElevatorRunForward = new JoystickButton(coDriver, XboxController.Button.kY.value);
    coHopperRunForward = new JoystickButton(coDriver, XboxController.Button.kB.value);
    coHopperRunReverse = new JoystickButton(coDriver, XboxController.Button.kX.value);
    coClimberSpoolUp = new JoystickButton(coDriver, XboxController.Button.kBumperRight.value);
    coClimberUnspool = new JoystickButton(coDriver, XboxController.Button.kBumperLeft.value);

    // Configure the button bindings
    configureButtonBindings();
    configureShuffleboard();
    configureDefaultCommands();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // primary driver
    collect.whenPressed(new ParallelCommandGroup(
      new DeployCollector(m_collector),
      new Index(m_elevator, m_hopper)
    ));
    // collect.whenPressed(new DeployCollector(m_collector));
    collect.whenReleased(new ParallelCommandGroup(
      new RetractCollector(m_collector),
      new SetElevatorPercent(m_elevator, 0),
      new SetHopperPercent(m_hopper, 0)
    ));
    // collect.whenReleased(new RetractCollector(m_collector));
    shoot.whenPressed(new Shoot(m_shooter, m_elevator, m_hopper));
    shoot.whenReleased(new SetHopperPercent(m_hopper, 0.0)  // TODO make real command for this (shoot command group)
      .andThen(new SetElevatorPercent(m_elevator, 0.0))
      .andThen(new SetShooterPercent(m_shooter, 0.0))
    );
    toggleTrenchConfig.whenPressed(new ConditionalCommand(
      new SetLiftUp(m_elevator),
      new SetLiftDown(m_elevator),
      () -> m_elevator.isDown()
     ));
    controlPanel.whenPressed(new RunCP(m_controlPanel));
    testRunAll.whenPressed(new SetHopperPercent(m_hopper, 1.0)
      .andThen(new SetElevatorPercent(m_elevator, 1.0))
      .andThen(new SetShooterPercent(m_shooter, 0.7))
    );
    testStopAll.whenPressed(new SetHopperPercent(m_hopper, 0.0)
      .andThen(new SetElevatorPercent(m_elevator, 0.0))
      .andThen(new SetShooterPercent(m_shooter, 0.0))
    );
    runCollectorMotors.whenPressed(new SetCollectorPercent(m_collector, 1.0));
    runCollectorMotors.whenReleased(new SetCollectorPercent(m_collector, 0.0));
    autoAim.whileHeld(new AimBot(m_shooter, m_drive, false));
    climberRelease.whenPressed(new PullPin(m_climber));
    climberPull.whileHeld(new Unspool(m_climber));
    // controlPanel.whenPressed(new );

    // secondary driver
    // reverseHopper.whenPressed(new SetHopperPercent(m_hopper, -1.0));
    // reverseHopper.whenReleased(new SetHopperPercent(m_hopper, 0.0));
    // index.whenPressed(new Index(m_elevator, m_hopper));
    coBallCounterDecrement.whenPressed(new BallCounterDecrement(m_elevator));
    coBallCounterIncrement.whenPressed(new BallCounterIncrement(m_elevator));
    coBallCounterReset.whenPressed(new BallCounterReset(m_elevator));
    coElevatorRunReverse.whileHeld(new RunElevatorReverse(m_elevator));
    coElevatorRunForward.whileHeld(new RunElevatorForward(m_elevator));
    coHopperRunForward.whileHeld(new RunHopperForward(m_hopper));
    coHopperRunReverse.whileHeld(new RunHopperReverse(m_hopper));
    coClimberSpoolUp.whileHeld(new SpoolUp(m_climber));
    coClimberUnspool.whileHeld(new Unspool(m_climber));
  }

  private void configureShuffleboard() {
    // TODO make different tabs for diagnostics / in-match
    SmartDashboard.putData("Run Hopper Motor", new SetHopperPercent(m_hopper, 1.0));
    SmartDashboard.putData("Stop Hopper Motor", new SetHopperPercent(m_hopper, 1.0));
    SmartDashboard.putData("Run Elevator Motors", new SetElevatorPercent(m_elevator, 0.50));
    SmartDashboard.putData("Stop Elevator Motors", new SetElevatorPercent(m_elevator, 0.0));
    SmartDashboard.putData("Set RPM 500", new SetRPMreal(m_shooter, 500));
    SmartDashboard.putData("Set RPM 1000", new SetRPMreal(m_shooter, 1000));
    SmartDashboard.putData("Set RPM 2500", new SetRPMreal(m_shooter, 2500));
    SmartDashboard.putData("Set RPM 3000", new SetRPMreal(m_shooter, 3000));
    SmartDashboard.putData("Set Shooter 25%", new SetShooterPercent(m_shooter, 0.25));
    SmartDashboard.putData("Set Shooter 50%", new SetShooterPercent(m_shooter, 0.5));
    SmartDashboard.putData("Set Shooter 75%", new SetShooterPercent(m_shooter, 0.75));
    SmartDashboard.putData("Stop Shooter", new SetShooterPercent(m_shooter, 0.0));
    SmartDashboard.putData("Deploy Collector CG", new DeployCollector(m_collector));
    SmartDashboard.putData("Retract Collector CG", new RetractCollector(m_collector));
    SmartDashboard.putData("Run All", new SetHopperPercent(m_hopper, 1.0)
      .andThen(new SetElevatorPercent(m_elevator, 1.0))
      .andThen(new SetShooterPercent(m_shooter, 0.7))
    );
    SmartDashboard.putData("Stop All", new SetHopperPercent(m_hopper, 0.0)
    .andThen(new SetElevatorPercent(m_elevator, 0.0))
    .andThen(new SetShooterPercent(m_shooter, 0.0))
    );
    SmartDashboard.putData("Lower Elevator", new SetLiftDown(m_elevator));
    SmartDashboard.putData("Raise Elevator", new SetLiftUp(m_elevator));
    SmartDashboard.putData("AimBot", new AimBot(m_shooter, m_drive, true));
    SmartDashboard.putNumber("Shooter RPM", 4000);
    SmartDashboard.putData("Index", new Index(m_elevator, m_hopper));
    SmartDashboard.putData("Shoot", new Shoot(m_shooter, m_elevator, m_hopper));
    SmartDashboard.putData("lower CP", new LowerCP(m_controlPanel));
  }


  private void configureDefaultCommands() {
    m_drive.setDefaultCommand(new TeleopDrive(m_drive, driver, m_shooter));
    m_shooter.setDefaultCommand(new ShooterDefault(m_shooter, m_elevator));
    m_elevator.setDefaultCommand(new ElevatorDefault(m_elevator, m_shooter));
    m_hopper.setDefaultCommand(new HopperDefault(m_hopper, m_elevator));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() { //TODO Look at this m_autoCommand thing in the return and do it right
    // An ExampleCommand will run in autonomous
    return null;//m_autoCommand;
  }
}
