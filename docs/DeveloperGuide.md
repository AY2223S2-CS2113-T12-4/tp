# Developer Guide
## Product Name
**WellNUS++**

## Acknowledgements
1. Reference to AB-3 Developer Guide: https://se-education.org/addressbook-level3/DeveloperGuide.html
2. Reference to AB-3 diagrams code: https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams

## Setting up, getting started
### Setting up the project in your computer
Firstly, **fork** this repo, and **clone** the fork into your computer. <br>
<br>
If you plan to use Intellij IDEA (highly recommended): <br>
1. **Configure the JDK**: Follow the guide 
[[se-edu/guides] IDEA: Configuring the JDK](https://se-education.org/guides/tutorials/intellijJdk.html) 
to ensure Intellij is configured to use **JDK 11**.
2. **Import the project as a Gradle project**: Follow the guide 
[[se-edu/guides] IDEA: Importing a Gradle project](https://se-education.org/guides/tutorials/intellijImportGradleProject.html) 
to import the project into IDEA.<br>
   **Note**: Importing a Gradle project is slightly different from importing a normal Java project.
3. **Verify the setup:**
   1. Run the ```wellnus.WellNus``` and try a few commands.
   2. [Run the tests](https://se-education.org/addressbook-level3/Testing.html) to ensure they all pass.

### Before writing code
1. **Configure the coding style**<br>
If using IDEA, follow the guide 
[[se-edu/guides] IDEA: Configuring the code style](https://se-education.org/guides/tutorials/intellijCodeStyle.html) 
to set up IDEA’s coding style to match ours.<br>
<br>
2. **Set up CI**<br>
   This project comes with a GitHub Actions config files (in `.github/workflows` folder). 
When GitHub detects those files, it will run the CI for your project automatically at each push 
to the `master` branch or to any PR. No set up required.<br>
<br>
3. **Learn the design**<br>
When you are ready to start coding, we recommend that you look at the class diagrams to understand the structure of the 
code and the interaction among different classes.<br>

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Object Diagram

## Product scope
### Target user profile
* NUS Computing and Engineering students
* Spend lots of time coding on their IDE and type relatively fast
* Have to regularly use digital gadgets and Internet for their courses
* Very familiar with command line interfaces
* Stressed about academy and many others
* Busy with work and drowning in deadlines
* Wants to improve their wellness
* Sometimes unmotivated with short attention span

### Value proposition
NUS Computing and Engineering students are often busy with work and sometimes will neglect their wellness. This app aims
to help NUS Computing and Engineering students improve their overall wellness by encouraging the **cultivation of meaningful
atomic habits**, **practice of self reflection** and **usage of offline timer to stay focused**. By using this app, 
we hope users will be more aware of the healthiness of their daily life and take actions to improve their wellness.<br>
<br>
WellNUS++ is a CLI app, primarily due to the following reasons: 
* Computing students generally type fast and prefer typing to mouse due to their daily coding routines. 
* Due to the data heavy nature and personalised user input of this app, typing will be preferred to clicking.
* In particular, our application is built to reduce context switching. Users can launch the application from the comfort
of their favourite IDE’s terminal to reduce disruption to their daily coding lives.
* Instead of using electronics with fancy GUI, this CLI app gives computing students an opportunity to minimise digital 
interaction which will be beneficial for their wellness.
* The app is gamified to make it more attractive for students to use. Levels and micro-goals incentivise our 
users to keep using the app’s features, allowing them to focus on their work and achieve wellness.

## User Stories

| Version | As a ...                                           | I want to ...                                  | So that I can ...                                           |
|---------|----------------------------------------------------|------------------------------------------------|-------------------------------------------------------------|
| v1.0    | Computing student who prefers typing over clicking | I can use keyboard instead of mouse            | I can use the app efficiently                               |
| v1.0    | Computing student who is too used to the Internet  | Reduce my browsing and information overload    | I can improve my attention span                             |
| v1.0    | Reflective student                                 | I can get one introspective question on-demand | I can reflect and grow emotionally at my own pace           |
|         |                                                    |                                                |                                                             |
|         |                                                    |                                                |                                                             |
|         |                                                    |                                                |                                                             |
|         |                                                    |                                                |                                                             |
|         |                                                    |                                                |                                                             |
| v2.0    | user                                               | find a to-do item by name                      | locate a to-do without having to go through the entire list |

## Non-Functional Requirements
1. Should work on any mainstream OS as long as it has Java 11 or above installed.
2. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should 
be able to accomplish most of the tasks faster using commands than using the mouse.

## Glossary
* *glossary item* - Definition
* **Mainstream OS**: Windows, Linux, Unix, OS-X

## Instructions for manual testing
### Launch 
1. Ensure you have Java 11 or above installed in your Computer.
2. Download the latest `wellnus.jar` from here.
3. Copy the file to the folder you want to use as the home folder for your WellNUS++.
4. Open a command terminal, cd into the folder you put the `wellnus.jar` file in, and use the `java -jar wellnus.jar` 
command to run the application. A CLI should appear in a few seconds.

### Sample test cases
#### Help command
1. Make sure you are in the main interface, but individual features(i.e. hb, reflect and timer)
2. Test case: `help`<br>
Expected output: a list of commands with their usage
Example: 
```
------------------------------------------------------------
    We are here to ensure your wellness is taken care of through WellNUS++
Here are all the commands available for you!
------------------------------------------------------------
------------------------------------------------------------
    1. hb - Enter Atomic Habits: Track your small daily habits and nurture it to form a larger behaviour
    usage: hb
    2. reflect - Read through introspective questions for your reflection
    usage: reflect
    3. exit - Exit WellNUS++
    usage: exit
------------------------------------------------------------
```
3. Test case: `help me`<br>
Expected output: the list of commands will not be generated as it is an invalid command<br>
Example: 
```
------------------------------------------------------------
    help does not take in any arguments!
------------------------------------------------------------
```
4. To get a list of available commands, any command other than `help` is invalid

#### Get reflection questions
1. Make sure you are inside **Self Reflection** feature by enter `reflect` command after the launch of the program
2. Test case: `get`<br>
Expected  output: get a set of 5 random introspective questions<br>
Example: 
```
============================================================
    1.What is my purpose in life?
    2.What is my personality type?
    3.Did I make time for myself this week?
    4.What scares me the most right now?
    5.When is the last time I gave back to others?
============================================================
```
3. Test case: `get reflect`<br>
Expected output: introspective questions will not be generated as this is an invalid command. <br>
Example: 
```
!!!!!!-------!!!!!--------!!!!!!!------!!!!!---------!!!!!!!
Error Message:
    Command is invalid.
Note:
    Please check the available commands and the format of commands.
!!!!!!-------!!!!!--------!!!!!!!------!!!!!---------!!!!!!!
```
4. Any command other than `get` is invalid

#### Add atomic habits
1. Make sure you are inside **Atomic habit** feature by enter `hb` command after the launch of the program
2. Test case: `add --name make bed every morning`<br>
Expected output: a new atomic habit is successfully added<br>
Example: 
```
------------------------------------------------------------
    Yay! You have added a new habit:
    'make bed every morning' was successfully added
------------------------------------------------------------
```
3. Test case: `add name make bed every morning`<br>
Expected output: the atomic habit will not be added in as this is an invalid command<br>
Example: 
```
!!!!!!-------!!!!!--------!!!!!!!------!!!!!---------!!!!!!!
Error Message:
    Wrong arguments given to 'add'!
Note:
    
!!!!!!-------!!!!!--------!!!!!!!------!!!!!---------!!!!!!!
```
4. Any commands that does not follow the format of `add --name ATOMIC_HABIT_NAME` is invalid

### Saving data