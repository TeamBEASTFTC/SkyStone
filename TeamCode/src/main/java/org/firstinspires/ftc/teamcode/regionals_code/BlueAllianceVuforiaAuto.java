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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

/**
 * This 2019-2020 OpMode illustrates the basics of using the Vuforia localizer to determine
 * positioning and orientation of robot on the SKYSTONE FTC field.
 * The code is structured as a LinearOpMode
 *
 * When images are located, Vuforia is able to determine the position and orientation of the
 * image relative to the camera.  This sample code then combines that information with a
 * knowledge of where the target images are on the field, to determine the location of the camera.
 *
 * From the Audience perspective, the Red Alliance station is on the right and the
 * Blue Alliance Station is on the left.

 * Eight perimeter targets are distributed evenly around the four perimeter walls
 * Four Bridge targets are located on the bridge uprights.
 * Refer to the Field Setup manual for more specific location details
 *
 * A final calculation then uses the location of the camera on the robot to determine the
 * robot's location and orientation on the field.
 *
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  skystone/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */


@Autonomous(name="BlueAllianceVuforiaAuto", group ="Concept")
public class BlueAllianceVuforiaAuto extends LinearOpMode {
    // Personal variables
    private String wallSide = "RIGHT";
    //whether turns are done clockwise or anticlockwise
    private boolean BlueAlliance = true;
    public String[] args = {"false", "", "", "", ""};

    private ElapsedTime computerVisionTime = new ElapsedTime();

    public boolean vuforiaInitialisation;

    private boolean SkySkoneCaptured = false;

    private int loopCounter = 0;

    //Positioning constants
    private double yPosCentreBoundaryRight = 30.5;
    private double yPosCentreBoundaryLeft = -30.5;


    // Motor configurations
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

    //Servos declaration
    static final String LFoundationHookName = "LFoundationHook";
    static final String RFoundationHookName = "RFoundationHook";
    static final String RsqueezerName = "Lsqueezer";
    static final String LsqueezerName = "Rsqueezer";
//    static final String squeezerName = "squeezer";

    //    Servos;
    CRServo LFoundationHook; //
    CRServo RFoundationHook;
    Servo LSqueezer;
    Servo RSqueezer;

    double angle;
    double power;
    double turn;
    // double pi = Math.PI;



    //Power var
    double servoPower = 0.0;
    double SqueezerServoPower = 0.0;
    double SqueezerServoPos = 0.1; //assuming 0 is closed
    double backwardsPower = -1.0;
    double rotationPower = 0.35; //allows for one 90 turn in 1s
    double modifier = 1;
    double riseCrane = -0.75; //raises the crane
    double lowerCrane = 0.75; //lowers the crane

    //movement timings
    int moveTowardsBlockTime = 17500;
    int moveAcrossOneBlockTime = 406;
    int moveToBlockTime = 605;
    int timeToGate = 3624;

    //servo positions
    double squeezerOpenPos = 0.3;
    double squeezerClosePos = 0;
    int craneStartUpPosition = 1000;



    // IMPORTANT:  For Phone Camera, set 1) the camera source and 2) the orientation, based on how your phone is mounted:
    // 1) Camera Source.  Valid choices are:  BACK (behind screen) or FRONT (selfie side)
    // 2) Phone Orientation. Choices are: PHONE_IS_PORTRAIT = true (portrait) or PHONE_IS_PORTRAIT = false (landscape)
    //
    // NOTE: If you are running on a CONTROL HUB, with only one USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    //
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = true;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AWmIb+//////AAABmblMTQ4kPUWfpxeAz3OzBzZLWvuZ1L/XI33F/Y3bohEHoMGgk8hUPhH2vv9tHLIo/EMJKY4+WYZiy7MaoNz4yYJ6jfA9q+uDkQynDOKfB2ji9kyqnMLsOmsgPRzqVQ5EhPIADoW5CFCNMLmPejwjiz0b2bWhmxa1l7Bx9eQPhTVbhqBjQrkykMkzCwLKvX2QxgST8rkQGgOFR8CJDWGVD5WptQnr07YH3UFLdk2MSuEaUGC7HNy4gX49AYFeIH56WZJZwt8hjvHtPKwV5wzsPsgNokra0qbRcmAZsCaztZG1Rx3abHyEG1NwoQaDCK57YX7Xo8fbY5E7yPGTglI4E/eeZjfCfMtxmZKZ1WJ88yz8";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;
    private Object OwnallTrackables;
//    private Object allTrackables;

    @Override
    public void runOpMode() {
        //initialisation code first
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

        //setting up the crane
        //set to 1750 ticks
        // max 2986.66 ticks

        rotateCrane.setPower(1);
        sleep(600);
        rotateCrane.setPower(0);
        sleep(2000);
//        rotateCrane.setPower(-0.5);
//        sleep(750);
//        rotateCrane.setPower(0);
        //750ms going down





//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
        // SERVOS
//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
        LFoundationHook = hardwareMap.get(CRServo.class, LFoundationHookName);
        RFoundationHook = hardwareMap.get(CRServo.class, RFoundationHookName);
        LSqueezer = hardwareMap.get(Servo.class, LsqueezerName);
        RSqueezer = hardwareMap.get(Servo.class, RsqueezerName);
//        squeezer.setPower(servoPower);

        //start
        checkComputerVision();
        telemetry.addLine("Moving to stone!");
        telemetry.update();
        sleep(500);
        moveForwBack(0.5, moveToBlockTime, false);
        grabSkyStone();
        moveForwBack(0.5, moveToBlockTime, true);
        //rotate clockwise if on left side, anti if on right - when starting,
        // blue alliance is on the right side
        rotate90(BlueAlliance);
        moveForwBack(0.5, (timeToGate - (moveAcrossOneBlockTime * loopCounter)), false);
        telemetry.addLine("complete");
        telemetry.update();

    }

    //Function to be used to move the robot forwards or backwards
    public void moveForwBack(double power, int time, boolean back) {

        if (back){
            power = power * -1;
        }
        driveTR.setPower(power);
        driveTL.setPower(power);
        driveBL.setPower(power);
        driveBR.setPower(power);
        sleep(time);
        driveTR.setPower(0);
        driveTL.setPower(0);
        driveBL.setPower(0);
        driveBR.setPower(0);
    }
    public void shuffle(double power, int time, boolean blueAlliance) {
        if (!blueAlliance){
            // shuffling left
            driveTR.setPower(power);
            driveTL.setPower(power* -1);
            driveBL.setPower(power);
            driveBR.setPower(power * -1);
        } else{
            // shuffling right
            driveTR.setPower(power * -1);
            driveTL.setPower(power);
            driveBL.setPower(power * -1);
            driveBR.setPower(power);
        }

        sleep(time);
        driveTR.setPower(0);
        driveTL.setPower(0);
        driveBL.setPower(0);
        driveBR.setPower(0);
    }
    public void rotate90(boolean clockwise) {
        int time = 1000;

        if (!(clockwise)){
            telemetry.addLine("Rotating 90 Anti-Clockwise!");
            telemetry.update();
            backwardsPower = rotationPower * -1;
            driveTR.setPower(rotationPower);
            driveTL.setPower(backwardsPower);
            driveBL.setPower(backwardsPower);
            driveBR.setPower(rotationPower);
        } else{
            telemetry.addLine("Rotating 90 Clockwise!");
            telemetry.update();
            backwardsPower = rotationPower * -1;
            driveTR.setPower(backwardsPower);
            driveTL.setPower(rotationPower);
            driveBL.setPower(rotationPower);
            driveBR.setPower(backwardsPower);

        }
        sleep(time);
        driveTR.setPower(0);
        driveTL.setPower(0);
        driveBL.setPower(0);
        driveBR.setPower(0);


    }

    // To do later: use parameters to set it, not necesasry due to only 1 use currently
    public void grabSkyStone() {
        telemetry.addLine("Grabing SkyStone");
        telemetry.update();
        //lowering the crane again
        //go back to 0
////        rotateCrane.setTargetPosition(encoderValue);
//        rotateCrane.setTargetPosition(0);
//        rotateCrane.setPower(0.5);
//        while (rotateCrane.isBusy()){
//
//        }
        rotateCrane.setPower(-0.5);
        sleep(750);
        rotateCrane.setPower(0);

        // assuming 0 is closed for both servos
        OpenCloseSqueezers(0, true);

    }

    //close the Squeezers on the intake
    //Just tell us how much you want to open/close by and then wether you want to open or close
    public void OpenCloseSqueezers(double position, boolean close){
        //if we want to close the squeezers, set the servo power to + "ve"
        if (close){
            SqueezerServoPos = position;
        } else{
            // in case we give a position larger than 0.5 - a safe limit
            if (position > 0.5){
                telemetry.addLine("Error settting Servo position. Check the position value.");
                telemetry.update();
                SqueezerServoPos = squeezerOpenPos;
            } else {
                SqueezerServoPos = 0.5 - position;
            }
        }
        LSqueezer.setPosition(SqueezerServoPos);
        RSqueezer.setPosition(1-SqueezerServoPos);

        telemetry.addData("SqueezerServoPos: ", SqueezerServoPos);
        telemetry.update();
        sleep(500);
//        RSqueezer.setPosition(SqueezerServoPower);
//    	LSqueezer.setPosition(SqueezerServoPower*-1);

    }

    //String[] because we are returning an array of values
    public void checkComputerVision() {
        LFoundationHook.setPower(servoPower);
        RFoundationHook.setPower(servoPower*-1);
        LSqueezer.setPosition(SqueezerServoPos);
        RSqueezer.setPosition(1-SqueezerServoPos);

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");


        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 270;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 0;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 2.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = -80;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:
        //CHECK HERE TO RUN VUFORIA ON INIT
        telemetry.addLine("Status: Initialised");
        telemetry.update();

        waitForStart();
        moveForwBack(0.5, moveTowardsBlockTime, false);

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.
        while (!(SkySkoneCaptured)){
            loopCounter += 1;
            telemetry.addData("Loop counter: ", loopCounter);
            telemetry.update();

            targetsSkyStone.activate();
            //calling the computer vision now
            String[] computerVisionResults = computerVisionRunning(allTrackables);
            targetsSkyStone.deactivate();
            //if robot is not center
            if (computerVisionResults[1].equals("CENTRE, GRAB!!")){
                telemetry.addLine("HEEREE! centre");
                telemetry.update();
                sleep(500);
                SkySkoneCaptured = true;

            }else if ((computerVisionResults[1].equals("To Field Centre"))){
                telemetry.addLine("HERE! To field centre");
                telemetry.update();
                sleep(5000);
                shuffle(0.5, (moveAcrossOneBlockTime/2), BlueAlliance);
                SkySkoneCaptured = true;

            } else if (computerVisionResults[1].equals("To Field Border")){
                telemetry.addLine("HERE! To field border");
                telemetry.update();
                sleep(5000);
                // if this is not the first loop then we consider this more carefully
                //we shuffle a tiny bit for adjustment's sake
                if (loopCounter >= 1){
                    shuffle(0.5,(moveAcrossOneBlockTime/2), !BlueAlliance);// We want to go towards to side again
                    telemetry.addLine("To Field border!");
                    telemetry.update();
                    sleep(500);
                    SkySkoneCaptured = true;
                }
            }else if (computerVisionResults[0].equals("false")) {
                ////shuffle the side of the field we are on,
                //eg on left side, means we shuffle left :)
                shuffle(0.5, moveAcrossOneBlockTime, BlueAlliance);
            } else {
                // if for what ever reason something does not work, don't stop the robot...
                shuffle(0.5,(moveAcrossOneBlockTime/4), BlueAlliance);// We want to go towards to side again

            }

        }


    }

    public String[] computerVisionRunning(List<VuforiaTrackable> allTrackables){
//  have code for computer vision reside here
        // check all the trackable targets to see which one (if any) is visible.
        targetVisible = false; // tells us if the target is visible
        sleep(1000);//A bit of time for the camera to process
        //telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                telemetry.addData("Visible Target", trackable.getName());
                telemetry.addLine("Skystone Target was found!");
                telemetry.update();
                targetVisible = true;
                if (trackable.getName().equals("Skystone")) {
                }

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                // this updates teh matrix as to the last known location of the robot
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }
        // passing through the args whether we have found the target
        args[0] = String.valueOf(targetVisible);

        // Provide feedback as to where the robot is located (if we know).
        if (targetVisible) {


            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

            // translation is vector array
            // x y z in that order
            double xPosition = translation.get(0); //how far it is facing forward
            double yPosition = translation.get(1); //how far left or right to the block
            //Y: -1 -> -1.5
            //+8 to the right
            //-3 for the left
            //Ph moved left: 3.4
            //Ph moved right: -8
            //-1 -> 1 = grab space

            //x value = value in front of camera


            String yposisitonSkystone = "";
            String xposisitonSkystone = "";

            // If we are on the blue alliance side of the field

            if (!BlueAlliance) {
                // flip the values
                yPosition *= -1;

            }
            if ((yPosition <= yPosCentreBoundaryRight) & (yPosition >= yPosCentreBoundaryLeft)) {
                yposisitonSkystone = "CENTRE, GRAB!!";

            } else if (yPosition > yPosCentreBoundaryRight) {
                yposisitonSkystone = "To Field Border";
            } else if (yPosition < yPosCentreBoundaryLeft) {
                yposisitonSkystone = "To Field Centre";
            }

            telemetry.addData("yPositionSkyStone: ", yposisitonSkystone);
            telemetry.update();
            sleep(4000); //REMOVE!
            // not using yet
            if (BlueAlliance) {
                if (xPosition > -8) {
                    xposisitonSkystone = "STOP";
                } else {
                    xposisitonSkystone = "FORWARDS!";
                }
            }
            args[1] = yposisitonSkystone;
            args[2] = xposisitonSkystone;
            args[3] = String.valueOf(xPosition);
            telemetry.addData("positionSkystone: ", "Stone located: %s, %s", yposisitonSkystone, xposisitonSkystone);

            // express the rotation of the robot in degrees.
//            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
//            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);


        } else {
            telemetry.addData("Visible Target", "none");
        }
        telemetry.update();
//        }

        telemetry.addLine("DONE!");
        telemetry.update();


        //args:
        //0 = targetVisible
        //1 = yposisitonSkystone
        //2 = xposisitonSkystone
        //3 = xPosition value

        return args;
    }
}


