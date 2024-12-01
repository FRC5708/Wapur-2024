package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.GenericEntry;

public class Robot extends TimedRobot {
  // The football robot drivetrain is a modified mecanum drive with
  // 4 independent gearboxes.

  private final XboxController m_stick = new XboxController(0);
  
  private final VictorSPX m_frontLeftMotor = new VictorSPX(1);
  private final VictorSPX m_frontRightMotor = new VictorSPX(4); 
  private final VictorSPX m_backLeftMotor = new VictorSPX(3);
  private final VictorSPX m_backRightMotor = new VictorSPX(2);
  private GenericEntry m_autonSelect;
  private long m_startTime;



  @Override //
     public void robotInit() {
    m_frontLeftMotor.set(ControlMode.PercentOutput, 0);
    m_frontRightMotor.set(ControlMode.PercentOutput, 0);
    m_backLeftMotor.set(ControlMode.PercentOutput, 0);
    m_backRightMotor.set(ControlMode.PercentOutput, 0);

    m_autonSelect = Shuffleboard.getTab("Auton Select")
        .add("Auton Choose", 0)
        .getEntry();
  }
  @Override //Setting the current System Time
  public void autonomousInit(){
    m_startTime = System.currentTimeMillis();
  }

  @Override //Moving and other command shit
  public void autonomousPeriodic() {
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
      else {
      mecanumDrive_Cartesian(0, 0, 0);
      }
  }

  @Override //Safety so it should double check speed is set to 0
  public void teleopInit() {
    mecanumDrive_Cartesian(0, 0, 0);
  }

  @Override //Driving
  public void teleopPeriodic() {
  mecanumDrive_Cartesian(Math.pow(m_stick.getLeftX(), 3), Math.pow(m_stick.getLeftY(), 3), Math.pow(m_stick.getRightX(), 3));
  }

public void mecanumDrive_Cartesian(double x, double y, double rotation)
{
    double wheelSpeeds[] = new double[32];

  //x, y, and rotation
    wheelSpeeds[1] = -x + y + -rotation;
    wheelSpeeds[2] = x + -y + -rotation;
    wheelSpeeds[3] = x + y + -rotation;
    wheelSpeeds[4] = -x + -y + -rotation;


   normalize(wheelSpeeds);

    m_frontLeftMotor.set(ControlMode.PercentOutput, wheelSpeeds[1]);
    m_frontRightMotor.set(ControlMode.PercentOutput, wheelSpeeds[4]);
    m_backLeftMotor.set(ControlMode.PercentOutput, wheelSpeeds[3]);
    m_backRightMotor.set(ControlMode.PercentOutput, wheelSpeeds[2]);
}   //mecanumDrive_Cartesian

private void normalize(double[] wheelSpeeds)
{
    double maxMagnitude = Math.abs(wheelSpeeds[0]);

    for (int i = 1; i < wheelSpeeds.length; i++)
    {
        double magnitude = Math.abs(wheelSpeeds[i]);

        if (magnitude > maxMagnitude)
        {
            maxMagnitude = magnitude;
        }
    }

    if (maxMagnitude > 1.0)
    {
        for (int i = 0; i < wheelSpeeds.length; i++)
        {
            wheelSpeeds[i] /= maxMagnitude;
        }
    }
}
    

    public void autonNineCrateSidewaysLeft()
    {
     if(System.currentTimeMillis() - m_startTime < 4000) {
          mecanumDrive_Cartesian(-1, -.1, 0);
      }
      else {
        mecanumDrive_Cartesian(0,0, 0);
      }
    }

    public void autonNineCrateFowardLeft()
    {
      if(System.currentTimeMillis() - m_startTime < 4000) {
          mecanumDrive_Cartesian(-0.2, -1, 0);
      }
      else {
        mecanumDrive_Cartesian(0,0, 0);
      }
    }
    public void autonNineCrateSidewaysRight()
    {
     if(System.currentTimeMillis() - m_startTime < 4000) {
          mecanumDrive_Cartesian(1, -.1, 0);
      }
      else {
        mecanumDrive_Cartesian(0,0, 0);
      }
    }

    public void autonNineCrateFowardRight()
    {
      if(System.currentTimeMillis() - m_startTime < 4000) {
          mecanumDrive_Cartesian(0.1, -1, 0);
      }
      else {
        mecanumDrive_Cartesian(0,0, 0);
      }
    }

}
