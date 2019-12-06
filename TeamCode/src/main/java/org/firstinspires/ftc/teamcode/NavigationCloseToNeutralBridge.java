package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="NavigationCloseToNeutralBridge", group="Auto")
public class NavigationCloseToNeutralBridge extends LinearOpMode{
    //TO do:
    //Write a catch for if it gets stuck in using the encoders
    double power=0.5;
    //    boolean redAlliance = false;
    boolean blueAlliance = false;


    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, false, true,blueAlliance);



        waitForStart();
        choppy.moveForwBackEncoder(0.25,36, true, false);
        choppy.rotateEncoder(0.25,((400/90)*6),false, blueAlliance);
        choppy.shuffleEncoder(0.5,(24/0.8), true,!blueAlliance);
        choppy.rotateEncoder(0.5,390, false, blueAlliance);
        choppy.releaseCapstone();
    }
}
