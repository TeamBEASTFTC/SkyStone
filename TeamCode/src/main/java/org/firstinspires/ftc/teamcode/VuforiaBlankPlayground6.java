/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="VuforiaBlankPlayground6", group ="Test")

public class VuforiaBlankPlayground6 extends LinearOpMode {
    boolean blueAlliance = false;


    HardwareSetup choppy = new HardwareSetup();
    String inheritance_msg = "none";
    @Override
    public void runOpMode() {

        //initialisation code first
        choppy.init(hardwareMap, telemetry, true, true, blueAlliance);
        waitForStart();


//        boolean SkyStoneFound = false;
//        int loopCounter = 0;
//        choppy.targetsSkyStone.activate();
//        while (!(SkyStoneFound)){
//            //calling the computer vision now
//            String[] computerVisionResults = choppy.computerVisionRunning(choppy.allTrackables, blueAlliance);
//            //args:
//            //0 = targetVisible
//            //1 = yposisitonSkystone
//            //2 = xposisitonSkystone
//            //3 = xPosition value
//            //4 = yPosition value
//            telemetry.addData("Target Visible: ", computerVisionResults[0]);
//            telemetry.addData("yPos SkyStone: ", computerVisionResults[1]);
//            telemetry.addData("xPos SkyStone: ", computerVisionResults[2]);
//            telemetry.addData("xPos value: ", computerVisionResults[3]);
//            telemetry.addData("yPos value: ", computerVisionResults[4]);
//            telemetry.update();
//            sleep(4000);
//
//        }
//        choppy.targetsSkyStone.deactivate();
        CVCode();




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
//                choppy.moveForwBackEncoder(0.5, distance_block_width, true, false);
//                distance_to_gate += distance_block_width;
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
            sleep(500);
            SkyStoneFound = true;

        }else if ((computerVisionResults[1].equals("To Field Centre"))){
            telemetry.addLine("HERE! To field centre");
            telemetry.addData("moving: ", computerVisionResults[4]);
            telemetry.update();
            sleep(1000);
//                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[4]));
//            distance_to_center_of_stone = distance_block_width/2; try other again
            //+10 for error correction
//            choppy.moveForwBackEncoder(0.3, distance_to_center_of_stone, false, true);
//            distance_to_gate -= distance_to_center_of_stone;
            SkyStoneFound = false;//This may be set to true if the above is effective
            movedUsingVision = true;

        } else if (computerVisionResults[1].equals("To Field Border")){
            telemetry.addLine("HERE! To field border");
            telemetry.addData("moving: ", computerVisionResults[4]);
            telemetry.update();
            sleep(1000);

//                distance_to_center_of_stone = Math.abs(Double.parseDouble(computerVisionResults[4]));
//                distance_to_center_of_stone = distance_block_width/2; I recogn try the other one again

                //+10 for error correction
//                choppy.moveForwBackEncoder(0.3, distance_to_center_of_stone, false, false);
            telemetry.addLine("To Field border!");
//                choppy.telementryLineMessage("moving slightly forwards");
//             distance_to_gate += distance_to_center_of_stone;
            telemetry.update();
            sleep(500);
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
//                choppy.moveForwBackEncoder(0.5, distance_block_width, true, false);
//                distance_to_gate += distance_block_width;

                choppy.telementryLineMessage("Vuforia computer vision = false");
                sleep(1000);
                false_counter = 0;

                // if we are at the third block and the others have not been found then,
                // this block must be what we are looking for!
//                if (loopCounter >= 3) {
//                    SkyStoneFound = true;
//                }
            } else{
                choppy.telementryLineMessage("Checking CV again.");
                loopCounter -= 1; // nothing happened, robot did not move so do not count as a move

            }

        } else {
            // if for what ever reason something does not work, don't stop the robot... just keep trying!
//            choppy.moveForwBackEncoder(0.5,distance_block_width, true,false);
//            distance_to_gate += distance_to_center_of_stone;
            choppy.telementryLineMessage("Vuforia else statement");
            sleep(1000);

        }

    }
}

}

