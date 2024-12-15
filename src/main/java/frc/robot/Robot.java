package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Commands.Auton;
import frc.robot.Subsystems.Elevator;
import frc.robot.Subsystems.Intake;

public class Robot extends TimedRobot {
  // The football robot drivetrain is a modified mecanum drive with
  // 4 independent gearboxes.
private GenericEntry m_drivePower;

  private final XboxController m_stick = new XboxController(0);
  
  private final VictorSPX m_frontLeftMotor = new VictorSPX(1);
  private final VictorSPX m_frontRightMotor = new VictorSPX(4); 
  private final VictorSPX m_backLeftMotor = new VictorSPX(3);
  private final VictorSPX m_backRightMotor = new VictorSPX(2);

  private final Intake m_Intake = new Intake(m_stick);
  private final Elevator m_Elevator = new Elevator(m_stick);
  private final Auton m_Auton = new Auton(m_Intake,m_Elevator,this);

  

  @Override //
    public void robotInit() {
    m_Intake.IntakeInit();
    m_Elevator.ElevatorInit();
    m_Auton.SystemInit();
    m_frontLeftMotor.set(ControlMode.PercentOutput, 0);
    m_frontRightMotor.set(ControlMode.PercentOutput, 0);
    m_backLeftMotor.set(ControlMode.PercentOutput, 0);
    m_backRightMotor.set(ControlMode.PercentOutput, 0);


    m_drivePower = Shuffleboard.getTab("Constants")
        .add("Drive Power", .3)
        .getEntry();
      
   
  }
  @Override //Setting the current System Time
  public void autonomousInit(){
    m_Auton.AutonInit();
  }

  @Override //Moving and other command shit
  public void autonomousPeriodic() {
      m_Auton.AutonPeriodic();
  }

  @Override //Safety so it should double check speed is set to 0
  public void teleopInit() {
    mecanumDrive_Cartesian(0, 0, 0);
  }

  @Override //Driving
  public void teleopPeriodic() {
    m_Intake.IntakePeriodic();
    m_Elevator.ElevatorPeriodic();
  mecanumDrive_Cartesian(Math.pow(m_stick.getLeftX(), 3), Math.pow(m_stick.getLeftY(), 3), Math.pow(m_stick.getRightX(), 3));
  }

  public void mecanumDrive_Cartesian(double x, double y, double rotation)
  {
    double drivePower = (double) m_drivePower.getDouble(1);

    double wheelSpeeds[] = new double[32];

  if (x <= .3 && y <= .3){
    x = x;
    y = y;
  }
  else {
    x *= drivePower;
    y *= drivePower;
  }
  
    //x, y, and rotation
    wheelSpeeds[1] = x + y + -rotation;
    wheelSpeeds[2] = -x + -y + -rotation;
    wheelSpeeds[3] = -x + y + -rotation;
    wheelSpeeds[4] = x + -y + -rotation;


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
}
