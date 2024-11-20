[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/YybNWfh8)

javac -d . imageprocessor/*.java imageprocessor/utils/*.java imageprocessor/processor/*.java

java imageprocessor.Main input/large.png 100 S

java imageprocessor.Main input/large.png 50 M 


### Folder Structure
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