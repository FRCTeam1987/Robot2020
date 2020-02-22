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
import frc.robot.Constants;

public class Collector extends SubsystemBase {
  
  private final CANSparkMax m_neo;
  private final CANSparkMax m_backRoller;
  private CANPIDController m_pidController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  private DoubleSolenoid piston;

  // TODO parameterize since there will be two instances of this
  public Collector() {
    piston = new DoubleSolenoid(Constants.collectorOut, Constants.collectorIn);

    m_neo = new CANSparkMax(Constants.collectorBack, MotorType.kBrushless);
    m_backRoller = new CANSparkMax(Constants.neoID, MotorType.kBrushless);
    m_neo.restoreFactoryDefaults();
    m_neo.setInverted(true);
    m_backRoller.follow(m_neo, true);

    m_pidController = m_neo.getPIDController();
    
    // TODO move these to constants
    m_pidController.setP(5e-5);
    m_pidController.setI(1e-6);
    m_pidController.setD(0);
    m_pidController.setIZone(0);
    m_pidController.setFF(0);
    m_pidController.setOutputRange(-1,1);
  }

  // TODO implemet setting an RPM if using the pid controller, otherwise remove the pid controller

  public void setNeoPercent(double percent){
    m_neo.set(-percent); //TODO (see comment below) either wiring, build or programmers need to properly do this
    //swapped master and slave IDs cuz front collector (master) was removed, so this was inverted temporarily until it's re-wired 
    //properly, build puts back the collector, or properly changed in code
  }

  public void collectorOut(){
    piston.set(Value.kForward);
  }

  public void collectorIn(){
    piston.set(Value.kReverse);
  }

  public boolean isOut() {
    return piston.get() == Value.kForward;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
