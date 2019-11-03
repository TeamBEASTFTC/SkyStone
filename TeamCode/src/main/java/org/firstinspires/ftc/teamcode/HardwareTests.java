package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="HardwareTest", group="Tests")
public class HardwareTests extends LinearOpMode {


    HardwareSetup robot = new HardwareSetup();
    @Override
    public void runOpMode() {


        robot.init(hardwareMap, telemetry, true);
        waitForStart();
        robot.telementryLineMessage("Success we can call functions");
        sleep(1000);


        //sample vuforia code
        /*boolean SkyStoneCaptured = false;
        int loopCounter = 0;
        while (!(SkyStoneCaptured)){
            loopCounter += 1;
            telemetry.addData("Loop counter: ", loopCounter);
            telemetry.update();

            robot.targetsSkyStone.activate();
            //calling the computer vision now
            String[] computerVisionResults = robot.computerVisionRunning(robot.allTrackables);
            robot.targetsSkyStone.deactivate();
            //if robot is not center
            if (computerVisionResults[1].equals("CENTRE, GRAB!!")){
                telemetry.addLine("HEEREE! centre");
                telemetry.update();
                sleep(500);
                SkyStoneCaptured = true;

            }else if ((computerVisionResults[1].equals("To Field Centre"))){
                telemetry.addLine("HERE! To field centre");
                telemetry.update();
                sleep(2000);
//                shuffle(0.5, (moveAcrossOneBlockTime/2), BlueAlliance);
                SkyStoneCaptured = true;

            } else if (computerVisionResults[1].equals("To Field Border")){
                telemetry.addLine("HERE! To field border");
                telemetry.update();
                sleep(2000);
                // if this is not the first loop then we consider this more carefully
                //we shuffle a tiny bit for adjustment's sake
                if (loopCounter >= 1){
//                    shuffle(0.5,(moveAcrossOneBlockTime/2), !BlueAlliance);// We want to go towards to side again
                    telemetry.addLine("To Field border!");
                    telemetry.update();
                    sleep(500);
                    SkyStoneCaptured = true;
                }
            }else if (computerVisionResults[0].equals("false")) {
                ////shuffle the side of the field we are on,
                //eg on left side, means we shuffle left :)
//                shuffle(0.5, moveAcrossOneBlockTime, BlueAlliance);
                robot.telementryLineMessage("Vuforia computer vision = false");
                sleep(1000);
            } else {
                // if for what ever reason something does not work, don't stop the robot...
                robot.telementryLineMessage("Vuforia else statement");
                sleep(1000);
//                shuffle(0.5,(moveAcrossOneBlockTime/4), BlueAlliance);// We want to go towards to side again

            }

        }*/

    }
}

