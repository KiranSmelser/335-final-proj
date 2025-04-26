# CSC 335 Final Project

## Cribbage

### Design

This project implements a complete and fully functional Java-based Cribbage game, featuring both human and computer players, while adhering to the MVC design principles. The Model consists of backend classes such as Card, Deck, Hand, Crib, Board, and Cribbage, each designed with clear encapsulation and purposeful interaction, ensuring a clear separation of concerns. The View manages all user interactions through the console, performing input validation to ensure robustness. The Controller logic resides primarily in the Cribbage class, which coordinates interactions between the model and view.

Our code employs key design patterns: the FLYWEIGHT pattern, which is effectively used in the Card class to ensure that card instances are shared rather than duplicated. The Strategy interface utilizes the STRATEGY pattern, encapsulating distinct algorithms for the computer's discarding and pegging strategies through concrete implementations (EasyStrategy and HardStrategy). Data structures (e.g., ArrayList, LinkedHashMap, and HashMap) and features (e.g., enums for card suits and ranks, Comparator interfaces for sorting) were thoughtfully integrated to enhance clarity, efficiency, and maintainability of our code. Additionally, our implementation includes comprehensive JUnit tests that provide greater than 90% coverage for each backend class, ensuring correctness and reliability. Finally, player statistics (win/loss records) are accurately tracked, and a more advanced and complex computer strategy (HardStrategy) is implemented, fulfilling the advanced feature requirement of the project.

### How to Run

```bash
java -jar cribbage.jar
```