package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareSetup;

@Autonomous(name="EncoderTest", group="Tests")
public class EncoderTest extends LinearOpMode {
    double power = 0.5;
    boolean redAlliance = false;

    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, false, true);


        waitForStart();
        choppy.rotateEncoder(0.5, 400, false, false);
//        choppy.rotateEncoder(0.5, 405, false, false);
//        sleep(1000);
//        choppy.rotateEncoder(0.5, 405, false, true);



    }
}

