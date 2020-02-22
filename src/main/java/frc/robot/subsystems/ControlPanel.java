/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ControlPanel extends SubsystemBase {
  
  private final VictorSPX cpMotor;
  private final ColorSensorV3 m_colorSensor;
  private final ColorMatch m_colorMatcher;
  private final DoubleSolenoid deploy;
  private String targetColor;
  private Color initColor;
  private Color lastColor;
  private int colorCount;
  private String expectedColor;
  private ColorMatchResult currentColor;
  private boolean hasRunBefore;


  public ControlPanel() {
    m_colorSensor = new ColorSensorV3(Constants.i2cPort);
    m_colorMatcher = new ColorMatch();
    deploy = new DoubleSolenoid(Constants.controlPanelDeploy, Constants.controlPanelRetract);
    colorCount = 0;
    expectedColor = "";
    targetColor = "";
    hasRunBefore = false;

    m_colorMatcher.addColorMatch(Constants.kBlueTarget);
    m_colorMatcher.addColorMatch(Constants.kGreenTarget);
    m_colorMatcher.addColorMatch(Constants.kRedTarget);
    m_colorMatcher.addColorMatch(Constants.kYellowTarget);

    initColor = null;

    cpMotor = new WPI_VictorSPX(Constants.controlPanelMotorID);
    cpMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void deployCP(){
    deploy.set(DoubleSolenoid.Value.kForward);
  }

  public void retractCP(){
    deploy.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggleHasRunBefore(){
    if(hasRunBefore){ //btw I know you don't need the curly brackets for 1 liners but it looks weird
      hasRunBefore = false;
    } else{
      hasRunBefore = true;
    }
  }

  public boolean getHasRunBefore(){
    return hasRunBefore;
  }

  public void setRPM(double rpm) {
  cpMotor.set(ControlMode.Velocity, (rpm / 600) * (Constants.ticksPerRotation / Constants.cpReduction));
  }

  public String getTargetColor() {
    return targetColor;
  }

  public Color getCurrentColor() {
    return m_colorMatcher.matchClosestColor(m_colorSensor.getColor()).color;
  }

  public String getCurrentColorStr() {
    Color color = getCurrentColor();

    if (color == Constants.kBlueTarget) {
      return "B";
    } else if (color == Constants.kRedTarget) {
      return "R";
    } else if (color == Constants.kGreenTarget) {
      return "G";
    } else if (color == Constants.kYellowTarget) {
      return "Y";
    } else {
      return "";
    }
  }

  public void spinToGameColor() {
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
        case 'B':
          targetColor = Constants.gameColorTargetBlue;
          break;
        case 'G':
          targetColor = Constants.gameColorTargetGreen;
          break;
        case 'R':
          targetColor = Constants.gameColorTargetRed;
          break;
        case 'Y':
          targetColor = Constants.gameColorTargetYellow;
          break;
        default:
          targetColor = Constants.gameColorTargetYellow;
          break;
      }

      calculateExpectedColor(getCurrentColor());
    } else {
      targetColor = null;
    }

    setPercent(-0.25);
  }

  public void spin3Times() {
    initColor = lastColor = getCurrentColor();
    calculateExpectedColor(initColor);
    resetColorCount();
    setPercent(-0.50);
  }

  public void setPercent(double percent) {
    cpMotor.set(ControlMode.PercentOutput, percent);
  }

  public void calculateExpectedColor(Color color) {
    if (color == Constants.kBlueTarget) {
      expectedColor = Constants.fromBlue;
    } else if (color == Constants.kYellowTarget) {
      expectedColor = Constants.fromYellow;
    } else if (color == Constants.kGreenTarget) {
      expectedColor = Constants.fromGreen;
    } else if (color == Constants.kRedTarget) {
      expectedColor = Constants.fromRed;
    }
  }

  public void resetColorCount() {
    colorCount = 0;
  }

  private boolean isExpectedColor(Color color) {
    if (color == Constants.kBlueTarget && expectedColor == "B") {
      return true;
    } else if (color == Constants.kYellowTarget && expectedColor == "Y") {
      return true;
    } else if (color == Constants.kGreenTarget && expectedColor == "G") {
      return true;
    } else if (color == Constants.kRedTarget && expectedColor == "R") {
      return true;
    }

    return false;
  }

  public boolean shouldSpinCP3Times(){
    Color currentColor;

    if(colorCount < 25){ //will see 8 color transitions per rotation, then we add 1 more for good measure
      currentColor = m_colorSensor.getColor();
      currentColor = m_colorMatcher.matchClosestColor(currentColor).color;
      if (expectedColor.isEmpty()) {
        calculateExpectedColor(currentColor);
      }
      if (currentColor != lastColor && isExpectedColor(currentColor)) {
        colorCount++;
        lastColor = currentColor;
        calculateExpectedColor(lastColor);
      }
      return false;
    }
    return true;
  }

  // Return true if wheel needs to keep spinning
  public boolean shouldSpinToGameColor() {

    Color currentColor = m_colorSensor.getColor();
    currentColor = m_colorMatcher.matchClosestColor(currentColor).color;
    String currentColorStr = getCurrentColorStr();

    // Stop spinning if we found the target color and it's not the
    // expected transition color.
    if (currentColorStr == targetColor && expectedColor != targetColor) {
      return false;
    }

    // Keep spinning if we have not found the expected transition color yet
    if (!isExpectedColor(currentColor)) {
      return true;
    }

    // Found the expected transition color.  Calculate the next one.
    calculateExpectedColor(currentColor);

    // return currentColorStr != targetColor;

    // if (currentColorStr == targetColor) {
    //   return false;
    // }

    // Look at currentColor and if not expectedColor return true
    // if (currentColorStr != expectedColor) {
    //   return true;
    // }

    // Keep going
    return true;

    // Return false if currentColor == targetColor
    // return currentColorStr != targetColor;
  }

  public boolean isTargetColor(Color detectedColor, Color targetColor) {
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == targetColor) {
      return true;
    }
    return false;
  }

  @Override
  public void periodic() {
    String currentColorString;
    double confidenceLevel = 0.0;

    if (currentColor != null) {
      confidenceLevel = currentColor.confidence;
    }

    if (currentColor == null) {
      currentColorString = "null";
    } else if (currentColor.color == Constants.kBlueTarget) {
      currentColorString = "Blue";
    } else if (currentColor.color == Constants.kRedTarget) {
      currentColorString = "Red";
    } else if (currentColor.color == Constants.kGreenTarget) {
      currentColorString = "Green";
    } else if (currentColor.color == Constants.kYellowTarget) {
      currentColorString = "Yellow";
    } else {
      currentColorString = "Unknown";
    }

    SmartDashboard.putNumber("color count", colorCount);
    SmartDashboard.putString("current color", currentColorString);
    SmartDashboard.putString("expected color", expectedColor);
    SmartDashboard.putString("target color", targetColor);
    SmartDashboard.putNumber("Color Confidece", confidenceLevel);
    // SmartDashboard.putNumber("Red", cColor.color.red);
    // SmartDashboard.putNumber("Green", cColor.color.green);
    // SmartDashboard.putNumber("Blue", cColor.color.blue);
  }
}
