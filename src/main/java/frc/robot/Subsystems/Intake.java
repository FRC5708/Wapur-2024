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
private GenericEntry m_twistCratePower;

private long m_intakeTime;

private final XboxController m_stick;

public Intake(XboxController stick){
    m_stick = stick;
}

public void IntakeInit(){
    m_inPower = Shuffleboard.getTab("Constants")
    .add("Intake In Power", .7)
    .getEntry();

    m_outPower = Shuffleboard.getTab("Constants")
    .add("Intake Out Power", .3)
    .getEntry();

    m_twistCratePower = Shuffleboard.getTab("Constants")
    .add("Twist Power", .2)
    .getEntry();
}

public void IntakePeriodic(){

if (m_stick.getRightBumper()){
    intakeIn();
}
else if (m_stick.getLeftBumper()){
    intakeOut();
}
else if (m_stick.getLeftTriggerAxis() > .5){
    crateGather();
}
else {
    crateIntakeReady();
}  
}

public void intakeIn(){
    double inPower = (double) m_inPower.getDouble(1);
    m_leftIntake.set(ControlMode.PercentOutput, -inPower);
    m_rightIntake.set(ControlMode.PercentOutput, inPower);
}
public void intakeOut(){
    double outPower = (double) m_outPower.getDouble(1);
    m_leftIntake.set(ControlMode.PercentOutput, outPower);
    m_rightIntake.set(ControlMode.PercentOutput, -outPower);
}
public void crateIntakeReady(){
    m_intakeTime = System.currentTimeMillis();
    m_leftIntake.set(ControlMode.PercentOutput, 0);
    m_rightIntake.set(ControlMode.PercentOutput, 0);
}
public void crateGather(){
    double twistPower = (double) m_twistCratePower.getDouble(1);
    if (System.currentTimeMillis() - m_intakeTime < 600){
    m_leftIntake.set(ControlMode.PercentOutput, -twistPower);
    m_rightIntake.set(ControlMode.PercentOutput, twistPower);
    }
    else if (System.currentTimeMillis() - m_intakeTime < 800){
    m_leftIntake.set(ControlMode.PercentOutput, twistPower);
    m_rightIntake.set(ControlMode.PercentOutput, twistPower);  
    }
    else {
    m_leftIntake.set(ControlMode.PercentOutput, -twistPower);
    m_rightIntake.set(ControlMode.PercentOutput, twistPower);
    m_intakeTime = System.currentTimeMillis();
    }
}
}