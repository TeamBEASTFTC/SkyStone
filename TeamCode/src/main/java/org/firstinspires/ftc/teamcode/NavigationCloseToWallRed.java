package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareSetup;

@Autonomous(name="NavigationCloseToWallRed", group="Auto")
public class NavigationCloseToWallRed extends LinearOpMode {

    boolean blueAlliance = false;


    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, false, true, blueAlliance);



        waitForStart();
        choppy.moveForwBackEncoder(0.5, 4, true, false);
        choppy.rotateEncoder(0.25, ((400 / 90) * 6), false, !blueAlliance);
        choppy.shuffleEncoder(0.75, (24 / 0.8), true, blueAlliance);
        choppy.moveForwBackEncoder(0.5, 10, true, true);
//        choppy.rotateEncoder(0.5,390, false, blueAlliance);

//        choppy.releaseCapstone();
    }
}
