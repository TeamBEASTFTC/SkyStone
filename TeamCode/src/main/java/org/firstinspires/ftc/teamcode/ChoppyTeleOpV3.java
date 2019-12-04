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
import com.qualcomm.robotcore.hardware.DcMotor;
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
    double LSqueezerClose = 0.56;
    double LSqueezerOpen = 0.4;

    double RSqueezerClose = 0.32;
    double RSqueezerOpen = 0.45; //open values good

    double servoPower = -0.1;
    double SqueezerServoPower = 0.0;
    double foundationPosition = 0;
    double SqueezerServoPosOpen = 0.7;
    double SqueezerServoPosClosed = 0.3;
    double SqueezerServoPos = 0;
    double LSqueezerServoPos = LSqueezerOpen;
    double RSqueezerServoPos = RSqueezerOpen;
    double SqueezerStartPos = 0; //assuming 0 is closed
    double closing = 1;
    double opening = 0;
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

    int stick_left;
    int moving_control;
    int intake_control;
    int speed_control;
    int dpad_control;
    int crane_control;
    int trigger_control;
    int bumper_control;







    double oneRotationTicks = 1440; //number of ticks in one revolution
    double dpadPower = 0.9;
    double driveIncrement = oneRotationTicks / 10; //tenth of a wheel rotation is the smallest movement increment


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        choppy.init(hardwareMap, telemetry, false, false, true);

        choppy.rotateCrane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        choppy.rotateCrane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);





        // Wait for the game to start (driver presses PLAY)
        waitForStart();
//        squeezer.setPower(servoPower);

        //setting foundation clip positions
        /*choppy.LFoundationHook.setPower(servoPower);
        choppy.RFoundationHook.setPower(servoPower*-1);*/
        /*
        choppy.LFoundationHook.setPosition(foundationPosition);
        choppy.RFoundationHook.setPosition(1-foundationPosition);
        */
        choppy.setFoundationClipPosition(foundationPosition);
//        choppy.setIntakeServoPos(0);

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
            getJoystickInput(gamepad1);
            getJoystickInput(gamepad2);
            gamepad_control(gamepad1);
            gamepad_control(gamepad2);
            gamepad_control_motors();
            motor_output();
//            sendOutput();
        //shuffling is rotating
            //shuffle right rotates right
            // the two classes are causing for it to be jumpy
            // foundation hook is too fast
            // setting teh motor speeds twice

        }}



    private void getJoystickInput(Gamepad gamepad) {
        double x = gamepad.left_stick_x;
        telemetry.addData("gamepad 1: ", x);
        telemetry.update();
        double y = -gamepad.left_stick_y;

        angle = Math.atan2(y, x); //Joah was here

//        Math.sqrt(x * x + y * y) * 10 / 7;

        turn = -gamepad.right_stick_x / 2;

        // To investigate teh ratios: https://www.geogebra.org/graphing/fcnh42zj

        if (x > 0){
            // Top Right of circle
            if ((y/x) >= 0 && (x/y) > (1/(Math.tan(pie/8))))
            {
                sector = 1;
            }

            else if (((x/y) < ((1/(Math.tan(pie/8))))) && ((x/y) >= (1/(Math.tan(3*pie/8))))){
                sector = 2;

            } else if ( (x/y) < (1/(Math.tan(3*pie/8))) && (x/y) >= (0)){
                sector = 3;
            }

            // Bottom Right of circle
            else if ((y/x) < 0 && (x/y) <= (-1/(Math.tan(pie/8)))){
                sector = 1;
            }else if ((x/y) > (-1/(Math.tan(pie/8))) && (x/y) <= (-1/(Math.tan(3*pie/8)))){
                sector = 8;

            } else if ((x/y) > (-1/(Math.tan(3*pie/8))) && (x/y) <= 0){
                sector = 7;
            }
        }
        else if (x<0){
            // Top Left of circle
            if ((x/y) <= 0 && (x/y) > (-1/(Math.tan(3*pie/8)))){
                sector = 3;
            }else if ((x/y) <= (-1/(Math.tan(3*pie/8))) && (x/y) > (-1/(Math.tan(pie/8)))){
                sector = 4;
            } else if ((x/y) <= (-1/(Math.tan(pie/8))) && (y/x) < 0 ){
                sector = 5;

            // Bottom Left of circle
            }else if ((y/x) >= 0 && (x/y) > (1/(Math.tan(pie/8)))){
                sector = 5;
            } else if ((x/y) <= (1/(Math.tan(pie/8))) && (x/y) > (1/(Math.tan(3*pie/8)))){
                sector = 6;
            } else if ((x/y) <= (1/(Math.tan(3*pie/8))) && (x/y) > 0){
                sector = 7;
            }
        }

        else {
            // The game_pad has not been activated
            sector = 0;
        }
    }
    private void gamepad_control(Gamepad gamepad) {



//        if (gamepad.a){
//            modifier = 0.5;
//        } else if (gamepad2.b){
//            modifier = 0.25;
//        } else modifier = 1;

        // changing the speed factor of the robot
        if (gamepad.x){
            choppy.telementryLineMessage("x");
            speed_control = gamepad.id;
            joystick = true;
            speedFactor = 0.2;
        } else if (gamepad.y){
            choppy.telementryLineMessage("y");

            speed_control = gamepad.id;
            joystick = true;
            speedFactor = 0.5;
        }  else {
            if (speed_control == gamepad.id){
                choppy.telementryLineMessage("speed factor none");
                speedFactor = 1;
                speed_control = 0;
            }
        }

        //foundation hooks
        if (gamepad.a){
            trigger_control = gamepad.id;
            joystick = true;
            //lower foundation
            foundationPosition = closing;
        } else if (gamepad.b) {
            trigger_control = gamepad.id;
            joystick = true;
            foundationPosition = opening; //FIX this actually closes
            //raise foundation
        } else{
            if (trigger_control == gamepad.id) {
            trigger_control = 0;
            }
        }

        //squeezer control
        if (gamepad.right_trigger > 0){

            intake_control = gamepad.id;
            joystick = true;
            SqueezerServoPos = SqueezerServoPosClosed;
        } else if (gamepad.left_trigger > 0){
            intake_control = gamepad.id;
            joystick = false;
            SqueezerServoPos = SqueezerServoPosOpen;
        } else{
            if (intake_control == gamepad.id){
                intake_control = 0;
            }

        }


        //  Crane control
        // If joystick is up, bring crane up!
        y_crane_value = -gamepad.right_stick_y;
        if (y_crane_value > 0){
            if (choppy.rotateCrane.getCurrentPosition() < 2450){
                crane_control = gamepad.id;
                joystick = true;
                directionCrane = 1;
            }

        } else if (y_crane_value < 0){
            if (choppy.rotateCrane.getCurrentPosition() > 100){
                crane_control = gamepad.id;
                joystick = true;
                directionCrane = -0.75; //slightly slower going down
            }
        } else{
            if (crane_control == gamepad.id){
                directionCrane = 0;
                crane_control = 0;
            }
        }





        powerCrane = directionCrane;//was not initialised before, meaning it had no value,
        //The value was hidden inside the if statement





    }

    private void gamepad_control_motors(){
        telemetry.addLine("GOT THROUGH THE METHOD!!");


//        telemetry.addData("controller: ", gamepad.id);


        positionTR = choppy.driveTR.getCurrentPosition();
        positionTL = choppy.driveTL.getCurrentPosition();
        positionBL = choppy.driveBL.getCurrentPosition();
        positionBR = choppy.driveBR.getCurrentPosition();

        positionCrane = choppy.rotateCrane.getCurrentPosition();

        boolean gamepad_2_movement = false;
        boolean gamepad_1_movement = false;

        //if gamepad one is moving the robot
        if (gamepad1.dpad_down || gamepad1.dpad_up || gamepad1.dpad_left || gamepad1.dpad_right ||
                gamepad1.right_bumper || gamepad1.left_bumper ||
                ((Math.abs(gamepad1.left_stick_x) > 0)) || ((Math.abs(gamepad1.left_stick_y)) > 0)) {
            gamepad_1_movement = true;

            // if gamepad 2 is moving robot
        } else if (gamepad2.dpad_down || gamepad2.dpad_up || gamepad2.dpad_left || gamepad2.dpad_right ||
                    gamepad2.right_bumper || gamepad2.left_bumper ||
                    ((Math.abs(gamepad2.left_stick_x) > 0)) || ((Math.abs(gamepad2.left_stick_y)) > 0)){
            gamepad_2_movement = true;
        }

        if (gamepad_1_movement && !gamepad_2_movement){
            motor_movement_control_method(gamepad1);
        } else if (gamepad_2_movement && ! gamepad_1_movement){
            motor_movement_control_method(gamepad2);
        } else{
            powerTR = 0;
            powerTL = 0;
            powerBR = 0;
            powerBL = 0;
        }


        //final change to speed depending on user change of it
        //eg. if user presses y it will reduce the speed till 0.25
        powerTR *= speedFactor;
        powerTL *= speedFactor;
        powerBR *= speedFactor;
        powerBL *= speedFactor;


    }

    private void motor_movement_control_method(Gamepad gamepad){
        telemetry.addLine("GOT TO METHOD 2");
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

        //setting the motor powers
        if (!joystick) {

//            moving_control= gamepad.id;

            telemetry.addLine("dpad driving");
            telemetry.update();
            powerTR = directionTR;
            powerTL = directionTL;
            powerBL = directionBL;
            powerBR = directionBR;
        } else if (joystick){
//            moving_control = gamepad.id;
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
                powerBR = 1;
                powerBL = 0;
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



    }

    private  void motor_output(){

        choppy.driveTR.setPower(powerTR);
        choppy.driveTL.setPower(powerTL);
        choppy.driveBL.setPower(powerBL);
        choppy.driveBR.setPower(powerBR);

        choppy.rotateCrane.setPower(powerCrane);


        //servos

        /*choppy.LFoundationHook.setPosition(SqueezerServoPostition);
        choppy.RFoundationHook.setPosition(SqueezerServoPostition);*/
        choppy.setFoundationClipPosition(foundationPosition);

/*        choppy.LFoundationHook.setPower(servoPower);
        choppy.RFoundationHook.setPower(servoPower*-1);*/
        choppy.setIntakeServoPos(SqueezerServoPos);
//        choppy.LSqueezer.setPosition(LSqueezerServoPos);
//        choppy.RSqueezer.setPosition(RSqueezerServoPos); //open values good


    }

}