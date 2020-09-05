# LevelControl
An example for communication between android and esp8266
Esp8266 has a wifi module and for communication a connection is required between android and esp.
In this case, the phone is used to create a hotspot to which the esp connects to.
Specifics of project:
The application decides the mode in which the PID controller behaves:
1. Manual: set the PID constants manually in application and send that data to esp
2. Automatic: The PID values are calculated on esp side using different tuning methods and then sent to the android
