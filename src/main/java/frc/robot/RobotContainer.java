/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.XboxDPad.Direction;
import frc.robot.commands.Rumble;
import frc.robot.commands.Shoot;
import frc.robot.commands.StopAll;
import frc.robot.commands.auto.Shoot3AndMove;
import frc.robot.commands.auto.TestDrive;
import frc.robot.commands.auto.TestRamsete;
import frc.robot.commands.climber.InsertPin;
import frc.robot.commands.climber.PullPin;
import frc.robot.commands.climber.SpoolUp;
import frc.robot.commands.climber.Unspool;
import frc.robot.commands.collector.DeployCollector;
import frc.robot.commands.collector.RetractCollector;
import frc.robot.commands.collector.SetCollectorPercent;
import frc.robot.commands.collector.ToggleDeployment;
import frc.robot.commands.control_panel.FindColor;
import frc.robot.commands.control_panel.LowerCP;
import frc.robot.commands.control_panel.Spin3Times;
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
import frc.robot.commands.shooter.ToggleFarShot;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // private final ExampleCommand m_autoCommand = new
  // ExampleCommand(m_exampleSubsystem);

  private final XboxController driver;
  private final XboxController coDriver;
  private final JoystickButton collect;
  private final JoystickButton shoot;
  private final JoystickButton toggleTrenchConfig;
  private final JoystickButton runCollectorMotors;
  private final POVButton controlPanelSpin;
  private final POVButton controlPanelFind;
  private final JoystickButton autoAim;
  private final JoystickButton climberRelease;
  private final JoystickButton climberPull;
  private final POVButton actuateCollector;
  private final POVButton coBallCounterDecrement;
  private final POVButton coBallCounterIncrement;
  private final POVButton coBallCounterReset;
  private final JoystickButton coFlowReverse;
  private final JoystickButton coFlowForward;
  private final JoystickButton coClimberSpoolUp;
  private final JoystickButton coToggleFarShot;
  // private final JoystickButton coClimberUnspool;
  private final JoystickButton coStopAll;
  // private final JoystickButton startCompressor;
  // private final JoystickButton stopCompressor;

  private final PowerDistributionPanel m_pdp = new PowerDistributionPanel();
  // private final Compressor m_compressor = new Compressor();
  private final Drive m_drive = new Drive();
  private final Shooter m_shooter = new Shooter();
  private final Collector m_collector = new Collector();
  private final Hopper m_hopper = new Hopper(m_pdp, new WPI_VictorSPX(Constants.hopperMotorID));
  private final Elevator m_elevator = new Elevator(m_pdp);
  private final ControlPanel m_controlPanel = new ControlPanel();
  private final Climber m_climber = new Climber(new Solenoid(Constants.Climber.Actuators.Solenoids.releasePinID),
      new WPI_VictorSPX(Constants.Climber.Actuators.Motors.winchMaster),
      new WPI_VictorSPX(Constants.Climber.Actuators.Motors.winchSlave));

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    driver = new XboxController(Constants.driverID);
    coDriver = new XboxController(Constants.coDriverID);

    collect = new JoystickButton(driver, XboxController.Button.kX.value);
    shoot = new JoystickButton(driver, XboxController.Button.kY.value);
    toggleTrenchConfig = new JoystickButton(driver, XboxController.Button.kB.value);
    controlPanelSpin = new POVButton(driver, XboxDPad.Direction.Left.get()); // TODO make smart dashboard button to rest
                                                                             // if even works :`(
    controlPanelFind = new POVButton(driver, XboxDPad.Direction.Right.get());
    runCollectorMotors = new JoystickButton(driver, XboxController.Button.kStickRight.value);
    autoAim = new JoystickButton(driver, XboxController.Button.kBack.value);
    climberRelease = new JoystickButton(driver, XboxController.Button.kStart.value);
    climberPull = new JoystickButton(driver, XboxController.Button.kBumperRight.value);
    actuateCollector = new POVButton(driver, XboxDPad.Direction.Down.get());

    coBallCounterDecrement = new POVButton(coDriver, XboxDPad.Direction.Down.get());
    coBallCounterIncrement = new POVButton(coDriver, XboxDPad.Direction.Up.get());
    coBallCounterReset = new POVButton(coDriver, XboxDPad.Direction.Right.get());
    coClimberSpoolUp = new JoystickButton(coDriver, XboxController.Button.kBumperRight.value);
    // coClimberUnspool = new JoystickButton(coDriver,
    // XboxController.Button.kBumperLeft.value); can't do this mid-match because of
    // the ratchet. design must be changed if this is needed
    coFlowReverse = new JoystickButton(coDriver, XboxController.Button.kA.value);
    coFlowForward = new JoystickButton(coDriver, XboxController.Button.kY.value);
    coStopAll = new JoystickButton(coDriver, XboxController.Button.kBack.value);
    coToggleFarShot = new JoystickButton(coDriver, XboxController.Button.kB.value); // TODO make sure Austin is OK with
                                                                                    // this button's mapping

    // Configure the button bindings
    configureButtonBindings();
    configureShuffleboard();
    configureDefaultCommands();
  }

  public XboxController getDriver() {
    return driver;
  }

  public XboxController getCoDriver() {
    return coDriver;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // primary driver
    collect.whenPressed(new DeployCollector(m_collector));
    collect.whenReleased(new RetractCollector(m_collector));
    shoot.whenPressed(new Shoot(m_shooter, m_elevator, m_hopper));
    shoot.whenReleased(new SetHopperPercent(m_hopper, 0.0) // TODO make real command for this (shoot command group)
        .andThen(new SetElevatorPercent(m_elevator, 0.0)).andThen(new SetShooterPercent(m_shooter, 0.0)));
    toggleTrenchConfig.whenPressed(
      new ConditionalCommand(
        new SetLiftUp(m_elevator),
        new ConditionalCommand(
          new SetLiftDown(m_elevator),
          new ParallelRaceGroup(
            new Rumble(driver),
            new WaitCommand(0.5)
          ),
          () -> m_elevator.canGoDown()
        ),
        () -> m_elevator.isDown()
      )
    );
    controlPanelSpin.whenPressed(new Spin3Times(m_controlPanel));
    controlPanelFind.whenPressed(new FindColor(m_controlPanel));
    runCollectorMotors.whenPressed(new SetCollectorPercent(m_collector, 1.0));
    runCollectorMotors.whenReleased(new SetCollectorPercent(m_collector, 0.0));
    autoAim.whileHeld(new AimBot(m_shooter, m_drive, false));
    climberRelease.whenPressed(new PullPin(m_climber));
    climberPull.whileHeld(new SpoolUp(m_climber));
    actuateCollector.whenPressed(new ToggleDeployment(m_collector));
    // controlPanel.whenPressed(new );

    // secondary driver
    // reverseHopper.whenPressed(new SetHopperPercent(m_hopper, -1.0));
    // reverseHopper.whenReleased(new SetHopperPercent(m_hopper, 0.0));
    // index.whenPressed(new Index(m_elevator, m_hopper));
    coBallCounterDecrement.whenPressed(new BallCounterDecrement(m_elevator));
    coBallCounterIncrement.whenPressed(new BallCounterIncrement(m_elevator));
    coBallCounterReset.whenPressed(new BallCounterReset(m_elevator));
    coClimberSpoolUp.whileHeld(new SpoolUp(m_climber));
    // coClimberUnspool.whileHeld(new Unspool(m_climber));
    coFlowForward.whileHeld(new ParallelCommandGroup(
      new RunHopperForward(m_hopper),
      new RunElevatorForward(m_elevator))
    );
    coFlowReverse.whileHeld(new ParallelCommandGroup(
      new RunHopperReverse(m_hopper),
      new RunElevatorReverse(m_elevator))
    );
    coStopAll.whenPressed(new StopAll(m_hopper, m_elevator, m_collector));
    coToggleFarShot.whenPressed(new ToggleFarShot(m_shooter));
  }

  private void configureShuffleboard() {
    // TODO make different tabs for diagnostics / in-match
    SmartDashboard.putData("Run Hopper Motor", new SetHopperPercent(m_hopper, 1.0));
    SmartDashboard.putData("Stop Hopper Motor", new SetHopperPercent(m_hopper, 1.0));
    SmartDashboard.putData("Run Elevator Motors", new SetElevatorPercent(m_elevator, 0.50));
    SmartDashboard.putData("Stop Elevator Motors", new SetElevatorPercent(m_elevator, 0.0));
    SmartDashboard.putData("Stop Shooter", new SetShooterPercent(m_shooter, 0.0));
    SmartDashboard.putData("retract CP", new LowerCP(m_controlPanel));
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
    // SmartDashboard.putData("Index", new Index(m_elevator, m_hopper));
    SmartDashboard.putData("Shoot", new Shoot(m_shooter, m_elevator, m_hopper));
    SmartDashboard.putData("Retract climber pin", new PullPin(m_climber));
    SmartDashboard.putData("Extend climber pin", new InsertPin(m_climber));
    SmartDashboard.putData("Set Shooter RPM", new SetRPMreal(m_shooter, 1000));
    SmartDashboard.putBoolean("isFarShot", m_shooter.getIsFarShot());
    // SmartDashboard.putData("lower CP", new LowerCP(m_controlPanel));
  }


  private void configureDefaultCommands() {
    m_drive.setDefaultCommand(new TeleopDrive(m_drive, driver, m_shooter));
    m_shooter.setDefaultCommand(new ShooterDefault(m_shooter, m_elevator));
    m_elevator.setDefaultCommand(new ElevatorDefault(m_elevator, m_shooter));
    m_hopper.setDefaultCommand(new HopperDefault(m_hopper, m_elevator));
  }

  public void autoInit(){
    m_elevator.setLiftDown();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() { //TODO Look at this m_autoCommand thing in the return and do it right
    // An ExampleCommand will run in autonomous
    // return new Shoot3AndMove(m_drive, m_shooter);
    return new TestRamsete(m_drive);
  }

  public void disabledInit(){
    m_drive.setCoast();
  }
}
