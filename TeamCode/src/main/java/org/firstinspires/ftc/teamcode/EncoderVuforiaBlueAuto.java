package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="EncoderVuforiaBlueAuto", group="Tests")
public class EncoderVuforiaBlueAuto extends LinearOpMode{
    double power=0.5;
//    boolean redAlliance = false;
    boolean blueAlliance = false;

    // distances
    double robot_inch_distance = 15.75; // 15.75"
    double distance_to_blocks_inches = 48.0 - robot_inch_distance;
    double distance_block_width = 8;//8"
//    double distance_to_gate = 13.5; // starting distance to gate 13.5"
    double distance_to_gate = 13.5; // starting distance to gate 13.5"

    double distance_to_center_of_stone;

    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, true, true, blueAlliance);

        waitForStart();
//        choppy.moveForwBack(0.5, 2000, false);
//        telemetry.addData("Moved: ", "TL: %d, TR: %d, BL: %d, BR: %d",
//                choppy.driveTL.getCurrentPosition(), choppy.driveTR.getCurrentPosition(), choppy.driveBL.getCurrentPosition(), choppy.driveBR.getCurrentPosition());
//        telemetry.update();
//        sleep(2000);
        //NOTE USING INCHES: 1 inch = 1000 distance unit
        // robot is 15.75inches long
        // move by 18.5"
        distance_to_blocks_inches -= 18.5;
        choppy.moveForwBackEncoder(0.5, distance_to_blocks_inches, true, false);

        //rotate90 towards the back wall
        choppy.rotateEncoder(0.5, 400, false, blueAlliance);


        //work your way towards the SkyStone
        boolean SkyStoneFound = false;
        int loopCounter = 0;
        while (!(SkyStoneFound)){
            loopCounter += 1;
            telemetry.addData("Loop counter: ", loopCounter);
            telemetry.update();

            choppy.targetsSkyStone.activate();
            //calling the computer vision now
            String[] computerVisionResults = choppy.computerVisionRunning(choppy.allTrackables);
            choppy.targetsSkyStone.deactivate();


            //if robot is not center
            if (computerVisionResults[1].equals("CENTRE, GRAB!!")){
                telemetry.addLine("HEEREE! centre");
                telemetry.update();
                sleep(500);
                SkyStoneFound = true;

            }else if ((computerVisionResults[1].equals("To Field Centre"))){
                telemetry.addLine("HERE! To field centre");
                telemetry.update();
                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[3])) + 10;//+10 for error correction
                choppy.moveForwBackEncoder(0.3, distance_to_center_of_stone, false, true);
                distance_to_gate -= distance_to_center_of_stone;
                SkyStoneFound = false;//This may be set to true if the above is effective

            } else if (computerVisionResults[1].equals("To Field Border")){
                telemetry.addLine("HERE! To field border");
                telemetry.update();
                sleep(2000);
                // if this is not the first loop then we consider this more carefully
                //we shuffle a tiny bit for adjustment's sake
                if (loopCounter >= 1){
                    distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[3])) + 10;//+10 for error correction
                    choppy.moveForwBackEncoder(0.3, distance_to_center_of_stone, false, true);                    telemetry.addLine("To Field border!");
                    distance_to_gate += distance_to_center_of_stone;
                    telemetry.update();
                    sleep(500);
                    SkyStoneFound = false;
                }

            }else if (computerVisionResults[0].equals("false")) {
                // move down a block distance!,
                choppy.moveForwBackEncoder(0.5,distance_block_width, true,false);
                distance_to_gate += distance_to_center_of_stone;
                choppy.telementryLineMessage("Vuforia computer vision = false");
                sleep(1000);
            } else {
                // if for what ever reason something does not work, don't stop the robot... just keep trying!
                choppy.moveForwBackEncoder(0.5,distance_block_width, true,false);
                distance_to_gate += distance_to_center_of_stone;
                choppy.telementryLineMessage("Vuforia else statement");
                sleep(1000);

            }

        }

        // The robot is now lined up with the stone
        // Now we rotate to face it
        choppy.rotateEncoder(0.5, 400, false, blueAlliance);

        // Move towards the block
        choppy.moveForwBackEncoder(0.5, distance_to_blocks_inches, true, false);

        // Grab the block
        choppy.grabStoneFlipperControl(true);

        // Move back
        choppy.moveForwBackEncoder(0.5, distance_to_blocks_inches, true, true);

        // Rotate towards the gate
//        choppy.rotate90(blueAlliance, 1);
        choppy.rotateEncoder(0.5, 400, false, true);

        // Move to the gate and a bit beyond
        choppy.moveForwBackEncoder(0.75, distance_to_gate+10, true, false);

        // Release the stone
        choppy.grabStoneFlipperControl(false);

        // Move back under the gate
        choppy.moveForwBackEncoder(0.5, 10, true, true);

        // DONE


    }
}
