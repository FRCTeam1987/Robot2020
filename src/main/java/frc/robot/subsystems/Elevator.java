package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DigitalDebouncer;

public class Elevator extends SubsystemBase {

  private final WPI_VictorSPX topMotors;
  private final WPI_VictorSPX bottomMotors;
  private final DoubleSolenoid lift;
  private final DigitalInput lowProx;
  private int ballsInLift;
  private final PowerDistributionPanel m_pdp;
  private DigitalDebouncer entranceDebouncer;

  public Elevator(PowerDistributionPanel pdp) {
    topMotors = new WPI_VictorSPX(Constants.elevatorTopMotorsID);
    bottomMotors = new WPI_VictorSPX(Constants.elevatorBottomMotorsID);
    // bottomMotors.follow(topMotors);
    lift = new DoubleSolenoid(Constants.elevatorLiftIn, Constants.elevatorLiftOut);
    lowProx = new DigitalInput(0);
    entranceDebouncer = new DigitalDebouncer(.25);
    ballsInLift = 0;
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
    setPercent(-1);
  }

  public void stop() {
    setPercent(0);
  }

  public boolean hasMaxNumberOfBalls(){
    return getNumOfBallsInLift() >= getMaxAllowableNumberOfBalls();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Low Elev Prox", !lowProx.get());
    SmartDashboard.putBoolean("elevator-isDown", isDown());
    entranceDebouncer.periodic(!lowProx.get());
  }
}