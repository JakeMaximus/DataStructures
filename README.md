# DataStructures
Programming using Data Structures

Your company is developing a JavaFX ticketing system for World Cup 2026. The
system is expected to have the following features:
1. Customer registration
2. Customer login
3. Customer can view seat layout, availability, and choose seat
4. Customer can buy tickets
5. Stadium staff can see view seats and the customers of the seats

Second Part (more complex):
1. Customer can choose a match to view its seat layout, availability, and choose seat.
2. Customer can choose a match to buy tickets.
3. Stadium staff can choose a match to view seat details including the customers of the seats.
4. Stadium staff can view statistics of matches (combined capacity, combined seats sold out, combine
revenue).
5. Block purchasing of unavailable seats.
6. Simulation of spectators queueing to enter the stadiums. There are 2 entrances for each stadium. A
spectator arrive at a specified entrance in random order.
7. A Java Documentation for a public class that has public constructors, and public methods with return
values and parameters.

Queue Simulation
Here is an example of a queue simulation. Let say Stadium X has a capacity of 12 seats with the following
layout. The spectators with ticket row A-B are directed to queue at entrance E1 while spectators with row
C-D queue at entrance E2. Spectators arrive the queue in random order.
Ticket sold for E1: [A1, A2, A3, B1, B2, B3]
Ticket sold for E2: [C1, C2, C3, D1, D2]
1 2 3
|A - - - E1
|B - - - |
|C - - - |
E2 D - - - |
Queue at E1: [A1, A2, B1, B2, B3, A3]
Queue at E2: [C3, C1, C2, D1, D2]
Press Enter to serve next spectator in queues...
1 2 3
|A / - - E1
|B - - - |
|C - - / |
E2 D - - - |
Queue at E1: [A2, B1, B2, B3, A3]
Queue at E2: [C1, C2, D1, D2]
Press Enter to serve next spectator in queues...
1 2 3
|A / / - E1
|B - - - |
|C / - / |
E2 D - - - |
Queue at E1: [B1, B2, B3, A3]
Queue at E2: [C2, D1, D2]
Press Enter to serve next spectator in queues...
1 2 3
|A / / - E1
|B / - - |
|C / / / |
E2 D - - - |
Queue at E1: [B2, B3, A3]
Queue at E2: [D1, D2]
Repeat the dequeuing until the queue is empty.
1 2 3
|A / / / E1
|B / / / |
|C / / / |
E2 D / / - |
Queue at E1: []
Queue at E2: []
