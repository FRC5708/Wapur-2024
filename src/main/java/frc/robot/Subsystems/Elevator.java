package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.GenericEntry;

public class Elevator{
private DigitalInput bottemSensor = new DigitalInput(0);
private DigitalInput topSensor = new DigitalInput(1);
    
private final TalonSRX m_highMotor = new TalonSRX (18);
private final TalonSRX m_lowMotor = new TalonSRX (19);

private GenericEntry m_upPower;
private GenericEntry m_downPower;
private GenericEntry m_holdPower;

private final XboxController m_stick;


public Elevator(XboxController stick){
    m_stick = stick;

}

public void ElevatorInit(){
    m_upPower = Shuffleboard.getTab("Constants")
        .add("Elevator Up Power", .3)
        .getEntry();

    m_downPower = Shuffleboard.getTab("Constants")
        .add("Elevator Down Power", .3)
        .getEntry();

    m_holdPower = Shuffleboard.getTab("Constants")
        .add("Elevator Stable Power", .2)
        .getEntry();
}

public void ElevatorPeriodic(){
if (m_stick.getYButton()){
    ElevatorUp();
}
else if(m_stick.getAButton()){
    ElevatorDown();
}
else if(m_stick.getBButton()){
    ElevatorHold();
}
else {
    ElevatorSet();
}
}

public void ElevatorUp(){
    double upPower = (double) m_upPower.getDouble(1);
    if (getTopSensor()){
        m_highMotor.set(ControlMode.PercentOutput, upPower);
        m_lowMotor.set(ControlMode.PercentOutput, upPower);
        System.out.println(upPower);
    }
    else {
        System.out.println("Too high, can't go further");
        m_highMotor.set(ControlMode.PercentOutput, 0);
        m_lowMotor.set(ControlMode.PercentOutput, 0);
    }
}

public void ElevatorDown(){
    double downPower = (double) m_downPower.getDouble(1);
    if (getBottemSensor()){
        m_highMotor.set(ControlMode.PercentOutput, -downPower);
        m_lowMotor.set(ControlMode.PercentOutput, -downPower);
        System.out.println(downPower);
    }
    else {
        System.out.println("Too low, can't go lower");
        m_highMotor.set(ControlMode.PercentOutput, 0);
        m_lowMotor.set(ControlMode.PercentOutput, 0);
    }
}

public void ElevatorHold(){
    double holdPower = (double) m_holdPower.getDouble(1);
    m_highMotor.set(ControlMode.PercentOutput, holdPower);
    m_lowMotor.set(ControlMode.PercentOutput, holdPower);
    System.out.println(holdPower);
}
public void ElevatorSet(){
    m_highMotor.set(ControlMode.PercentOutput, 0);
    m_lowMotor.set(ControlMode.PercentOutput, 0);
}


public boolean getTopSensor() {
    return bottemSensor.get();
  }

  public boolean getBottemSensor() {
    return topSensor.get();
  }

}
