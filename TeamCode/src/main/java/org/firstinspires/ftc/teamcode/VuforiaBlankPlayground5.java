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

@Autonomous(name="VuforiaBlankPlayground5", group ="Test")

public class VuforiaBlankPlayground5 extends LinearOpMode {
    boolean blueAlliance = false;


    HardwareSetup choppy = new HardwareSetup();
    String inheritance_msg = "none";
    @Override
    public void runOpMode() {

        //initialisation code first
        choppy.init(hardwareMap, telemetry, true, true, blueAlliance);
        choppy.telementryLineMessage("Message:");
        sleep(2000);
        telemetry.addData("From inheritance: ", this.inheritance_msg);
        telemetry.update();
        sleep(4000);
        waitForStart();


        boolean SkyStoneFound = false;
        int loopCounter = 0;
        choppy.targetsSkyStone.activate();
        while (!(SkyStoneFound)){
            //calling the computer vision now
            String[] computerVisionResults = choppy.computerVisionRunning(choppy.allTrackables, blueAlliance);
            //args:
            //0 = targetVisible
            //1 = yposisitonSkystone
            //2 = xposisitonSkystone
            //3 = xPosition value
            //4 = yPosition value
            telemetry.addData("Target Visible: ", computerVisionResults[0]);
            telemetry.addData("yPos SkyStone: ", computerVisionResults[1]);
            telemetry.addData("xPos SkyStone: ", computerVisionResults[2]);
            telemetry.addData("xPos value: ", computerVisionResults[3]);
            telemetry.addData("yPos value: ", computerVisionResults[4]);
            telemetry.update();
            sleep(4000);

        }
        choppy.targetsSkyStone.deactivate();




    }

}

