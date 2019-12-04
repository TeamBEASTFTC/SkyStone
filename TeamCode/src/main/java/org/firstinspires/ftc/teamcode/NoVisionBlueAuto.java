package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="NoVisionBlueAuto", group="Tests")
public class NoVisionBlueAuto extends LinearOpMode{
    //TO do:
    //Write a catch for if it gets stuck in using the encoders
    double power=0.5;
//    boolean redAlliance = false;
    boolean blueAlliance = true;

    // computer vision
    int false_counter = 0; //counts the number of times vision not found

    // distances
    double robot_inch_distance = 15.75; // 15.75"
    double distance_to_blocks_inches = 55.0 - robot_inch_distance;
    double distance_block_width = 8;//8"
//    double distance_to_gate = 13.5; // starting distance to gate 13.5"
    double distance_to_gate = 19.5; // starting distance to gate 13.5"

    double distance_to_center_of_stone;

    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, true, true,true);

        waitForStart();
//        choppy.moveForwBack(0.5, 2000, false);
//        telemetry.addData("Moved: ", "TL: %d, TR: %d, BL: %d, BR: %d",
//                choppy.driveTL.getCurrentPosition(), choppy.driveTR.getCurrentPosition(), choppy.driveBL.getCurrentPosition(), choppy.driveBR.getCurrentPosition());
//        telemetry.update();
//        sleep(2000);
        //NOTE USING INCHES: 1 inch = 1000 distance unit
        // robot is 15.75inches long
        // move by 18.5"
        choppy.moveForwBackEncoder(0.5, 25.5, true, false); //move further forward 7 inches
        distance_to_blocks_inches -= 22;

        //rotate90 towards the back wall
        choppy.rotateEncoder(0.5, 400, false, !blueAlliance);


        //work your way towards the SkyStone
        //But without the autonomous CV!
        //So just move to the second last stone
//        choppy.moveForwBackEncoder(0.5,distance_block_width, true,false);
//        distance_to_gate += distance_to_center_of_stone;
        CVCode();


        // The robot is now lined up with the stone...well hopefully
        // Now we rotate to face it
        choppy.rotateEncoder(0.5, 400, false, blueAlliance);

        //prepare intake
//        choppy.grabStoneFlipperControl(false);
        choppy.setIntakeServoPos(0.4);
        // Move towards the block
        choppy.moveForwBackEncoder(0.5, distance_to_blocks_inches, true, false);

        // Grab the block
//        choppy.grabStoneFlipperControl(true);
        choppy.setIntakeServoPos(0.65);

        // Move back
        choppy.moveForwBackEncoder(0.5, distance_to_blocks_inches, true, true);

        // Rotate towards the gate
//        choppy.rotate90(blueAlliance, 1);
        choppy.rotateEncoder(0.5, 400, false, blueAlliance);

        // Move to the gate and a bit beyond
        telemetry.addData("distance to gate: ", distance_to_gate);
        telemetry.update();
        sleep(1000);
        choppy.moveForwBackEncoder(0.75, distance_to_gate+10, true, false);

        // Release the stone
        choppy.grabStoneFlipperControl(false);
        choppy.setIntakeServoPos(0.4);

        // Move back under the gate
        choppy.moveForwBackEncoder(0.5, 10, true, true);

        choppy.releaseCapstone();

        // DONE


    }

    private void CVCode(){
        boolean SkyStoneFound = false;
        boolean movedUsingVision = false;
        int loopCounter = 0;
        int false_counter = 0;
        String [] computerVisionResults = {"false", "", "", "", ""};
        while (!(SkyStoneFound)){
            loopCounter += 1;
            telemetry.addData("Loop counter: ", loopCounter);
            telemetry.update();

            // if we have moved with CV then we are not aligned as per our setup position, so just take where we are
            if (loopCounter >= 3 && (computerVisionResults[0].equals("false")) && movedUsingVision){
                choppy.telementryLineMessage("we are done here!");
                SkyStoneFound = true;
                // if we are at the third block, have not moved with CV and see no block just MOVE then grab it
            }else if (loopCounter >= 3 && (computerVisionResults[0].equals("false") && !movedUsingVision)){
                choppy.moveForwBackEncoder(0.5, distance_block_width, true, false);
                distance_to_gate += distance_block_width;
                choppy.telementryLineMessage("moving forwards last block");
                SkyStoneFound = true;
            }


            choppy.targetsSkyStone.activate();
            //calling the computer vision now
            sleep(1000);// wait a bit for it to look
            computerVisionResults = choppy.computerVisionRunning(choppy.allTrackables, blueAlliance);
            choppy.targetsSkyStone.deactivate();
            //if robot is not center
            if (computerVisionResults[1].equals("CENTRE, GRAB!!")){
                telemetry.addLine("HEEREE! centre");
                telemetry.update();
                sleep(250);
                SkyStoneFound = true;

            }else if ((computerVisionResults[1].equals("To Field Centre"))){
                telemetry.addLine("HERE! To field centre");
                telemetry.addData("moving: ", computerVisionResults[4]);
                telemetry.update();
                sleep(250);
                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[4]));
//            distance_to_center_of_stone = distance_block_width/2; try other again
                //+10 for error correction
            choppy.moveForwBackEncoder(0.3, distance_to_center_of_stone, false, true);
            distance_to_gate -= distance_to_center_of_stone;
                SkyStoneFound = false;//This may be set to true if the above is effective
                movedUsingVision = true;

            } else if (computerVisionResults[1].equals("To Field Border")){
                telemetry.addLine("HERE! To field border");
                telemetry.addData("moving: ", computerVisionResults[4]);
                telemetry.update();
                sleep(250);

                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[4]));
//                distance_to_center_of_stone = distance_block_width/2; I recogn try the other one again

                //+10 for error correction
                choppy.moveForwBackEncoder(0.3, distance_to_center_of_stone, false, false);
                telemetry.addLine("To Field border!");
//                choppy.telementryLineMessage("moving slightly forwards");
//             distance_to_gate += distance_to_center_of_stone;
                telemetry.update();
                sleep(250);
                SkyStoneFound = false; //if not working change to true
                movedUsingVision = true;
//                if (loopCounter == 3){
//                    SkyStoneFound = true;
//                }

            }else if (computerVisionResults[0].equals("false")) {
                false_counter += 1;
                if (false_counter == 2) {
                    //double check the program first
                    // move down a block distance!,
                choppy.moveForwBackEncoder(0.5, distance_block_width, true, false);
                distance_to_gate += distance_block_width;

                    choppy.telementryLineMessage("Vuforia computer vision = false");
                    sleep(250);
                    false_counter = 0;

                    // if we are at the third block and the others have not been found then,
                    // this block must be what we are looking for!
//                    if (loopCounter >= 3) {
//                        SkyStoneFound = true;
//                    }
                } else{
                    choppy.telementryLineMessage("Checking CV again.");
                    loopCounter -= 1; // nothing happened, robot did not move so do not count as a move

                }

            } else {
                // if for what ever reason something does not work, don't stop the robot... just keep trying!
            choppy.moveForwBackEncoder(0.5,distance_block_width, true,false);
            distance_to_gate += distance_to_center_of_stone;
                choppy.telementryLineMessage("Vuforia else statement");
                sleep(250);

            }

        }
    }
}
