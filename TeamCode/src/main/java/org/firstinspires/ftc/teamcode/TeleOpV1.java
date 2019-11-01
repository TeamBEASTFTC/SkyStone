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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;



@TeleOp(name = "TeleOpV1", group = "Linear Opmode")

public class TeleOpV1 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //Motor Decs
    //Naming motors
    static final String DriveTRName = "TR";
    static final String DriveTLName = "TL";
    static final String DriveBLName = "BL";
    static final String DriveBRName = "BR";
    static final String RotateCraneName = "Crane";

    //DcMotor drive declarations
    DcMotor driveTR;
    DcMotor driveTL;
    DcMotor driveBL;
    DcMotor driveBR;
    DcMotor rotateCrane; //-ve power makes it go down
//    rotateCrane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //variables that hold the current position of the DC Motors
    double positionTR;
    double positionTL;
    double positionBL;
    double positionBR;
    double positionCrane;

    double angle;
    double power;
    double turn;
    double pie = Math.PI;
    boolean dpad;

    int sector;

    static final String LFoundationHookName = "LFoundationHook";
    static final String RFoundationHookName = "RFoundationHook";
    static final String RsqueezerName = "Lsqueezer";
    static final String LsqueezerName = "Rsqueezer";

//    CRServo squeezer;
    CRServo LFoundationHook; //
    CRServo RFoundationHook;
    CRServo LSqueezer;
    CRServo RSqueezer;

    double servoPower = 0.0;
    double SqueezerServoPower = 0.0;
    double closing = 1;
    double opening = -1;
    double modifier = 1;
    double riseCrane = -0.75; //raises the crane
    double lowerCrane = 0.75; //lowers the crane

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        driveTR = hardwareMap.get(DcMotor.class, DriveTRName);
        driveTL = hardwareMap.get(DcMotor.class, DriveTLName);
        driveBL = hardwareMap.get(DcMotor.class, DriveBLName);
        driveBR = hardwareMap.get(DcMotor.class, DriveBRName);
        rotateCrane = hardwareMap.get(DcMotor.class, RotateCraneName);


        driveTL.setDirection(DcMotor.Direction.REVERSE);
        driveBL.setDirection(DcMotor.Direction.REVERSE);

        driveTR.setPower(0);
        driveTL.setPower(0);
        driveBL.setPower(0);
        driveBR.setPower(0);
        rotateCrane.setPower(0);

        // SERVOS
//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
        LFoundationHook = hardwareMap.get(CRServo.class, LFoundationHookName);
        RFoundationHook = hardwareMap.get(CRServo.class, RFoundationHookName);
        LSqueezer = hardwareMap.get(CRServo.class, LsqueezerName);
        RSqueezer = hardwareMap.get(CRServo.class, RsqueezerName);


//        squeezer.setPower(servoPower);
        LFoundationHook.setPower(servoPower);
        RFoundationHook.setPower(servoPower*-1);
        LSqueezer.setPower(SqueezerServoPower);
        RSqueezer.setPower(SqueezerServoPower*-1);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            //show elapsed time and wheel power
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Crane pos", rotateCrane.getCurrentPosition());
            try {
                telemetry.addData("Hook pos: ", LFoundationHook.getDirection());

            }catch(Exception e){
                telemetry.addData("error: ", e);
            }
            telemetry.update();

            //Drive input and output
            getInput();
            sendOutput();
        }}



    private void getInput() {
        double x = gamepad1.left_stick_x;
        telemetry.addData("x gamepad 1: ", x);
        telemetry.update();
        double y = gamepad1.left_stick_y;
        angle = Math.atan2(y, x); //Joah was here

        power = Math.sqrt(x * x + y * y) * 10 / 7;

        turn = -gamepad1.right_stick_x / 2;

        if (x > 0){
            if ((y/x) > 0 && (y/x) < 1){
                sector = 1;
            }
            else if ((x/y) > 0 && (x/y) < 1){
                sector = 2;
            }
            else if ((x/y) < 0 && (x/y) > -1){
                sector = 7;
            }
            else if ((y/x) < 0 && (y/x) > -1){
                sector = 8;
            }
        }
        else if (x<0){
            if ((y/x) > 0 && (y/x) < 1){
                sector = 5;
            }
            else if ((x/y) > 0 && (x/y) < 1){
                sector = 6;
            }
            else if ((x/y) < 0 && (x/y) > -1){
                sector = 3;
            }
            else if ((y/x) < 0 && (y/x) > -1){
                sector = 4;
            }
        }
        else if (x==0){
            if (y < 1) {
                sector = 10;
            }
            else if (y > 9){
                sector = 9;
            }
            else sector = 0;
        }
        else {
            sector = 0;
        }
    }

    private void sendOutput() {
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
        int directionCrane;




        double oneRotationTicks = 1440; //number of ticks in one revolution
        double dpadPower = 0.9;
        double driveIncrement = oneRotationTicks / 10; //tenth of a wheel rotation is the smallest movement increment


        positionTR = driveTR.getCurrentPosition();
        positionTL = driveTL.getCurrentPosition();
        positionBL = driveBL.getCurrentPosition();
        positionBR = driveBR.getCurrentPosition();

        positionCrane = rotateCrane.getCurrentPosition();

        //driving with the dpad
        if (gamepad1.dpad_up) {
            telemetry.addLine("gamepad1.dpad_up");
            telemetry.update();
            dpad = true;
            directionTR = 1;
            directionTL = 1;
            directionBL = 1;
            directionBR = 1;
        } else if (gamepad1.dpad_down) {
            telemetry.addLine("gamepad1.dpad_down");
            telemetry.update();
            dpad = true;
            directionTR = -1;
            directionTL = -1;
            directionBL = -1;
            directionBR = -1;
        } else if (gamepad1.dpad_left) {
            telemetry.addLine("gamepad1.dpad_left");
            telemetry.update();
            dpad = true;
            directionTR = 1;
            directionTL = -1;
            directionBL = 1;
            directionBR = -1;
        } else if (gamepad1.dpad_right) {
            telemetry.addLine("gamepad1.dpad_right");
            telemetry.update();
            dpad = true;
            directionTR = -1;
            directionTL = 1;
            directionBL = -1;
            directionBR = 1;
        } else if (gamepad1.right_bumper) {
//            driveTR.setPower(backTurnPower);
//            driveTL.setPower(turnPower);
//            driveBL.setPower(turnPower);
//            driveBR.setPower(backTurnPower);
                directionBR = -1;
                directionBL = 1;
                directionTR = -1;
                directionTL = 1;

        } else if (gamepad1.left_bumper){
//            driveTR.setPower(turnPower);
//            driveTL.setPower(backTurnPower);
//            driveBL.setPower(backTurnPower);
//            driveBR.setPower(turnPower);
                directionBR = 1;
                directionBL = -1;
                directionTR = 1;
                directionTL = -1;
            } else {
            dpad = false;
            directionTR = 0;
            directionTL = 0;
            directionBL = 0;
            directionBR = 0;
        }
        if (gamepad2.a){
            modifier = 0.5;
        } else if (gamepad2.b){
            modifier = 0.25;
        } else modifier = 1;



//        if (gamepad2.right_bumper){
//            //close servo
//            LFoundationHook.setDirection(-0.75);
//        } else if (gamepad2.left_bumper) {
//            LFoundationHook.setPosition(0.75);
//            //open servo
//        } else{
//        }

        if (gamepad2.y){
            //close servo
            servoPower = closing;
        } else if (gamepad2.x) {
            servoPower = opening;
            //open servo
        } else{
            servoPower = 0;
        }

        // Crane control
        if (gamepad2.dpad_up){
            directionCrane = 1;
        } else if (gamepad2.dpad_down){
            directionCrane = -1;
        } else{
            directionCrane = 0;
        }
        //squeezer control
        if (gamepad2.left_stick_button){
            SqueezerServoPower = 1;
        } else if (gamepad2.right_stick_button){
            SqueezerServoPower = -1;
        } else {
            SqueezerServoPower = 0;
        }

                //setting the motor powers
        if (dpad) {
            powerTR = directionTR * dpadPower;
            powerTL = directionTL * dpadPower;
            powerBL = directionBL * dpadPower;
            powerBR = directionBR * dpadPower;
        } else {
            /* joystick controls */
            // only takes the up and down motion of the joystick and not the horizontal
            dpad = false;
            if (sector == 1){
                powerTR = -1;
                powerTL = 1;
                powerBR = 1;
                powerBL = -1;

            } else if (sector == 2){
                powerTR = 1;
                powerTL = 1;
                powerBR = 1;
                powerBL = 1;
            } else if (sector == 3){
                powerTR = 1;
                powerTL = 1;
                powerBR = 1;
                powerBL = 1;
            } else if (sector == 4){
                powerTR = 1;
                powerTL = -1;
                powerBR = -1;
                powerBL = 1;
            } else if (sector == 5){
                powerTR = 1;
                powerTL = -1;
                powerBR = -1;
                powerBL = 1;
            } else if (sector == 6){
                powerTR = -1;
                powerTL = -1;
                powerBR = -1;
                powerBL = -1;
            } else if (sector == 7){
                powerTR = -1;
                powerTL = -1;
                powerBR = -1;
                powerBL = -1;
            } else if (sector == 8){
                powerTR = -1;
                powerTL = 1;
                powerBR = 1;
                powerBL = -1;
            } else if (sector == 9){
                powerTR = 1;
                powerTL = 1;
                powerBR = 1;
                powerBL = 1;
            } else if (sector == 10){
                powerTR = -1;
                powerTL = -1;
                powerBR = -1;
                powerBL = -1;
            } else {
                powerTR = 0;
                powerTL = 0;
                powerBR = 0;
                powerBL = 0;
            }
        }
                powerCrane = directionCrane * dpadPower;//was not initialised before, meaning it had no value,
                //The value was hidden inside the if statement


                if (turn != 0) {
                    powerTR += turn;
                    powerTL += turn;
                    powerBL += turn;
                    powerBR += turn;
                }


                driveTR.setPower(powerTR);
                driveTL.setPower(powerTL);
                driveBL.setPower(powerBL);
                driveBR.setPower(powerBR);

                rotateCrane.setPower(powerCrane);

                //servos
                LFoundationHook.setPower(servoPower);
                RFoundationHook.setPower(servoPower*-1);

                LSqueezer.setPower(SqueezerServoPower);
                RSqueezer.setPower(SqueezerServoPower*-1);

    }}