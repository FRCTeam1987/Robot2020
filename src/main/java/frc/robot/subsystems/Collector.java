/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Collector extends SubsystemBase {
  
  private final CANSparkMax m_neo;
  private CANPIDController m_pidController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  private DoubleSolenoid piston;

  public Collector() {
    piston = new DoubleSolenoid(RobotMap.collectorOut, RobotMap.collectorIn);

    m_neo = new CANSparkMax(RobotMap.neoID, MotorType.kBrushless);
    m_neo.restoreFactoryDefaults();

    m_pidController = m_neo.getPIDController();
    
    m_pidController.setP(5e-5);
    m_pidController.setI(1e-6);
    m_pidController.setD(0);
    m_pidController.setIZone(0);
    m_pidController.setFF(0);
    m_pidController.setOutputRange(-1,1);
  }

  public void setNeoPercent(double percent){
    m_neo.set(percent);
  }

  public void collectorOut(){
    piston.set(Value.kForward);
  }

  public void collectorIn(){
    piston.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
