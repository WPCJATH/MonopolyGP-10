# GP-10 Monopoly
## Introduction
The implementation of group project in course COMP3211 Software Engineering (21/22 sem 1) of The Hong Kong Polytechnic 
University from group 10.

This is a Command-Line based simple Monopoly game running on OS X or Linux terminal. The game uses a custom GUI-like 
interface and supports keyboard input without echo. The game interface is refreshed, with a refresh rate currently set 
at 10. MVC architecture is applied throughout the game.


### Refreshed Interface
Although the command line interface is used as the display, we strive for a GUI-like operation experience, so we 
implement a large number of API, such as Window, Widget, Button, Label, and so on. The whole interface
is refreshed in real time every 0.1s. ANSI escapes are used to move the Terminal cursor so that the terminal window 
position remains fixed when the interface is refreshed. That's why the game does not work on Windows.
### Key Listen without Echo
In Terminal of the Unix family of operating systems, keyboard input usually results in an echo i.e. The keyboard input
would be displayed directly in Terminal, but it was clear that the echo would mess with our display, and our game 
did not need this feature, so we blocked it.
### Animations
Because the rules and mechanics of the game are relatively simple, the game runs as fast as rock-paper-scissors. We 
considered adding some animations to slow down the pace of the game and make it more playable. Detailed animations are 
shown in the video below.

## Demo Video
<video id="video" controls="" preload="auto" poster="images/gameboard.png">
<source id="mp4" src="https://www.youtube.com/watch?v=D5y0iqo7b1o" type="video/mp4">
</video>

## 123
