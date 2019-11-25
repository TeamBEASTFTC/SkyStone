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
import org.firstinspires.ftc.teamcode.HardwareSetup;

@Autonomous(name="HardwareTest", group="Tests")
public class HardwareTests extends LinearOpMode {


    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {


        choppy.init(hardwareMap, telemetry, false, true);
//
//        choppy.LFoundationHook.setPower(0);
//        choppy.RFoundationHook.setPower(0);
//        choppy.LFoundationHook.setDirection();
        choppy.setFoundationClipPower(-0.02);
        waitForStart();


//        choppy.releaseCapstone();
//        choppy.shuffle(0.75, 2000, false);
        choppy.rotateEncoder(0.25, ((400/90)*6), false, true);
        choppy.shuffleEncoder(0.5, 1000, false, false);
        /*
        Test:
        x: 850
        y: 90
        tan: 6deg

        x: 840
        y: 96
        6.5

        x: 840
        y: 25
        1

        x: 850
        y: 91
        6

        x: 848
        y: 75
        5deg

         */
    }
}

