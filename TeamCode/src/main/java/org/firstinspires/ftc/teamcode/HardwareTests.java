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


        choppy.init(hardwareMap, telemetry, false, true, true);
//        choppy.setIntakeServoPos(0.5);
/*        choppy.LSqueezer.setPosition(1);
        choppy.RSqueezer.setPosition(0);
        choppy.telementryLineMessage("pos: 0");
        sleep(2000);
        choppy.LSqueezer.setPosition(0.75);
        choppy.RSqueezer.setPosition(0.25);
        choppy.telementryLineMessage("pos: 0.25");
        sleep(2000);
        choppy.LSqueezer.setPosition(0.5);
        choppy.RSqueezer.setPosition(0.5);
        choppy.telementryLineMessage("pos: 0.5");
        sleep(2000);
        choppy.LSqueezer.setPosition(0);
        choppy.RSqueezer.setPosition(1);
        choppy.telementryLineMessage("pos: 1");
        sleep(2000);*/
//        waitForStart();
//
//        choppy.LFoundationHook.setPower(0);
//        choppy.RFoundationHook.setPower(0);
//        choppy.LFoundationHook.setDirection();

//        choppy.setFoundationClipPower(-0.02);
        waitForStart();
        choppy.setIntakeServoPos(0);
//        choppy.setFoundationClipPosition(0);
        sleep(2000);
        choppy.setIntakeServoPos(0.4);
        sleep(2000);
        choppy.setIntakeServoPos(0.65);
//        choppy.setFoundationClipPosition(0.5);
        sleep(2000);
        /*choppy.setFoundationClipPosition(1);
//        choppy.LFoundationHook.setPosition(0);
//        choppy.RFoundationHook.setPosition(0.9);

        choppy.telementryLineMessage("pos: 0");
        sleep(2000);
//        choppy.LFoundationHook.setPosition(0.5);
//        choppy.RFoundationHook.setPosition(0.5);
        //1- position + 0.1
        choppy.setFoundationClipPosition(0.75);
        choppy.telementryLineMessage("pos 0.75");
        sleep(2000);
//        choppy.LFoundationHook.setPosition(0.6);
//        choppy.RFoundationHook.setPosition(0.4);
        choppy.setFoundationClipPosition(0.5);
        choppy.telementryLineMessage("pos 0.5");
        sleep(2000);
        choppy.setFoundationClipPosition(0.25);
//        choppy.LFoundationHook.setPosition(0.8);
//        choppy.RFoundationHook.setPosition(0.3);
        choppy.telementryLineMessage("pos 0.25");
        sleep(2000);
        choppy.setFoundationClipPosition(0);
//        choppy.LFoundationHook.setPosition(1);
//        choppy.RFoundationHook.setPosition(0);
        choppy.telementryLineMessage("pos 0");
        sleep(4000);*/

        //choppy.setFoundationClipPosition(0); raises foundation all the way up
        //choppy.setFoundationClipPosition(1); lowers the clips all teh way down

//        choppy.releaseCapstone();
////        choppy.shuffle(0.75, 2000, false);
//        choppy.rotateEncoder(0.25, ((400/90)*6), false, true);
//        choppy.shuffleEncoder(0.5, 1000, false, false);
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

