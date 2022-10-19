package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Robot extends TimedRobot {
  public enum IntakeState {
    STOWED_INACTIVE, // Intake is stowed and inactive
    DEPLOYED_ACTIVE_SUCK, // Intake is extended, active, and is sucking cargo in
    DEPLOYED_ACTIVE_SPIT, // Intake is extended, active, and is spitting cargo out
  }

  private IntakeState state;
  private XboxController controller;
  private Spark verticalRollerMotor;
  private Spark horizontalRollerMotor;
  private Spark deploymentMotor;
  private double suckSpeed;
  private double spitSpeed;
  private double inactiveSpeed;

  @Override
  public void robotInit() {
    // State / controller configuration
    state = IntakeState.STOWED_INACTIVE;
    controller = new XboxController(0);

    // Initializing motor controllers
    verticalRollerMotor = new Spark(0);
    horizontalRollerMotor = new Spark(0);
    deploymentMotor = new Spark(0);

    // Motor speeds
    suckSpeed = 1.0;
    spitSpeed = -0.3;
    inactiveSpeed = 0.0;
  }

  public void setState(IntakeState _state) {
    state = _state;
  }

  //deploy intake
  public void deploy(boolean spit, double deploymentMotorSpeed) {
    if (spit) {
      setState(IntakeState.DEPLOYED_ACTIVE_SPIT);

      verticalRollerMotor.set(spitSpeed);
      horizontalRollerMotor.set(spitSpeed);
    } else {
      setState(IntakeState.DEPLOYED_ACTIVE_SUCK);
      
      verticalRollerMotor.set(suckSpeed);
      horizontalRollerMotor.set(suckSpeed);
    }

    deploymentMotor.set(deploymentMotorSpeed);
  }

  // stow intake
  public void stow() {
    setState(IntakeState.STOWED_INACTIVE);
    
    verticalRollerMotor.set(inactiveSpeed);
    horizontalRollerMotor.set(inactiveSpeed);
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    // controls
    if (controller.getRightTriggerAxis() >= 0.0) {
      setState(IntakeState.DEPLOYED_ACTIVE_SUCK);
      deploy(false, controller.getRightTriggerAxis());
    } else if (controller.getLeftTriggerAxis() >= 0.0) {
      setState(IntakeState.DEPLOYED_ACTIVE_SPIT);
      deploy(true, -(controller.getLeftTriggerAxis()));
    } else if (controller.getRightTriggerAxis() == 0.0 & state == IntakeState.DEPLOYED_ACTIVE_SPIT || state == IntakeState.DEPLOYED_ACTIVE_SUCK) {
      setState(IntakeState.STOWED_INACTIVE);
      stow();
    }
  }
}
 