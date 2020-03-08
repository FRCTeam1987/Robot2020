package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.DigitalDebouncer;

public class Elevator extends SubsystemBase {

  private final WPI_VictorSPX topMotors;
  private final WPI_VictorSPX bottomMotors;
  private final DoubleSolenoid lift;
  private final DigitalInput lowProx;
  private final DigitalInput highProx;
  private int ballsInLift;
  private final PowerDistributionPanel m_pdp;
  private DigitalDebouncer entranceDebouncer;

  public Elevator(PowerDistributionPanel pdp) {
    topMotors = new WPI_VictorSPX(Constants.elevatorTopMotorsID);
    bottomMotors = new WPI_VictorSPX(Constants.elevatorBottomMotorsID);
    // bottomMotors.follow(topMotors);
    lift = new DoubleSolenoid(Constants.elevatorLiftIn, Constants.elevatorLiftOut);
    lowProx = new DigitalInput(0);
    highProx = new DigitalInput(1);
    entranceDebouncer = new DigitalDebouncer(.25);
    ballsInLift = Constants.Elevator.startingBallCount;
    m_pdp = pdp;

    setLiftUp();

    addChild("top", topMotors);
    addChild("bottom", bottomMotors);
  }

  public void setPercent(double percent) {
    topMotors.set(ControlMode.PercentOutput, percent);
    bottomMotors.set(ControlMode.PercentOutput, percent);
  }

  public void setNumOfBallsInLift(int numOfBalls){
    ballsInLift = numOfBalls;
  }

  public void incrementNumofBallsInLift(){
    ballsInLift++;
  }

  public void decrementNumofBallsInLift(){
    ballsInLift--;
  }

  public int getNumOfBallsInLift(){
    return ballsInLift;
  }

  public boolean isBallAtEntrance(){
    return entranceDebouncer.get();
  }

  public boolean isBallAtTop(){
    return !highProx.get();
  }

  public boolean isBallAtEntranceRaw(){
    return !lowProx.get();
  }

  public double getBottomMotorCurrent(){
    return m_pdp.getCurrent(4);
  }

  public void topRun() {
    topMotors.set(ControlMode.PercentOutput, 1);
  }

  public void topRunDown() {
    topMotors.set(ControlMode.PercentOutput, -1);
  }

  public void topStop() {
    topMotors.set(ControlMode.PercentOutput, 0);
  }

  public void bottomRun() {
    bottomMotors.set(ControlMode.PercentOutput, 1);
  }

  public void bottomRunSlow(){
    bottomMotors.set(ControlMode.PercentOutput, .75);
  }

  public void bottomRunDown() {
    bottomMotors.set(ControlMode.PercentOutput, -1);
  }

  public void bottomStop() {
    bottomMotors.set(ControlMode.PercentOutput, 0);
  }

  public void setLiftUp() {
    lift.set(DoubleSolenoid.Value.kForward);
  }

  public void setLiftDown() {
    lift.set(DoubleSolenoid.Value.kReverse);
  }

  public boolean isDown() {
    return lift.get() == Value.kReverse;
  }

  // TODO make this to return true if intaking
  public boolean isBottomRunning() {
    return getBottomMotorCurrent() > 1.0;
  }

  public double getMaxAllowableNumberOfBalls() {
    return isDown() ? Constants.Elevator.maxNumberOfBallsWhileDown : Constants.Elevator.maxNumberOfBallsWhileUp;
  }

  public void forward() {
    setPercent(1);
  }

  public void reverse() {
    setPercent(-0.75);
  }

  public void stop() {
    setPercent(0);
  }

  public boolean hasMaxNumberOfBalls(){
    return getNumOfBallsInLift() >= getMaxAllowableNumberOfBalls();
  }

  public boolean canGoDown(){
    return getNumOfBallsInLift() <= 1;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Low Elev Prox", !lowProx.get());
    SmartDashboard.putBoolean("High Elev Prox", !highProx.get());
    SmartDashboard.putNumber("Balls in lift", ballsInLift);
    SmartDashboard.putBoolean("elevator-isDown", isDown());
    entranceDebouncer.periodic(!lowProx.get());

  }
}
