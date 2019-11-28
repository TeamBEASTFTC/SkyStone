package org.firstinspires.ftc.teamcode.test_code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareSetup;

@Autonomous(name="EncoderTestOld", group="Tests")
public class EncoderTestOld extends LinearOpMode{
    double power=0.5;
    boolean redAlliance = false;

    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, false, true);

        waitForStart();
//        choppy.moveForwBack(0.5, 2000, false);
//        telemetry.addData("Moved: ", "TL: %d, TR: %d, BL: %d, BR: %d",
//                choppy.driveTL.getCurrentPosition(), choppy.driveTR.getCurrentPosition(), choppy.driveBL.getCurrentPosition(), choppy.driveBR.getCurrentPosition());
//        telemetry.update();
//        sleep(2000);
        choppy.moveForwBackEncoder(0.5, 1000, false, false);



    }
}
