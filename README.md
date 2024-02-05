# Warehouse Robotic Control System

## Demo (Click to View)
[![Watch the video](https://filedn.eu/lfgrnWJ01yfHacQuQP84g4X/Team%20Software%20Project%20-%20Tema%205%20-%20WRCS/WRCS.png)]([https://youtu.be/vt5fpE0bzSY](https://filedn.eu/lfgrnWJ01yfHacQuQP84g4X/Team%20Software%20Project%20-%20Tema%205%20-%20WRCS/team5_demo_d4.mp4))

## Introduction
The Warehouse Robotic Control System (WRCS) is a software application that automates warehouse processes using a warehouse robot. In this README, we will focus on the technical implementation aspects of the WRCS.

## System Architecture
The WRCS consists of two main components:

1. **Controller**: This component is responsible for managing the core functionality of the WRCS. It receives orders from the Order System, prepares orders for the Robot, calculates the shortest path for order fulfillment, and sends order details and paths to the Robot.

2. **Visualisation Tool**: The Visualisation Tool provides a user interface for warehouse operators to start/stop the system and monitor the Robot's operation. It also displays a visual representation of the warehouse layout, including the charging area, storage area, dispatched area, and the path the Robot follows.

## Programming Language and Tools
The WRCS is implemented in Java, a versatile and platform-independent programming language. Java was chosen for its portability and suitability for creating cross-platform software.

### Java Swing for User Interface
Java Swing, a GUI widget toolkit, is used to create the user interface of the WRCS. It provides the necessary components like JFrame, JPanel, JList, JButton, etc., for building the graphical user interface.

## Core Components and Classes
### Controller
The Controller class is the heart of the WRCS, responsible for coordinating the various functionalities. It interacts with the Order System, Item Database, and Robot to ensure seamless order processing.

Key functions of the Controller:
- Receiving and validating orders.
- Preparing orders for the Robot.
- Calculating the shortest path for order fulfillment.
- Sending instructions and paths to the Robot.

### Visualisation Tool
The Visualisation Tool class creates the graphical user interface for monitoring the WRCS operation. It displays the warehouse layout, the Robot's path, and allows warehouse operators to control the system.

Key features of the Visualisation Tool:
- Visual representation of the warehouse layout.
- Start/stop controls for the Controller and Robot.
- Real-time monitoring of the Robot's movement.

### Robot Emulation
To facilitate testing and development, a digital model of the warehouse robot, known as Robot Emulation, is used. It interacts with the Visualisation Tool to mimic the Robot's behavior within the warehouse environment.

### PathFinder
The PathFinder class is responsible for calculating the shortest path between locations within the warehouse. It uses algorithms like Dijkstra's shortest path algorithm to determine efficient routes for the Robot.

## Testing
Testing is a critical aspect of the WRCS implementation to ensure its functionality and reliability. Two main types of testing are performed:

### Whitebox Testing (Control-Flow Testing)
Control-Flow Testing is used to establish the execution order of program statements within the WRCS. It involves analyzing the control structure of the software to create test cases. For example, the findShortestPath() method of the PathFinder class is tested using control-flow testing.

### Black-box Testing
Black-box testing focuses on testing the WRCS from a user's perspective without knowledge of its internal code. It checks if the software meets the specified requirements and behaves as expected. For instance, the UI components and functionalities, such as order submission and monitoring, are tested through black-box testing.

## Lessons Learned
Throughout the implementation of the WRCS, several valuable lessons were learned:

1. **Version Control**: Effective version control using Git is crucial for collaboration and code management in team projects.

2. **Writing Requirements**: Writing clear and unambiguous software requirements is essential to guide the development process effectively.

3. **Teamwork**: Collaborative project management, task assignment, and communication are vital for successful project execution.

4. **Java Features**: Gaining a deeper understanding of Java and its features is beneficial when working on Java-based projects.

The WRCS project has provided practical experience in software development, testing, and project management, contributing to the team's skill set and knowledge.
