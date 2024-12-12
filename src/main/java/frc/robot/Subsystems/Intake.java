package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.GenericEntry;

public class Intake {

private final TalonSRX m_leftIntake = new TalonSRX (5);
private final TalonSRX m_rightIntake = new TalonSRX (6);

private GenericEntry m_inPower;
private GenericEntry m_outPower;

private final XboxController m_stick;

public Intake(XboxController stick){
    m_stick = stick;
}

public void IntakeInit(){
    m_inPower = Shuffleboard.getTab("Constants")
    .add("Elevator Up Power", .5)
    .getEntry();

    m_outPower = Shuffleboard.getTab("Constants")
    .add("Elevator Down Power", -.5)
    .getEntry();

}

public void IntakePeriodic(){
if (m_stick.getRightBumper()){
    IntakeIn();
}
else if (m_stick.getLeftBumper()){
    IntakeOut();
}
else {
    IntakeZero();
}  
}

public void IntakeIn(){
    int inPower = (int) m_inPower.getInteger(1);
    m_leftIntake.set(ControlMode.PercentOutput, -inPower);
    m_rightIntake.set(ControlMode.PercentOutput, inPower);
    System.out.println(inPower);
}
public void IntakeOut(){
    int outPower = (int) m_outPower.getInteger(1);
    m_leftIntake.set(ControlMode.PercentOutput, outPower);
    m_rightIntake.set(ControlMode.PercentOutput, -outPower);
    System.out.println(outPower);
}
public void IntakeZero(){
    m_leftIntake.set(ControlMode.PercentOutput, 0);
    m_rightIntake.set(ControlMode.PercentOutput, 0);
}
}