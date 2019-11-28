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
        choppy.setFoundationClipPower(-0.02);

        waitForStart();
//        choppy.shuffle(0.5, 500, !blueAlliance);
        choppy.moveForwBackEncoder(0.5, -2, true, !blueAlliance);
        choppy.releaseCapstone(););
        // moving to drop capstone
        choppy.moveForwBackEncoder(0.5,16.75,true,true);
        //dropping capstone
        choppy.releaseCapstone();
//        choppy.lowerFoundationClips();
//        choppy.raiseFoundationClips();
        choppy.setFoundationClipPower(-0.02);
        //reversing to centre of foundation
        choppy.moveForwBackEncoder(0.5,16.75,true,false);
        //turn so clips face foundation
//        choppy.rotate90(blueAlliance,1);
        choppy.rotateEncoder(0.5, 400, false, blueAlliance);
        //drive to foundation
        choppy.moveForwBackEncoder(0.5,29.5,true,true);
        //lower clips
        choppy.lowerFoundationClips();
        //drag foundation
        choppy.moveForwBackEncoder(0.25,27.5, true,false);
        //release foundation
        choppy.raiseFoundationClips();
        //move out of way of foundation
        choppy.moveForwBackEncoder(0.25,1,true,false);
        //shuffle to underneath bridge
        choppy.shuffle(0.25,3,true);
        
//        choppy.moveForwBackEncoder(0.5, 4.32, true, true);
//        choppy.rotateEncoder(0.5, 405, false, true); 90 degrees

    }
}
