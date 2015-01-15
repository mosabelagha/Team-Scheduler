# Team Practice Scheduler
####Created by: Mosab Elagha
####Version 1.0
A scheduling program to assign team players practice times that work best with their preferences and availability

## Go [here](https://github.com/mosabelagha/Team-Scheduler/releases) to download the latest version, along with direction on how to use the program
------

## Building (for developers)
###Installing Gradle
This is a Gradle project. You will need to have Gradle installed.
#####Mac
You can use Homebrew to easily install Gradle (http://brew.sh/)

    brew install gradle

#####Windows/Linux
You can find instructions here: http://www.gradle.org/docs/current/userguide/installation.html

###Building an Eclipse project
To build an Eclipse project, you can run the following

    gradle eclipse

After this, import the project to Eclipse

###Exporting the completed .jar
#####Recommended
I recommend using the custom command I created for this project

    gradle release
This will create a folder 'RELEASE' in the same directory as the build.gradle file and that folder will have all the files that are needed.

Additionally, a ZIP file with all the files ready for release is generated. (You'll need to run the command again to do this. Small bug, not sure how to fix)

#####Other option
You can also run 

    gradle build
in order to run the project. This will create the .jar file in 'build/libs' but you will need to maunally move the other required files.

# Todo
* Fix corner cases
* Make variables more dynamic (number of players per slot, level names, number of levels, etc.)
* Make build.gradle better
* [Low Priority] Have this all be self contained (no excel/source file)

##If you have any suggestions, please let me know!
