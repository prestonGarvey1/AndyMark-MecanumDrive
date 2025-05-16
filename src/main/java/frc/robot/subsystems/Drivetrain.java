// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  // Define motor controllers
  private PWMSparkMax frontLeftMotor;
  private PWMSparkMax frontRightMotor;
  private PWMSparkMax backLeftMotor;
  private PWMSparkMax backRightMotor;
  private MecanumDrive mecanumDrive;

  // Define local variables
  private double gyroAngle;
  private double speedX, speedY, speedZ;

  /** Creates a new Drivetrain. */
  public Drivetrain() {

    // Initialize motor controllers
    frontLeftMotor = new PWMSparkMax(FRONT_LEFT_MOTOR_ID);
    frontRightMotor = new PWMSparkMax(FRONT_RIGHT_MOTOR_ID);
    backLeftMotor = new PWMSparkMax(BACK_LEFT_MOTOR_ID);
    backRightMotor = new PWMSparkMax(BACK_RIGHT_MOTOR_ID);

    // Invert back motors
    backLeftMotor.setInverted(true);
    backRightMotor.setInverted(true);

    // Initialize mecanum drive
    mecanumDrive = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
  }

  public void drive(double leftJoyX, double leftJoyY, double rightJoyX, boolean useGyro) {
    frontLeftMotor.set(0);
    frontRightMotor.set(0);
    backLeftMotor.set(0);
    backRightMotor.set(0);

    // Set properties of drive
    mecanumDrive.setSafetyEnabled(false);
    mecanumDrive.setMaxOutput(0.95);

    // Get joystick values and scale
    speedX = -leftJoyY * DRIVE_SPEED;
    speedY = leftJoyX * DRIVE_SPEED;
    speedZ = rightJoyX * DRIVE_SPEED;

    // If using the pi gyro board, mod the returned value by 360 to avoid a spinning robot
    if (useGyro) {

      // gyroAngle = Robot.driveAngle.getDouble(0) % 360.0;

      mecanumDrive.driveCartesian(speedY, speedX, speedZ);

    } else {

      mecanumDrive.driveCartesian(speedY, speedX, speedZ);
    }

    // SmartDashboard.putString("Gyro Angle", Double.toString(gyroAngle));
    // SmartDashboard.putString("Gyro Yaw", Double.toString(Robot.gyroYaw.getDouble(0)));

    // Replace current sensing with motor output values
    SmartDashboard.putNumber("Front Left Drive Output", frontLeftMotor.get());
    SmartDashboard.putNumber("Back Left Drive Output", backLeftMotor.get());
    SmartDashboard.putNumber("Front Right Drive Output", frontRightMotor.get());
    SmartDashboard.putNumber("Back Right Drive Output", backRightMotor.get());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
