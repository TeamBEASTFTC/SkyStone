package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="RedAutoFoundationTestNavid", group="Tests")
public class RedAutoFoundationTestNavid extends LinearOpMode{
//    double power=0.5;
    boolean blueAlliance = true;
    double power;
    HardwareSetup choppy = new HardwareSetup();
    @Override
    public void runOpMode() {
//        !blueAlliance


        choppy.init(hardwareMap, telemetry, true, true);

//        choppy.setFoundationClipPower(-0.02);

        waitForStart();
//        choppy.shuffle(0.5, 500, !blueAlliance);
        choppy.moveForwBackEncoder(0.5, -2, true, !blueAlliance);
        choppy.releaseCapstone();
        // moving to drop capstone
        choppy.moveForwBackEncoder(0.5,16.75,true,true);


        

    }
}
