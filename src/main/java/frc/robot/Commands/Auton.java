package frc.robot.Commands;

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Robot;
import frc.robot.Subsystems.Elevator;
import frc.robot.Subsystems.Intake;
import edu.wpi.first.networktables.GenericEntry;

public class Auton {
  private final Intake m_Intake;
  private final Elevator m_Elevator;
  private final Robot m_Robot;

    
    private GenericEntry m_autonSelect;
  private long m_startTime;

public Auton(Intake intake, Elevator elevator, Robot robot) {
    m_Intake = intake;
    m_Elevator = elevator;
    m_Robot = robot;
}

public void SystemInit(){
     m_autonSelect = Shuffleboard.getTab("Auton Select")
        .add("Auton Choose", 0)
        .getEntry();
}

public void AutonInit(){
    m_startTime = System.currentTimeMillis();
}

public void AutonPeriodic(){
    int auton = (int) m_autonSelect.getInteger(1);
      if (auton == 1)
      {
          autonNineCrateFowardLeft();
      }
      else if (auton == 2)
      {
          autonNineCrateSidewaysLeft();
      }
       else if (auton == 3)
      {
          autonNineCrateFowardRight();
      }
        else if (auton == 4)
      {
          autonNineCrateSidewaysRight();
      }
        else if (auton == 5)
      {
          autonTestSpeed();
      }
      else {
      m_Robot.mecanumDrive_Cartesian(0, 0, 0);
      }
    }

    public void autonNineCrateSidewaysLeft()
    {
     if(System.currentTimeMillis() - m_startTime < 4000) {
          m_Robot.mecanumDrive_Cartesian(-1, -.1, 0);
      }
      else {
        m_Robot.mecanumDrive_Cartesian(0,0, 0);
      }
    }

    public void autonNineCrateFowardLeft()
    {
      if(System.currentTimeMillis() - m_startTime < 4000) {
          m_Robot.mecanumDrive_Cartesian(-0.1, -1, 0);
      }
      else {
        m_Robot.mecanumDrive_Cartesian(0,0, 0);
      }
    }
    public void autonNineCrateSidewaysRight()
    {
     if(System.currentTimeMillis() - m_startTime < 4000) {
          m_Robot.mecanumDrive_Cartesian(1, -.1, 0);
      }
      else {
        m_Robot.mecanumDrive_Cartesian(0,0, 0);
      }
    }

    public void autonNineCrateFowardRight()
    {
      if(System.currentTimeMillis() - m_startTime < 4000) {
          m_Robot.mecanumDrive_Cartesian(0.1, -1, 0);
      }
      else {
        m_Robot.mecanumDrive_Cartesian(0,0, 0);
      }
    }
    public void autonTestSpeed()
    {
      if(System.currentTimeMillis() - m_startTime < 1000) {
          m_Robot.mecanumDrive_Cartesian(0, 1, 0);
      }
      else {
        m_Robot.mecanumDrive_Cartesian(0,0, 0);
      }
    }

}

