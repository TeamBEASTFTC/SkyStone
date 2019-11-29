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
        choppy.moveForwBackEncoder(0.5, 8,true, !blueAlliance);
        choppy.rotateEncoder(0.5, 400,false, false);
        choppy.moveForwBackEncoder(0.5, 2, true,true);
        choppy.releaseCapstone();
        // moving to drop capstone
        choppy.moveForwBackEncoder(0.5,16.75,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.25,24.5,true,true);
        //moving to grab the foundation.
        choppy.lowerFoundationClips();
        choppy.moveForwBackEncoder(0.25,22,true,false);
        choppy.raiseFoundationClips();
        choppy.moveForwBackEncoder(0.5,5,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,18,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,59,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,78.25,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,43.5,true,false);




        

    }
}
