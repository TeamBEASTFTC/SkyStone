/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "ChoppyTeleOpV3", group = "Linear Opmode")

public class ChoppyTeleOpV3 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    HardwareSetup choppy = new HardwareSetup();




    //variables that hold the current position of the DC Motors
    double positionTR;
    double positionTL;
    double positionBL;
    double positionBR;
    double positionCrane;

   //servo positions
   double LSqueezerOpen = 0.1;
    double RSqueezerOpen = 0.5; //open values good
    double LSqueezerClose = 0.3;
    double RSqueezerClose = 0.1;

    double servoPower = -0.1;
    double SqueezerServoPower = 0.0;
    double SqueezerServoPosOpen = 0.7;
    double SqueezerServoPosClosed = 0.3;
    double LSqueezerServoPos = LSqueezerOpen;
    double RSqueezerServoPos = RSqueezerOpen;
    double SqueezerStartPos = 0; //assuming 0 is closed
    double closing = 1;
    double opening = -1;
    double modifier = 1;
    double riseCrane = -0.75; //raises the crane
    double lowerCrane = 0.75; //lowers the crane*/
    double cranePower;
    double speedFactor =1; //allow for chqanges in the speed of the robot

    double angle;
    double power;
    double turn;
    double pie = Math.PI;
    double root_3 = Math.sqrt(3);

    boolean dpad;
    int sector;
    boolean _Turn;
    boolean joystick;
    double y_crane_value;

    //power variables
    double powerTR;
    double powerTL;
    double powerBL;
    double powerBR;

//        double turnPower = 0.75;
//        double backTurnPower = turnPower * -1;

    double powerCrane;

    int directionTR;
    int directionTL;
    int directionBL;
    int directionBR;
    double directionCrane;




    double oneRotationTicks = 1440; //number of ticks in one revolution
    double dpadPower = 0.9;
    double driveIncrement = oneRotationTicks / 10; //tenth of a wheel rotation is the smallest movement increment


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        choppy.init(hardwareMap, telemetry, false, false);





        // Wait for the game to start (driver presses PLAY)
        waitForStart();
//        squeezer.setPower(servoPower);
        choppy.LFoundationHook.setPower(servoPower);
        choppy.RFoundationHook.setPower(servoPower*-1);
        choppy.LSqueezer.setPosition(LSqueezerOpen);
       choppy. RSqueezer.setPosition(RSqueezerOpen);

        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {




            //show elapsed time and wheel power
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Crane pos", choppy.rotateCrane.getCurrentPosition());
            telemetry.addData("Moved: ", "TL: %d, TR: %d, BL: %d, BR: %d",
                    choppy.driveTL.getCurrentPosition(),
                    choppy.driveTR.getCurrentPosition(),
                    choppy.driveBL.getCurrentPosition(),
                    choppy.driveBR.getCurrentPosition());

            try {
                telemetry.addData("Hook pos: ", choppy.LFoundationHook.getDirection());

            }catch(Exception e){
                telemetry.addData("error: ", e);
            }
            telemetry.update();

            //Drive input and output
            getInput(gamepad1);
            getInput(gamepad2);
            gamepad_control(gamepad1);
            gamepad_control(gamepad2);
//            sendOutput();
        }}



    private void getInput(Gamepad gamepad) {
        double x = -gamepad.left_stick_x;
        telemetry.addData("gamepad 1: ", x);
        telemetry.update();
        double y = -gamepad.left_stick_y;

        angle = Math.atan2(y, x); //Joah was here

//        Math.sqrt(x * x + y * y) * 10 / 7;

        turn = -gamepad.right_stick_x / 2;

        // To investigate teh ratios: https://www.geogebra.org/graphing/fcnh42zj

        if (x > 0){
            // Top Right of circle
            if ((y/x) >= 0 && (x/y) > (root_3/1)){
                sector = 1;
            }

            else if ((x/y) > (1/root_3) && (x/y) <= (root_3/1)){
                sector = 2;

            } else if ( (x/y) > 0 && (x/y) <= (1/root_3)){
                sector = 3;
            }

            // Bottom Right of circle
            else if ((y/x) < 0 && (x/y) <= (-root_3/1)){
                sector = 12;
            }else if ((x/y) > (-root_3/1) && (x/y) <= (-1/root_3)){
                sector = 11;

            } else if ((x/y) > (-1/root_3) && (x/y) <= 0){
                sector = 10;
            }
        }
        else if (x<0){
            // Top Left of circle
            if ((x/y) <= 0 && (x/y) > (-1/root_3)){
                sector = 4;
            }else if ((x/y) <= (-1/root_3) && (x/y) > (-root_3/1)){
                sector = 5;
            } else if ((x/y) <= (-root_3/1) && (y/x) < 0 ){
                sector = 6;

            // Bottom Left of circle
            }else if ((y/x) >= 0 && (x/y) > (root_3/1)){
                sector = 7;
            } else if ((x/y) <= (root_3/1) && (x/y) > (1/root_3)){
                sector = 8;
            } else if ((x/y) <= (1/root_3) && (x/y) > 0){
                sector = 9;
            }
        }

        else {
            // The game_pad has not been activated
            sector = 0;
        }
    }
    private void gamepad_control(Gamepad gamepad) {


        positionTR = choppy.driveTR.getCurrentPosition();
        positionTL = choppy.driveTL.getCurrentPosition();
        positionBL = choppy.driveBL.getCurrentPosition();
        positionBR = choppy.driveBR.getCurrentPosition();

        positionCrane = choppy.rotateCrane.getCurrentPosition();

        //driving with the dpad
        if (gamepad.dpad_up) {
            telemetry.addLine("gamepad.dpad_up");
            telemetry.update();
            dpad = true;
            joystick = false;
            directionTR = 1;
            directionTL = 1;
            directionBL = 1;
            directionBR = 1;
        } else if (gamepad.dpad_down) {
            telemetry.addLine("gamepad1.dpad_down");
            telemetry.update();
            dpad = true;
            joystick = false;
            directionTR = -1;
            directionTL = -1;
            directionBL = -1;
            directionBR = -1;
        } else if (gamepad.dpad_left) {
            telemetry.addLine("gamepad.dpad_left");
            telemetry.update();
            dpad = true;
            joystick = false;
            directionTR = 1;
            directionTL = -1;
            directionBL = 1;
            directionBR = -1;
        } else if (gamepad.dpad_right) {
            telemetry.addLine("gamepad.dpad_right");
            telemetry.update();
            dpad = true;
            joystick = false;
            directionTR = -1;
            directionTL = 1;
            directionBL = -1;
            directionBR = 1;
        } else if (gamepad.right_bumper) {
            _Turn = true;
            telemetry.addLine("right bumper");
            telemetry.update();
            joystick = false;

            directionBR = -1;
            directionBL = 1;
            directionTR = -1;
            directionTL = 1;

        } else if (gamepad.left_bumper){
            _Turn = true;
            telemetry.addLine("left bumper");
            telemetry.update();
            joystick = false;

            directionBR = 1;
            directionBL = -1;
            directionTR = 1;
            directionTL = -1;

        } else {
            _Turn = false;
            dpad  = false;
            joystick = true;
            directionTR = 0;
            directionTL = 0;
            directionBL = 0;
            directionBR = 0;
        }
//        if (gamepad.a){
//            modifier = 0.5;
//        } else if (gamepad2.b){
//            modifier = 0.25;
//        } else modifier = 1;

        // changing the speed factor of the robot
        if (gamepad.x){
            speedFactor = 0.25;
        } else if (gamepad.y){
            speedFactor = 0.5;
        }  else {
            speedFactor = 1;
        }

        //foundation hooks
        if (gamepad.left_trigger > 0){
            //lower foundation
            servoPower = closing;
        } else if (gamepad.right_trigger > 0) {
            servoPower = opening; //FIX this actually cloess
            //raise foundation
        } else{
            servoPower = 0;
        }

        //squeezer control
        if (gamepad.a){
            LSqueezerServoPos = LSqueezerClose; //closes left
            RSqueezerServoPos = RSqueezerClose; //closes right
        } else if (gamepad.b){
            LSqueezerServoPos = LSqueezerOpen; //opens left
            RSqueezerServoPos = RSqueezerOpen; //opens right
        }


        //  Crane control
        // If joystick is up, bring crane up!
        y_crane_value = -gamepad.right_stick_y;
        if (y_crane_value > 0){
            directionCrane = 1;

        } else if (y_crane_value < 0){
            directionCrane = -0.75; //slightly slower going down
        } else{
            directionCrane = 0;
        }

        powerBL = 0;
        powerTR = 0;
        powerBR =0;
        powerTL = 0;
        //setting the motor powers
        if (!joystick) {
            telemetry.addLine("dpad driving");
            telemetry.update();
            powerTR = directionTR * dpadPower;
            powerTL = directionTL * dpadPower;
            powerBL = directionBL * dpadPower;
            powerBR = directionBR * dpadPower;
        } else if (joystick){
            /* joystick controls */
            // only takes the up and down motion of the joystick and not the horizontal
            dpad = false;

            if (sector == 1) {
                choppy.telementryLineMessage("Sector 1: Shuffling right");
                powerTR = -1;
                powerTL = 1;
                powerBR = 1;
                powerBL = -1;

            } else if (sector == 2) {
                choppy.telementryLineMessage("Sector 2: Diagonal top right");
                powerTR = 0;
                powerTL = 1;
                powerBR = 0;
                powerBL = 1;
            } else if (sector == 3) {
                choppy.telementryLineMessage("Sector 3: Forwards");
                powerTR = 1;
                powerTL = 1;
                powerBR = 1;
                powerBL = 1;
            } else if (sector == 4) {
                choppy.telementryLineMessage("Sector 4: Forwards");
                powerTR = 1;
                powerTL = 1;
                powerBR = 1;
                powerBL = 1;
            } else if (sector == 5) {
                choppy.telementryLineMessage("Sector 5: Diagonal top left");
                powerTR = 1;
                powerTL = 0;
                powerBR = 0;
                powerBL = 1;
            } else if (sector == 6) {
                choppy.telementryLineMessage("Sector 6: Shuffling left");
                powerTR = 1;
                powerTL = -1;
                powerBR = -1;
                powerBL = 1;
            } else if (sector == 7) {
                choppy.telementryLineMessage("Sector 6: Shuffling left");
                powerTR = 1;
                powerTL = -1;
                powerBR = -1;
                powerBL = 1;
            } else if (sector == 8) {
                choppy.telementryLineMessage("Sector 8: Diagonal bottom left");
                powerTR = 0;
                powerTL = -1;
                powerBR = -1;
                powerBL = 0;
            } else if (sector == 9) {
                choppy.telementryLineMessage("Sector 9: Backwards");
                powerTR = -1;
                powerTL = -1;
                powerBR = -1;
                powerBL = -1;
            } else if (sector == 10) {
                choppy.telementryLineMessage("Sector 10: Backwards");
                powerTR = -1;
                powerTL = -1;
                powerBR = -1;
                powerBL = -1;
            } else if (sector == 11) {
                choppy.telementryLineMessage("Sector 11: Diagonal bottom right");
                powerTR = -1;
                powerTL = 0;
                powerBR = 0;
                powerBL = -1;
            } else if (sector == 12) {
                choppy.telementryLineMessage("Sector 12: Shuffling right");
                powerTR = -1;
                powerTL = 1;
                powerBR = 1;
                powerBL = -1;
            } else if (sector == 0){
                choppy.telementryLineMessage("Sector 0: Off");
                powerBL = 0;
                powerBR = 0;
                powerTR = 0;
                powerBL = 0;
            } else {
                choppy.telementryLineMessage("Sector None: Off");
                powerTR = 0;
                powerTL = 0;
                powerBR = 0;
                powerBL = 0;
            }

        }

        powerCrane = directionCrane * dpadPower;//was not initialised before, meaning it had no value,
        //The value was hidden inside the if statement

        //final change to speed depending on user change of it
        //eg. if user presses y it will reduce the speed till 0.25
        powerTR *= speedFactor;
        powerTL *= speedFactor;
        powerBR *= speedFactor;
        powerBL *= speedFactor;



        choppy.driveTR.setPower(powerTR);
        choppy.driveTL.setPower(powerTL);
        choppy.driveBL.setPower(powerBL);
        choppy.driveBR.setPower(powerBR);

        choppy.rotateCrane.setPower(powerCrane);


        //servos
        choppy.LFoundationHook.setPower(servoPower);
        choppy.RFoundationHook.setPower(servoPower*-1);

        choppy.LSqueezer.setPosition(LSqueezerServoPos);
        choppy.RSqueezer.setPosition(RSqueezerServoPos); //open values good


    }
}