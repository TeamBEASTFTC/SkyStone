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

@Autonomous(name="RedAutoFoundationTest", group="Tests")
public class RedAutoFoundationTest extends LinearOpMode{
//    double power=0.5;
    boolean blueAlliance = false;
    HardwareSetup choppy = new HardwareSetup();
    @Override
    public void runOpMode() {


        choppy.init(hardwareMap, telemetry, true, false);

        waitForStart();
        choppy.moveForwBack(0.5,3000, true);
        choppy.LFoundationHook.setPower(1);
        choppy.RFoundationHook.setPower(1);
        sleep(3000);
        choppy.moveForwBack(0.5, 2000,false);
        choppy.unlockFoundationClips();
        choppy.rotate90(blueAlliance,1000);
        choppy.moveForwBack(0.5, 1500, false);
        choppy.rotate90(blueAlliance,1000);
        choppy.moveForwBack(0.5, 4000, false);
        choppy.rotate90(blueAlliance, 1000);
        choppy.moveForwBack(0.5, 1000, false);
        choppy.rotate90(blueAlliance, 1000);
        choppy.moveForwBack(0.5,5000,false);
        choppy.moveForwBack(0.5, 1000, false);
        choppy.rotate90(blueAlliance,1000);
        choppy.moveForwBack(0.5, 4000, false);

    }
}
