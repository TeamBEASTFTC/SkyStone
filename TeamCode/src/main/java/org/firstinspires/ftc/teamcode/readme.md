# Team BEAST Team Code

Welcome!
This is the main codebase for Team BEAST's programs. The programs are split accordingly:
1) Anything with the name with TeleOp corresponds to a TeleOp program
2) Anything with the name with Hardware involves configuration of the hardware
3) All else is autonomous

## Writing an Autonomous
### The setup
To start an autonomous you'll need the following:
`
@Autonomous(name="HardwareTest", group="Tests")
public class HardwareTests extends LinearOpMode {


    HardwareSetup choppy = new HardwareSetup();
    @Override
    public void runOpMode() {


        robot.init(hardwareMap, telemetry, true);
        waitForStart();
        robot.telementryLineMessage("Success we can call functions");
        sleep(1000);
    }
}
`
Now let's brake this down. Firstly:
`
@Autonomous(name="fileName", group="Tests")
`
The `@Autonomous` tells the program that this is an autonomous program. If you want to make a TeleOp, simple have `@TeleOp`.
The name is the name of the file which will be shown on the phone and the group is just a group for the program, the phone will have a small line sepearting the different programs of different groups. It is not too important though.

Next:
`
public class fileNmae extends LinearOpMode {
`
This may be a bit confusing, but all you really need to know is that the text in the middle: fileName, needs to be the name of the file.

Within this loop but before the next is where we can do the configuration, setups, naming of variables. The following code does some of this:
`
    HardwareSetup choppy = new HardwareSetup();
`
What this does is it do a lot of the setup for you. All the code is written within a file called HardwareSetup and you basically get to copy all of it and place it within a variable called choppy (this can be whatever you want it though).

Think of it like you have an infinite amount of toolboxes. You say "hey I want a toolbox", and then you say: "I'll name you choppy".

Within this toolbox there's a lot of tools! Duh...

To use these tools ou need to say choppy.toolName. This is like first saying, "I want that toolbox and then I want to use that tool."

However, these "tools" will require information from you, think of it like giving the tool the batteries it needs to run.

These are called parameters and within Android Studio you should be prompted with what these are.
Let's go through a couple of these tools.
`
choppy.init(hardwareMap, telemetry, vuforia_program)
`
This does the initialisation of the robot's hardware behind the scenes. This does the "mapping" between the variable for each piece of hardware within our code and its corresponding variable we gave it on the phone (the phone variable is where we say: Servo Controller Port 1 = LFoundationHook).

The hardwaremap is just a variable ou pass through.

The telemetry allows us to do telemetry calls (calls to the phone's screen with text for us to read).

The vuforia_program is a true or false. So you literally type true or false for this one. This tells the program if it needs to setup the phone's camera for computer vision or not. Leaves this to false if you do not plan on doing computer vision.

`
choppy.turnOffDrive();
`
This tool (function/method) set all the drive motors to 0.


`
choppy.moveForwBack(power, time, back)
`
This one allows us to move the robot forwards or backwards.

Power: a value from 0 - 1 which determines the power given to the motors

Time: the amount of time in miliseconds that you want to drive

Back: a true/false on whether you want the robot to drive backwards. If false it will move forwards.

`
choppy.shuffle(power, time, right);
`
This allows for the shuffling of the robot.

Power: a value from 0 - 1 which determines the power given to the motors

Time: the amount of time in miliseconds that you want to shuffle

right: a true/false on whether you want the robot to shuffle right. If false it will shuffle left.

`
choppy.rotate90(clockwise, time);
`
This allows for the robot to turn. It has a preset power of 0.35 as 1s of this power does a 90deg turn.

Clockwise: true/false depending if you want to go clockwise or anti clockwise

Time: the amount of time in miliseconds that you want to turn. 90deg = 1000

`
choppy.telementryLineMessage(message);
`
If you ever want to just send a piece of text to the phone for debugging without any data, this is a nice way to do it.

message = a string with the message you want to say.

`
choppy.unlockFoundationClips();
`
This sets the foundation clips to power 0.


`
choppy.computerVisionRunning(allTrackables)
`
This does the checking of whether a skystone can be seen. You need to pass it the allTrackables variable by doing `choppy.allTrackables`.

It will return an array with the following:

choppy.computerVisionRunning(allTrackables)[0] = targetVisible

choppy.computerVisionRunning(allTrackables)[1]= yposisitonSkystone

choppy.computerVisionRunning(allTrackables)[2]= xposisitonSkystone

choppy.computerVisionRunning(allTrackables)[3]= xPosition value

Here's an example of how this is used:
`

boolean SkyStoneCaptured = false;
        int loopCounter = 0;
        while (!(SkyStoneCaptured)){
            loopCounter += 1;
            telemetry.addData("Loop counter: ", loopCounter);
            telemetry.update();

            choppy.targetsSkyStone.activate();
            //calling the computer vision now
            String[] computerVisionResults = choppy.computerVisionRunning(choppy.allTrackables);
            choppy.targetsSkyStone.deactivate();
            //if robot is not center
            if (computerVisionResults[1].equals("CENTRE, GRAB!!")){
                choppy.telementryLineMessage("HEEREE! centre");
                sleep(500);
                SkyStoneCaptured = true;

            }else if ((computerVisionResults[1].equals("To Field Centre"))){
                choppy.telementryLineMessage("HERE! To field centre");
                sleep(2000);
                choppy.shuffle(0.5, (moveAcrossOneBlockTime/2), BlueAlliance);
                SkyStoneCaptured = true;

            } else if (computerVisionResults[1].equals("To Field Border")){
                choppy.telementryLineMessagee("HERE! To field border");
                sleep(2000);
                // if this is not the first loop then we consider this more carefully
                //we shuffle a tiny bit for adjustment's sake
                if (loopCounter >= 1){
                    choppy.shuffle(0.5,(moveAcrossOneBlockTime/2), !BlueAlliance);// We want to go towards to side again
                    choppy.telementryLineMessage("To Field border!");
                    sleep(500);
                    SkyStoneCaptured = true;
                }
            }else if (computerVisionResults[0].equals("false")) {
                ////shuffle the side of the field we are on,
                //eg on left side, means we shuffle left :)
                choppy.shuffle(0.5, moveAcrossOneBlockTime, BlueAlliance);
                robot.telementryLineMessage("Vuforia computer vision = false");
                sleep(1000);
            } else {
                // if for what ever reason something does not work, don't stop the robot...
                choppy.telementryLineMessage("Vuforia else statement");
                sleep(1000);
                choppy.shuffle(0.5,(moveAcrossOneBlockTime/4), BlueAlliance);// We want to go towards to side again

            }

        }
        `

# If you have any questions just ask!
