package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="RedAutoFoundationTestNavid", group="Tests")
public class RedAutoFoundationTestNavid extends LinearOpMode{
//    double power=0.5;
    boolean blueAlliance = false;
    double power;
    HardwareSetup choppy = new HardwareSetup();
    @Override
    public void runOpMode() {
//        !blueAlliance


        choppy.init(hardwareMap, telemetry, false, true,blueAlliance);

//        choppy.setFoundationClipPower(-0.02);

        waitForStart();
//        choppy.shuffle(0.5, 500, !blueAlliance);
        choppy.moveForwBackEncoder(0.75, 8,true, false);
        choppy.rotateEncoder(0.5, 400,false, false);
        choppy.releaseCapstone();
        // moving to drop capstone
        choppy.moveForwBackEncoder(0.75,20,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.75,20,true,true);
        choppy.moveForwBackEncoder(0.25,4.5,true,true);
        //moving to grab the foundation.
        choppy.lowerFoundationClips();
        choppy.moveForwBackEncoder(0.5,20,true,false);
        choppy.moveForwBackEncoder(0.25, 2, true, false);
        choppy.raiseFoundationClips();
        //move the foundation
        choppy.moveForwBackEncoder(0.75,10,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.75,15,true,false);
        //obstruct the foundation
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(1,60,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,15.75,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,43.5,true,false);
        choppy.rotateEncoder(0.5, 400, false, false);
        choppy.moveForwBackEncoder(0.5, 35, true, false);
        //move the foundation back




        

    }
}
