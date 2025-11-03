Smart Food Delivery Tracker
A Java 24 application demonstrating OOP principles for managing food delivery orders.

Features
* Customer order placement and tracking
* Delivery agent assignment and management
* Order status tracking (PLACED → PREPARING → DISPATCHED → DELIVERED)
* Menu management system

Requirements
* Java 24 (JDK 24)

Build and Run
Windows

bash
javac --enable-preview --release 24 src/*.java
java --enable-preview -cp src Main

Mac/Linux

bash
javac --enable-preview --release 24 src/*.java
java --enable-preview -cp src Main
```

Or simply run:
- Windows: Double-click `run.bat`
- Mac/Linux: `./run.sh`

## Project Structure
```
src/
├── Order.java
├── Item.java
├── OrderStatus.java
├── User.java
├── Customer.java
├── DeliveryAgent.java
├── DeliveryManager.java
├── Menu.java
└── Main.java

Technologies Used
* Java 24 (Records, Sealed Classes, Pattern Matching, Switch Expressions)

Author
Bhaumik Shyam Birje
A00335935 OOP1 Assignment - TUS
