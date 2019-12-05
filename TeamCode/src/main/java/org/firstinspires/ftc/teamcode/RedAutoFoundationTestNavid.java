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
        choppy.moveForwBackEncoder(0.5, 8,true, false);
        choppy.rotateEncoder(0.5, 400,false, false);
        choppy.releaseCapstone();
        // moving to drop capstone

        choppy.moveForwBackEncoder(0.5,35,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,20,true,true);
        choppy.moveForwBackEncoder(0.25,4.5,true,true);
        //moving to grab the foundation.

        choppy.lowerFoundationClips();
        choppy.moveForwBackEncoder(0.5,12,true,false);
        choppy.raiseFoundationClips();
        //move the foundation

        choppy.moveForwBackEncoder(0.5,10,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,26,true,false);
        //obstruct the foundation ready to move it back

        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,35,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,20,true,false);
        choppy.rotateEncoder(0.5,400,false,false);
        choppy.moveForwBackEncoder(0.5,35,true,false);
        //push foundation

        choppy.moveForwBackEncoder(0.5,35,true,true);
        choppy.rotateEncoder(0.5, 600, false, false);
        choppy.moveForwBackEncoder(0.5, 35, true, false);
        //move to get navigating points




        

    }
}
