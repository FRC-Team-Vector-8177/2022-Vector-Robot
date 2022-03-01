package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class testIntake extends CommandBase {
    boolean off = false;
    private Boolean forwardOpen = null;

    public void execute() {
        if (!off) {

        }
        if (Robot.m_oi.aIntakeButtonPressed() && !off) {
            forwardOpen = true;
        }
        if (Robot.m_oi.yIntakeButtonPressed() && !off) {
            forwardOpen = false;
        }
        if (forwardOpen != null && !forwardOpen && !off) {
            Robot.intake.openIntakeReverseSolenoid();
        } else if (forwardOpen != null && forwardOpen && !off) {
            Robot.intake.openIntakeForwardSolenoid();
        }

    }

    public void end() {
        Robot.intake.closeIntakeSolenoid();
        Robot.intake.closeCompressor();
    }
}
