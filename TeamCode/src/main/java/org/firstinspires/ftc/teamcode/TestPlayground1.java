package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="TestPlayground1", group="Tests")
public class TestPlayground1 extends LinearOpMode{
    double power=0.5;
    HardwareSetup robot = new HardwareSetup();
    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry, true);

        waitForStart();
        robot.moveForwBack(power,20000,false);
        robot.rotate90(true,5000);
        robot.moveForwBack(power,10000,true);
        robot.LFoundationHook.setPower(1);
        robot.RFoundationHook.setPower(1);
        sleep(2000);
        robot.moveForwBack(power,5000,false);
        robot.shuffle(power,5000,true);
        robot.unlockFoundationClips();




    }
}
