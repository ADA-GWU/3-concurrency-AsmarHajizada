[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/YybNWfh8)

# Assignment 3 - Concurrency

## Task Definition

The goal for this task is to processes images by dividing them into squares of a given size, averaging the colors within each square, and showing results for both Single-Threaded and Multi-Threaded mode. 

## Features

**1. Single-Threaded Processing**

 - Processes the image sequantially (left-to-right) from top to bottom.
 - Visualizes the progress dynamically in a resized window, if the image is larger than the screen size.
 - Saves the result in the [src/output](output/) folder

**2. Multi-Threaded Processing**

 - Divides the image into regions processed in parallel by multiple threads. 
 - Dynamically visualizes progress with simultaneous updates from multiple regions. Here, the initial approach (processing sequantially) was changed to different location for each core so that multi-threading's difference from single-threading could be shown, other than its speed.
 - Saves the result in the [src/output](output/) folder

**3. Visualization and Resizing**

 - Displays a the image as the averaging process happens. If the image size is bigger than the screen, displays processing for screen-fit visualization.
 - While it shows the resized image (if image is too big), the actual process is done on the original image at full resolution.


javac -d . imageprocessor/*.java imageprocessor/utils/*.java imageprocessor/processor/*.java

java imageprocessor.Main input/large.png 100 S

java imageprocessor.Main input/large.png 50 M 


## Folder Structure
```
└── src
    ├── imageprocessor
    │   ├── Main.java
    │   ├── processor
    │   │   ├── MultiThreadProcessor.java
    │   │   └── SingleThreadProcessor.java
    │   └── utils
    │       ├── ImageUtils.java
    │       └── ScreenUtils.java
    ├── input
    │   ├── large.png
    │   └── small.jpg
    └── output
        ├── multi_thread_50.jpg
        └── single_thread_100.jpg
```

### Components 

**1.[Main.java](src/imageprocessor/Main.java)