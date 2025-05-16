// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveWithJoysticks extends Command {

  private Drivetrain drivetrain;
  private XboxController
      xboxJoysticks; // I don't think we need this because XboxController is a static class
  private Joystick lJoy, rJoy;
  private boolean useXboxControl;
  public static double kJoystickSpeedCorr = 1.0; // from DriveTrainConstants
  private double lastJoystickLeftX = 0.0;
  private double lastJoystickLeftY = 0.0;
  private double lastJoystickRightX = 0;
  double angleCorrection = 0.0;
  double gyroAngle;

  /** Creates a new DriveWithJoysticks. */
  public DriveWithJoysticks(Drivetrain drive, XboxController xbox) {

    drivetrain = drive;
    xboxJoysticks = xbox;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    angleCorrection = 0.0;
    lastJoystickLeftX = xboxJoysticks.getLeftX();
    lastJoystickLeftY = xboxJoysticks.getLeftY();
    lastJoystickRightX = xboxJoysticks.getRightX();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Drive using xbox joystick values

    double speedCorrection = kJoystickSpeedCorr;

    drivetrain.drive(
        speedCorrection * xboxJoysticks.getLeftX(),
        speedCorrection * xboxJoysticks.getLeftY(),
        speedCorrection * xboxJoysticks.getRightX(),
        false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
