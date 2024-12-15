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
private GenericEntry m_twistTowards;
private GenericEntry m_twistAway;
private GenericEntry m_twistTowardsTime;
private GenericEntry m_twistAwayTime;

private long m_intakeTime;

private final XboxController m_stick;

public Intake(XboxController stick){
    m_stick = stick;
}

public void IntakeInit(){
    m_inPower = Shuffleboard.getTab("Constants")
    .add("Intake In Power", 1)
    .getEntry();

    m_outPower = Shuffleboard.getTab("Constants")
    .add("Intake Out Power", .3)
    .getEntry();

    m_twistAway = Shuffleboard.getTab("Constants")
    .add("Twist Away Power", .6)
    .getEntry();
    m_twistTowards = Shuffleboard.getTab("Constants")
    .add("Twist Towards Power", 1)
    .getEntry();
    m_twistAwayTime = Shuffleboard.getTab("Constants")
    .add("Twist Away Timing", 6)
    .getEntry();
    m_twistTowardsTime = Shuffleboard.getTab("Constants")
    .add("Twist Towards Timing", 2)
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
    double twistPowerTowards = (double) m_twistTowards.getDouble(1);
    double twistPowerAway = (double) m_twistAway.getDouble(1);

    int twistTimeTowards = (int) m_twistTowardsTime.getInteger(1);
    int twistTimeAway = (int) m_twistAwayTime.getInteger(1);

    twistTimeTowards *= 100;
    twistTimeAway *= 100;
    twistTimeAway += twistTimeTowards;

    if (System.currentTimeMillis() - m_intakeTime < twistTimeTowards){
    m_leftIntake.set(ControlMode.PercentOutput, -twistPowerTowards);
    m_rightIntake.set(ControlMode.PercentOutput, twistPowerTowards);
    }
    else if (System.currentTimeMillis() - m_intakeTime < twistTimeAway){
    m_leftIntake.set(ControlMode.PercentOutput, twistPowerAway);
    m_rightIntake.set(ControlMode.PercentOutput, twistPowerAway);  
    }
    else {
    m_leftIntake.set(ControlMode.PercentOutput, -twistPowerTowards);
    m_rightIntake.set(ControlMode.PercentOutput, twistPowerTowards);
    m_intakeTime = System.currentTimeMillis();
    }
}
}