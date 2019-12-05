package org.firstinspires.ftc.teamcode.test_code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareSetup;

@Autonomous(name="TestPlayground3", group="Tests")
@Disabled
public class TestPlayground3 extends LinearOpMode{
    double power=0.5;
    boolean redAlliance = false;

    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, false, false, true);

        waitForStart();
        choppy.moveForwBack(power,3000,true);
        choppy.lowerFoundationClips();
        sleep(1000);
        choppy.moveForwBack(power,1500, false);
        choppy.raiseFoundationClips();
        choppy.moveForwBack(power,1000,false);

        choppy.shuffle(power,1500,redAlliance);
        choppy.moveForwBack(power,2500,true);
        choppy.rotate90(true, 2000);
        choppy.shuffle(power,2000,redAlliance);
        choppy.moveForwBack(power,4000,true);


    }
}
