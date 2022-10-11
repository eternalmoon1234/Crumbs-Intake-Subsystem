package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Robot extends TimedRobot {
  public enum IntakeState {
    RETRACTED_INACTIVE, // Intake is stowed and inactive
    EXTENDED_INACTIVE, // Intake is extended and inactive 
    EXTENDED_ACTIVE_SUCK, // Intake is extended, active, and is sucking cargo in
    EXTENDED_ACTIVE_SPIT, // Intake is extended, active, and is spitting cargo out
    ERROR
  }

  private IntakeState state = IntakeState.RETRACTED_INACTIVE;
  private Joystick controller = new Joystick(0);
  private TalonFX intakeMotor = new TalonFX(0);
  private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 0);

  @Override
  public void robotInit() {}

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    System.out.println(controller.getRawButton(0));
    switch (state) {
      case RETRACTED_INACTIVE:
        intakeSolenoid.set(Value.kReverse);
        intakeMotor.set(ControlMode.PercentOutput, 0.00);
      case EXTENDED_INACTIVE:
        intakeSolenoid.set(Value.kForward);
        intakeMotor.set(ControlMode.PercentOutput, 0.00);
      case EXTENDED_ACTIVE_SUCK:
        intakeSolenoid.set(Value.kForward);
        intakeMotor.set(ControlMode.PercentOutput, 1.00);
      case EXTENDED_ACTIVE_SPIT:
        intakeSolenoid.set(Value.kForward); 
        intakeMotor.set(ControlMode.PercentOutput, -1.00);      
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
