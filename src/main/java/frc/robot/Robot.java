// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.XboxController;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private final XboxController m_stick = new XboxController(0);
  
  // Robot Ports
  private final VictorSPX m_upperLeft = new VictorSPX(1);
  private final VictorSPX m_lowerRight = new VictorSPX(2);
  private final VictorSPX m_upperRight = new VictorSPX(3);
  private final VictorSPX m_lowerLeft = new VictorSPX(4);
  private GenericEntry m_drivePower;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  m_upperLeft.set(ControlMode.PercentOutput,0);
  m_upperRight.set(ControlMode.PercentOutput,0);
  m_lowerLeft.set(ControlMode.PercentOutput,0);
  m_lowerRight.set(ControlMode.PercentOutput,0);

  m_drivePower = Shuffleboard.getTab("Drive Variables")
        .add("Power Slider", 1)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1))
        .getEntry();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double power = Math.pow(m_drivePower.getDouble(1.0),3);
    if (m_stick.getAButton()){
      m_upperLeft.set(ControlMode.PercentOutput,power);
    }
    else if (m_stick.getBButton()){
      m_lowerLeft.set(ControlMode.PercentOutput,power);
    }
    else if (m_stick.getXButton()){
      m_upperRight.set(ControlMode.PercentOutput,power);
    }
    else if (m_stick.getYButton()){
      m_lowerRight.set(ControlMode.PercentOutput,power);
    }
    else {
    m_upperLeft.set(ControlMode.PercentOutput,-Math.pow(m_stick.getLeftY(),3));
    m_lowerLeft.set(ControlMode.PercentOutput,-Math.pow(m_stick.getLeftY(),3));
    m_upperRight.set(ControlMode.PercentOutput,Math.pow(m_stick.getRightY(),3));
    m_lowerRight.set(ControlMode.PercentOutput,Math.pow(m_stick.getRightY(),3));
    }
  }
 
}
