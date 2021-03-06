// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveDirection extends CommandBase {
  private final double feet;
  double pidcalc;
  PIDController pid = new PIDController(1, 0.05, 0.01);

  /** Creates a new ArcadeDrive. */
  public MoveDirection(double feet) {
    this.feet = feet;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid.setTolerance(.1);

    Robot.driveTrain.resetEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidcalc = MathUtil.clamp(
        pid.calculate(Robot.driveTrain.encoderDegrees() / RobotMap.DriveTrain.FT_PER_ENCODER_DEGREE, feet),
        -Robot.m_oi.getAutoSpeed(), Robot.m_oi.getAutoSpeed());
    SmartDashboard.putNumber("Direction PID Output", pidcalc);
    SmartDashboard.putData("Direction PID", pid);
    SmartDashboard.putNumber("Current Position",
        Robot.driveTrain.encoderDegrees() / RobotMap.DriveTrain.FT_PER_ENCODER_DEGREE);
    SmartDashboard.putNumber("Goal Position", feet);
    Robot.driveTrain.motors.tankDrive(pidcalc, pidcalc);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.motors.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (pid.atSetpoint()) {

      Robot.driveTrain.resetEncoder();
      return true;
    }
    return false;
  }
}
