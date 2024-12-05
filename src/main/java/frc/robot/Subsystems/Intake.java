package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.GenericEntry;

public class Intake {

private final TalonSRX m_leftIntake = new TalonSRX (5);
private final TalonSRX m_rightIntake = new TalonSRX (6);

private final XboxController m_stick;


public Intake(XboxController stick){
    m_stick = stick;
}

public void IntakeInit(){
}

public void IntakePeriodic(){
if (m_stick.getAButton()){
    IntakeIn();
}
else if (m_stick.getBButton()){
    IntakeOut();
}
else {
    IntakeZero();
}  
}

public void IntakeIn(){
    m_leftIntake.set(ControlMode.PercentOutput, .35);
    m_rightIntake.set(ControlMode.PercentOutput, -.35);
}
public void IntakeOut(){
    m_leftIntake.set(ControlMode.PercentOutput, -.35);
    m_rightIntake.set(ControlMode.PercentOutput, .35);
}
public void IntakeZero(){
    m_leftIntake.set(ControlMode.PercentOutput, 0);
    m_rightIntake.set(ControlMode.PercentOutput, 0);
}
}