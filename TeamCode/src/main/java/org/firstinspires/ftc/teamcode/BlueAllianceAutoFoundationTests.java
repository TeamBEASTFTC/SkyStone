package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueAllianceAutoFoundationTests", group ="Concept")
public class BlueAllianceAutoFoundationTests extends LinearOpMode {
    // Personal variables
    private String wallSide = "LEFT";
    //whether turns are done clockwise or anticlockwise
    private boolean BlueAlliance = true;
    public String[] args = {"false", "", "", "", ""};

    private ElapsedTime timeCounter = new ElapsedTime();

    public boolean vuforiaInitialisation;

    private boolean FoundationCaptured = false;

    private int loopCounter = 0;



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

    //distances
    int timeToFoundation = 1549;
    int timeToFoundationAndMore = timeToFoundation + 51;
    int timeToWallWithFoudation = (860*2) + 400; //Time to wall with foundation
    int timeshuffleToGate = 6000; //update when we have the numbers!

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
        rotateCrane.setPower(0);

//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
        // SERVOS
//        squeezer = hardwareMap.get(CRServo.class, squeezerName);
        LFoundationHook = hardwareMap.get(CRServo.class, LFoundationHookName);
        RFoundationHook = hardwareMap.get(CRServo.class, RFoundationHookName);
        LSqueezer = hardwareMap.get(Servo.class, LsqueezerName);
        RSqueezer = hardwareMap.get(Servo.class, RsqueezerName);
//        squeezer.setPower(servoPower);
        LFoundationHook.setPower(servoPower);
        RFoundationHook.setPower(servoPower*-1);
        LSqueezer.setPosition(SqueezerServoPos);
        RSqueezer.setPosition(SqueezerServoPos);
        //start
        //servos move up
        servoPower = 1;
        LFoundationHook.setPower(servoPower);
        RFoundationHook.setPower(servoPower*-1);
        sleep(2000);
        LFoundationHook.setPower(0);
        RFoundationHook.setPower(0);






        waitForStart();
//        bring the clips up
        // moving to foundation backwards
        moveForwBack(0.5, (timeToFoundationAndMore - 500), true);
        moveForwBack(0.25, 750, true);
        //grabs foundation
        lockFoundationClips(1,true);
        //moves back towards the wall
        moveForwBack(0.25, timeToWallWithFoudation, false);
        // unclips
        lockFoundationClips(1, false);
        unlockFoundationClips();
        sleep(2000);
        shuffle(0.5, timeshuffleToGate, false);

//        moveForwBack(0.25,);
//FELIX HERE
//    shuffle(0.5, 1000, false);


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
    // moves the clips down or up
    public void lockFoundationClips(double power, boolean down) {

        if (down){
            power = power * -1;
        }
        //servos move up
        LFoundationHook.setPower(power);
        RFoundationHook.setPower(power*-1);
        // time for teh clip to go down
        sleep(2000);
    }
    //turns off power in servos
    public void unlockFoundationClips() {
        LFoundationHook.setPower(0);
        RFoundationHook.setPower(0);
    }

    public void shuffle(double power, int time, boolean blueAlliance) {
        if (blueAlliance){
            // shuffling left
            driveTR.setPower(power);
            driveTL.setPower((power* -1));
            driveBL.setPower(power);
            driveBR.setPower((power * -1));
        } else{
            // shuffling right
            driveTR.setPower((power * -1));
            driveTL.setPower(power);
            driveBL.setPower((power * -1));
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
}
