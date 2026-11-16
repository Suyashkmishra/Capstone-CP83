# Capstone-CP83
# ScreenSeat: Cinema Show and Seat Booking System (CP-083)

A simple console-based Java project for a small cinema. It lets the cinema manage shows, book and cancel seats, and see occupancy and revenue without paying commission to any booking website.

## Features

1. **Add Screen and Show:** add a show with movie name, time, rows and seats per row.
2. **View Seat Map:** see free `[ ]` and booked `[X]` seats for any show. First 2 rows are Premium, the rest are Classic.
3. **Book Seats:** pick seats like `A1`, `B3`, add a food combo, and get a printed ticket.
4. **Cancel Booking:** cancel by booking ID and get a refund based on time left.
5. **Business Report:** occupancy and revenue per show and per movie.
6. **Food Combo (extra):** Popcorn, Popcorn + Coke, Nachos or no food.

## Pricing and Refund Rules

| Item | Price |
|------|-------|
| Premium seat (rows A, B) | Rs 250 |
| Classic seat (other rows) | Rs 150 |
| Popcorn | Rs 120 |
| Popcorn + Coke | Rs 200 |
| Nachos | Rs 150 |

| Time left before show | Refund |
|-----------------------|--------|
| 2 hours or more | 80% |
| Less than 2 hours | 50% |

## Project Files

| File | What it does |
|------|--------------|
| `Show.java` | Stores show details and the seat grid (2D boolean array) |
| `Booking.java` | Stores one booking and prints the ticket |
| `FileHelper.java` | Saves and loads shows and bookings from text files |
| `Main.java` | Menu and all the main logic |

Data files (created automatically): `shows.txt` and `bookings.txt`. Data stays saved after closing the program.

## How to Run

Make sure Java (JDK) is installed. Open a terminal in the project folder and run:

```
javac *.java
java Main
```

## Concepts Used

Classes and objects, constructors, getters and setters, ArrayList, 2D arrays, loops, switch, if/else, Scanner for input, and file handling with FileWriter and BufferedReader.

## Author

Suyash