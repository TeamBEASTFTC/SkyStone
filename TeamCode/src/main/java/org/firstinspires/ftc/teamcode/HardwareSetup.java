
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class HardwareSetup {


    // Motor configurations
    //Motor Decs
    //Naming motors
    static final String DriveTRName = "TR";
    static final String DriveTLName = "TL";
    static final String DriveBLName = "BL";
    static final String DriveBRName = "BR";
    static final String RotateCraneName = "Crane";

    //DcMotor drive declarations
    public DcMotor driveTR;
    public DcMotor driveTL;
    public DcMotor driveBL;
    public DcMotor driveBR;
    public DcMotor rotateCrane; //-ve power makes it go down
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
        public CRServo LFoundationHook; //
        public CRServo RFoundationHook;
        public Servo LSqueezer;
        public Servo RSqueezer;

    //    //Power var
    public double servoPower = 0.0;
    public double SqueezerServoPower = 0.0;
    public double SqueezerServoPos = 0.1; //assuming 0 is closed
    public double backwardsPower = -1.0;
    public double rotationPower = 0.35; //allows for one 90 turn in 1s

    //positioning var
    //Positioning constants
    private double yPosCentreBoundaryRight = 30.5;
    private double yPosCentreBoundaryLeft = -30.5;


    //Vuforia
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
//    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;
    private Object OwnallTrackables;
//    private Object allTrackables;



    /* Public OpMode members. */


    /* local OpMode members. */
    //for the computer vision
    boolean targetVisible = false;
    String[] args = {"","","","",""};
    boolean BlueAlliance = true; //FIX: for now...
    List<VuforiaTrackable>  allTrackables;
    VuforiaTrackables targetsSkyStone;
    VuforiaTrackable stoneTarget;


    HardwareMap hardwareMap;
    Telemetry telemetry;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public String HardwareSetup() {

        return "Success";
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hardwareMap, Telemetry telemetry, boolean vuforia_program) {

        String[] args = {"","","","",""};

        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
        // Save reference to Hardware map
        // Define and Initialize Motors
        //initialisation code first
        this.telemetry.addData("Status", "Initializing");
        this.telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
/*        driveTR = hardwareMap.get(DcMotor.class, DriveTRName);
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
        rotateCrane.setPower(0);*/

//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
        // SERVOS
//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
/*        LFoundationHook = hardwareMap.get(CRServo.class, LFoundationHookName);
        RFoundationHook = hardwareMap.get(CRServo.class, RFoundationHookName);
        LSqueezer = hardwareMap.get(Servo.class, LsqueezerName);
        RSqueezer = hardwareMap.get(Servo.class, RsqueezerName);
//        squeezer.setPower(servoPower);
        LFoundationHook.setPower(servoPower);
        RFoundationHook.setPower(servoPower*-1);*/


//        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        driveTL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        driveTR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
/*
        Old Code:
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        leftClaw  = hwMap.get(Servo.class, "left_hand");
        rightClaw = hwMap.get(Servo.class, "right_hand");
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);*/

    //If we are using vuforia for this opMode, we need to initialise it
        if (vuforia_program){
/*            LFoundationHook.setPower(servoPower);
            RFoundationHook.setPower(servoPower*-1);
            LSqueezer.setPosition(SqueezerServoPos);
            RSqueezer.setPosition(1-SqueezerServoPos);*/

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
            this.allTrackables = allTrackables;
            this.targetsSkyStone= targetsSkyStone;
            this.stoneTarget = stoneTarget;

        }// end vuforia if

    }


    //Motor/Driver functions
    public void turnOffDrive() {
        telementryLineMessage("Turning off drive motor power/");
/*        driveTR.setPower(0);
        driveTL.setPower(0);
        driveBL.setPower(0);
        driveBR.setPower(0);*/
    }

    public void moveForwBack(double power, int time, boolean back) {

        if (back) {
            power = power * -1;
        }


/*        driveTR.setPower(power);
        driveTL.setPower(power);
        driveBL.setPower(power);
        driveBR.setPower(power);*/
        this.telemetry.addData("Moving at power: ", power);
        this.telemetry.update();
        sleep(time);
        turnOffDrive();
    }

    public void shuffle(double power, int time, boolean right) {
        if (!right) {
            // shuffling left
/*            driveTR.setPower(power);
            driveTL.setPower((power * -1));
            driveBL.setPower(power);
            driveBR.setPower((power * -1));*/
        } else {
            // shuffling right
/*            driveTR.setPower((power * -1));
            driveTL.setPower(power);
            driveBL.setPower((power * -1));
            driveBR.setPower(power);*/
        }
        this.telemetry.addData("Shuffling at power: ", power);
        this.telemetry.update();
        sleep(time);
        turnOffDrive();
    }

    public void rotate90(boolean clockwise, int time) {
        // 0.35 power for 1s = 90 deg
        if (!(clockwise)) {
            this.telemetry.addLine("Rotating 90 Anti-Clockwise!");
            this.telemetry.update();
            backwardsPower = rotationPower * -1;
/*            driveTR.setPower(rotationPower);
            driveTL.setPower(backwardsPower);
            driveBL.setPower(backwardsPower);
            driveBR.setPower(rotationPower);*/
        } else {
            this.telemetry.addLine("Rotating 90 Clockwise!");
            this.telemetry.update();
/*            backwardsPower = rotationPower * -1;
            driveTR.setPower(backwardsPower);
            driveTL.setPower(rotationPower);
            driveBL.setPower(rotationPower);
            driveBR.setPower(backwardsPower);*/

        }

        sleep(time);
        turnOffDrive();
    }


    //Messaging
    public void telementryLineMessage(String msg) {
        this.telemetry.addLine(msg);
        this.telemetry.update();
    }


    // Servos
    public void unlockFoundationClips() {
        telementryLineMessage("Unlocking Foundation Clips");
//        LFoundationHook.setPower(0);
//        RFoundationHook.setPower(0);
    }



    // Computer vivion/Vuforia
    public String[] computerVisionRunning(List<VuforiaTrackable> allTrackables) {
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
            telemetry.addData("X Pos: ", xPosition);
            telemetry.update();
            sleep(3000); //REMOVE
            if (!BlueAlliance) {
                // flip the values
                yPosition *= -1;
                telemetry.addData("Y Pos flipped: ", yPosition);
                telemetry.update();
                sleep(3000); //REMOVE
            }
            if ((yPosition <= yPosCentreBoundaryRight) & (yPosition >= yPosCentreBoundaryLeft)) {
                yposisitonSkystone = "CENTRE, GRAB!!";
//                    }
//                    if (yPosition > -1 ){
//                        yposisitonSkystone = "LEFT";
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
