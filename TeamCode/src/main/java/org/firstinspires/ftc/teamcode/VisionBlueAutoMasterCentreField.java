package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="VisionBlueAutoMasterCentreField", group="Tests")
public class VisionBlueAutoMasterCentreField extends LinearOpMode{
    //TO do:
    //Write a catch for if it gets stuck in using the encoders
    double power=0.5;
//    boolean redAlliance = false;
    boolean blueAlliance = true;

    // computer vision
    int false_counter = 0; //counts the number of times vision not found
    int loopCounter = 0;
    boolean movedUsingVision = false;
    boolean SkyStoneFound = false;



    // distances
    double robot_inch_distance = 15.75; // 15.75"
    double distance_to_blocks_inches = 55.0 - robot_inch_distance;
    double distance_block_width = 8;//8"
    double horizontal_distance_to_gate = robot_inch_distance-6;
//    double distance_to_gate = 13.5; // starting distance to gate 13.5"
    double distance_to_gate = 33.5; // starting distance to gate 13.5"
    double distance_to_wall = distance_to_blocks_inches - 7;

    double distance_to_center_of_stone;

    HardwareSetup choppy = new HardwareSetup();


    @Override
    public void runOpMode() {
        choppy.init(hardwareMap, telemetry, true, true,blueAlliance);

        waitForStart();
//        choppy.moveForwBack(0.5, 2000, false);
//        telemetry.addData("Moved: ", "TL: %d, TR: %d, BL: %d, BR: %d",
//                choppy.driveTL.getCurrentPosition(), choppy.driveTR.getCurrentPosition(), choppy.driveBL.getCurrentPosition(), choppy.driveBR.getCurrentPosition());
//        telemetry.update();
//        sleep(2000);
        //NOTE USING INCHES: 1 inch = 1000 distance unit
        // robot is 15.75inches long
        // move by 18.5"
        choppy.moveForwBackEncoder(0.65, 23, true, false); //move further forward 7 inches
        distance_to_blocks_inches -= 23;

        //rotate90 towards the block
        choppy.rotateEncoder(0.25, 395, false, blueAlliance);


        //work your way towards the SkyStone
        //But without the autonomous CV!
        //So just move to the second last stone
//        choppy.moveForwBackEncoder(0.5,distance_block_width, true,false);
//        distance_to_gate += distance_to_center_of_stone;
        CVCode();
        if (loopCounter > 1 && !movedUsingVision){
            // if it is not the first block
//            choppy.moveForwBackEncoder(0.5, distance_block_width/3, true, false);
        }


        // The robot is now lined up with the stone...well hopefully
        // Now we rotate to face it
        choppy.rotateEncoder(0.25, 393, false, !blueAlliance);

        //prepare intake
//        choppy.grabStoneFlipperControl(false);
        choppy.setIntakeServoPos(0.4);
        // Move towards the block
        choppy.moveForwBackEncoder(0.75, distance_to_blocks_inches, true, false);


        // Grab the block
//        choppy.grabStoneFlipperControl(true);
        choppy.setIntakeServoPos(0.65);
        choppy.moveCrane(200, 0.7, true);//  lifting crane so it does not drag

        // Move back to pass through gate
        choppy.moveForwBackEncoder(0.65, horizontal_distance_to_gate, true, true);
        //a bit of space to rotate
//        choppy.moveForwBackEncoder(0.5, 3, true, false);

        // Rotate towards the gate
//        choppy.rotate90(blueAlliance, 1);
        choppy.rotateEncoder(0.25, 395, false, !blueAlliance);




        // Move to the gate and a bit beyond
        telemetry.addData("distance to gate: ", distance_to_gate);
        telemetry.update();
//        sleep(500);
        choppy.moveForwBackEncoder(1, 20, true, false);
        distance_to_gate -= 20;
        //release da capstone
        choppy.releaseCapstone();
        choppy.moveForwBackEncoder(0.8, distance_to_gate, true, false);
        //passing further than gate

        // Release the stone
        choppy.grabStoneFlipperControl(false);
        choppy.setIntakeServoPos(0.4);

        // Move back under the gate
        choppy.moveForwBackEncoder(0.5, 10, true, true);

        choppy.moveCrane(0, 0.5, false);//  lifting crane so it does not drag



        // DONE


    }

    private void CVCode(){
        String [] computerVisionResults = {"false", "", "", "", ""};
        while (!(SkyStoneFound)){
            loopCounter += 1;
            telemetry.addData("Loop counter: ", loopCounter);
            telemetry.update();




            choppy.targetsSkyStone.activate();
            //calling the computer vision now
            sleep(250);// wait a bit for it to look
            computerVisionResults = choppy.computerVisionRunning(choppy.allTrackables, blueAlliance);
            choppy.targetsSkyStone.deactivate();
            //if robot is not center
            if (computerVisionResults[1].equals("CENTRE, GRAB!!")){
                telemetry.addLine("HEEREE! centre");
                telemetry.update();
                sleep(10);
                SkyStoneFound = true;

            }else if ((computerVisionResults[1].equals("To Field Centre"))){
                telemetry.addLine("HERE! To field centre");
                telemetry.addData("moving: ", computerVisionResults[4]);
                telemetry.update();
                sleep(10);
                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[4])) - 10;
//            distance_to_center_of_stone = distance_block_width/2; try other again
                //+10 for error correction
            choppy.moveForwBackEncoder(0.5, distance_to_center_of_stone, false, true);
            distance_to_gate -= distance_to_center_of_stone;
                SkyStoneFound = true;//This may be set to true if the above is ineffective
                movedUsingVision = true;

            } else if (computerVisionResults[1].equals("To Field Border")){
                telemetry.addLine("HERE! To field border");
                telemetry.addData("moving: ", computerVisionResults[4]);
                telemetry.update();
                sleep(10);

                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[4])) - 10;
//                distance_to_center_of_stone = distance_block_width/2; I recogn try the other one again

                //+10 for error correction
                choppy.moveForwBackEncoder(0.5, distance_to_center_of_stone, false, false);
                telemetry.addLine("To Field border!");
//                choppy.telementryLineMessage("moving slightly forwards");
             distance_to_gate += distance_to_center_of_stone;
                telemetry.update();
                sleep(10);
                SkyStoneFound = true; //if not working change to true
                movedUsingVision = true;
//                if (loopCounter == 3){
//                    SkyStoneFound = true;
//                }

            }else if (computerVisionResults[0].equals("false")) {
                false_counter += 1;
                if (false_counter == 2) {
                    //double check the program first
                    // move down a block distance!,


                    choppy.telementryLineMessage("Vuforia computer vision = false");
                    sleep(10);
                    false_counter = 0;
                    // if we have moved with CV then we are not aligned as per our setup position, so just take where we are
                    if (loopCounter >= 3 && movedUsingVision){
                        choppy.telementryLineMessage("we are done here!");
                        SkyStoneFound = true;
                        // if we are at the third block, have not moved with CV and see no block just MOVE then grab it
                    }else if (loopCounter >= 3 && !movedUsingVision){
//                        choppy.moveForwBackEncoder(0.5, distance_block_width, true, true);
//                        distance_to_gate -= distance_block_width;
                        choppy.telementryLineMessage("moving back last block");
                        SkyStoneFound = true;
                    } else{
                        choppy.moveForwBackEncoder(0.65, distance_block_width, true, false);
                        distance_to_gate += distance_block_width;
                    }

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
            choppy.moveForwBackEncoder(0.65,distance_block_width, true,false);
            distance_to_gate += distance_to_center_of_stone;
                choppy.telementryLineMessage("Vuforia else statement");
                sleep(10);

            }

        }
    }
}
