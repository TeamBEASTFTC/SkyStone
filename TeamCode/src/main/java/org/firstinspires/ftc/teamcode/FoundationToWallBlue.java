package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="FoundationToWallBlue", group="Auto")
public class FoundationToWallBlue extends LinearOpMode {

    boolean blueAlliance = true;


    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, false, true, blueAlliance);



        waitForStart();
        choppy.moveForwBackEncoder(0.5, 4, true, true);
        choppy.rotateEncoder(0.25, ((400 / 90) * 6), false, !blueAlliance);
        choppy.shuffleEncoder(0.75, (26 / 0.8), true, blueAlliance);
        choppy.rotateEncoder(0.25, ((400 / 90) * 6), false, blueAlliance);

        //moving to foundation
        choppy.moveForwBackEncoder(0.5,20,true,true);
        choppy.moveForwBackEncoder(0.25,9,true,true);

        //pulling foundation
        choppy.lowerFoundationClips();
        choppy.moveForwBackEncoder(0.2,31.75,true,false);
        choppy.raiseFoundationClips();
//        choppy.moveForwBackEncoder(0.5, 10, true, true);
        choppy.rotateEncoder(0.25, ((400 / 90) * 6), false, blueAlliance);

        choppy.shuffleEncoder(0.5, 30, true, !blueAlliance);
        choppy.rotateEncoder(0.25, ((400 / 90) * 4), false, blueAlliance);
        choppy.shuffleEncoder(0.5, 40, true, !blueAlliance);


        choppy.rotateEncoder(0.25, ((400 / 90) * 6), false, !blueAlliance);

        choppy.moveForwBackEncoder(0.5, 3, true, false);
//        choppy.rotateEncoder(0.5,390, false, blueAlliance);

//        choppy.releaseCapstone();
    }
}
