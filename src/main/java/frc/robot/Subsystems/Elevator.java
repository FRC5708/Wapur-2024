package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.GenericEntry;

public class Elevator{
private final TalonSRX m_highMotor = new TalonSRX (18);
private final TalonSRX m_lowMotor = new TalonSRX (19);

private final XboxController m_stick;


public Elevator(XboxController stick){
    m_stick = stick;

}

public void ElevatorInit(){
}

public void ElevatorPeriodic(){
if (m_stick.getRightBumper()){
    ElevatorUp();
}
else if(m_stick.getLeftBumper()){
    ElevatorDown();
}
else {
    ElevatorSet();
}
}

public void ElevatorUp(){
     m_highMotor.set(ControlMode.PercentOutput, -.35);
    m_lowMotor.set(ControlMode.PercentOutput, -.35);
}

public void ElevatorDown(){
    m_highMotor.set(ControlMode.PercentOutput, .35);
    m_lowMotor.set(ControlMode.PercentOutput, .35);
}

public void ElevatorSet(){
    m_highMotor.set(ControlMode.PercentOutput, 0);
    m_lowMotor.set(ControlMode.PercentOutput, 0);
}
}
