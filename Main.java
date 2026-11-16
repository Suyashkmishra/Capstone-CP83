import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String SHOWS_FILE = "shows.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Show> showList = FileHelper.loadShows(SHOWS_FILE);
        if (showList.isEmpty()) {
            showList.add(new Show(101, "Avatar: Fire and Ash", "18:00", 5, 8));
            showList.add(new Show(102, "Interstellar", "21:00", 5, 8));
            FileHelper.saveShows(showList, SHOWS_FILE);
            System.out.println("Default shows created (5 rows x 8 seats).");
        }

        ArrayList<Booking> bookingList = FileHelper.loadBookings(BOOKINGS_FILE);
        applyExistingBookings(showList, bookingList);

        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Enter your choice (1-6): ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number from 1 to 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    addNewShow(scanner, showList);
                    break;
                case 2:
                    viewSeatMap(scanner, showList);
                    break;
                case 3:
                    bookSeats(scanner, showList, bookingList);
                    break;
                case 4:
                    cancelBooking(scanner, showList, bookingList);
                    break;
                case 5:
                    viewBusinessReport(showList, bookingList);
                    break;
                case 6:
                    System.out.println("Thank you for using ScreenSeat. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Please select a valid option (1-6).");
            }
        }
        scanner.close();
    }

    public static void printMainMenu() {
        System.out.println("\n==================================================");
        System.out.println("  ScreenSeat: Cinema Show & Seat Booking System  ");
        System.out.println("==================================================");
        System.out.println("1. Add a Screen & Show");
        System.out.println("2. View Seat Map");
        System.out.println("3. Book Seats");
        System.out.println("4. Cancel Booking");
        System.out.println("5. View Business Report");
        System.out.println("6. Exit");
        System.out.println("==================================================");
    }

    public static void applyExistingBookings(ArrayList<Show> showList, ArrayList<Booking> bookingList) {
        for (int i = 0; i < bookingList.size(); i++) {
            Booking b = bookingList.get(i);
            if (b.getStatus().equals("BOOKED")) {
                Show show = findShowById(showList, b.getShowId());
                if (show != null) {
                    for (int j = 0; j < b.getSeatLabels().size(); j++) {
                        String label = b.getSeatLabels().get(j);
                        int row = Character.toUpperCase(label.charAt(0)) - 'A';
                        int col = Integer.parseInt(label.substring(1)) - 1;
                        if (row >= 0 && row < show.getTotalRows() && col >= 0 && col < show.getSeatsPerRow()) {
                            show.bookSeat(row, col);
                        }
                    }
                }
            }
        }
    }

    public static Show findShowById(ArrayList<Show> showList, int showId) {
        for (int i = 0; i < showList.size(); i++) {
            if (showList.get(i).getShowId() == showId) {
                return showList.get(i);
            }
        }
        return null;
    }

    public static Booking findBookingById(ArrayList<Booking> bookingList, int bookingId) {
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingId() == bookingId) {
                return bookingList.get(i);
            }
        }
        return null;
    }

    public static int getNextBookingId(ArrayList<Booking> bookingList) {
        int maxId = 0;
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingId() > maxId) {
                maxId = bookingList.get(i).getBookingId();
            }
        }
        return maxId + 1;
    }

    public static void listAllShows(ArrayList<Show> showList) {
        System.out.println("\nAvailable Shows:");
        for (int i = 0; i < showList.size(); i++) {
            Show s = showList.get(i);
            System.out.println("ID: " + s.getShowId() + " | Movie: " + s.getMovieName()
                    + " | Time: " + s.getShowTime() + " | Screen: " + s.getTotalRows() + " rows x "
                    + s.getSeatsPerRow() + " seats");
        }
    }

    public static void addNewShow(Scanner scanner, ArrayList<Show> showList) {
        System.out.println("\n--- Add New Screen & Show ---");
        try {
            System.out.print("Enter Show ID (number): ");
            int showId = Integer.parseInt(scanner.nextLine().trim());

            if (findShowById(showList, showId) != null) {
                System.out.println("Show ID already exists! Please use a unique ID.");
                return;
            }

            System.out.print("Enter Movie Name: ");
            String movieName = scanner.nextLine().trim();

            System.out.print("Enter Show Time (e.g. 18:00): ");
            String showTime = scanner.nextLine().trim();

            System.out.print("Enter Number of Rows (e.g. 5): ");
            int rows = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Seats Per Row (e.g. 8): ");
            int seatsPerRow = Integer.parseInt(scanner.nextLine().trim());

            if (rows <= 0 || seatsPerRow <= 0) {
                System.out.println("Rows and seats per row must be greater than zero.");
                return;
            }

            Show newShow = new Show(showId, movieName, showTime, rows, seatsPerRow);
            showList.add(newShow);
            FileHelper.saveShows(showList, SHOWS_FILE);
            System.out.println("Show added successfully and saved to " + SHOWS_FILE);
        } catch (Exception e) {
            System.out.println("Error adding show! Please enter valid details.");
        }
    }

    public static void viewSeatMap(Scanner scanner, ArrayList<Show> showList) {
        if (showList.isEmpty()) {
            System.out.println("No shows available.");
            return;
        }

        listAllShows(showList);
        System.out.print("\nEnter Show ID to view seat map: ");
        try {
            int showId = Integer.parseInt(scanner.nextLine().trim());
            Show show = findShowById(showList, showId);
            if (show == null) {
                System.out.println("Show not found with ID: " + showId);
                return;
            }
            show.displaySeatMap();
        } catch (Exception e) {
            System.out.println("Invalid Show ID.");
        }
    }

    public static void bookSeats(Scanner scanner, ArrayList<Show> showList, ArrayList<Booking> bookingList) {
        if (showList.isEmpty()) {
            System.out.println("No shows available to book.");
            return;
        }

        listAllShows(showList);
        System.out.print("\nEnter Show ID to book: ");
        int showId;
        try {
            showId = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid Show ID.");
            return;
        }

        Show show = findShowById(showList, showId);
        if (show == null) {
            System.out.println("Show not found with ID: " + showId);
            return;
        }

        show.displaySeatMap();

        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine().trim();
        if (customerName.length() == 0) {
            customerName = "Guest";
        }

        System.out.print("How many seats do you want to book? ");
        int seatCount;
        try {
            seatCount = Integer.parseInt(scanner.nextLine().trim());
            if (seatCount <= 0) {
                System.out.println("Seat count must be at least 1.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid number.");
            return;
        }

        ArrayList<String> chosenSeats = new ArrayList<String>();
        double seatTotalPrice = 0.0;

        for (int i = 1; i <= seatCount; i++) {
            System.out.print("Enter Seat " + i + " (e.g. A1, B3): ");
            String seatLabel = scanner.nextLine().trim().toUpperCase();

            if (seatLabel.length() < 2) {
                System.out.println("Invalid seat format! Booking aborted.");
                return;
            }

            char rowChar = seatLabel.charAt(0);
            int row = rowChar - 'A';
            int col;
            try {
                col = Integer.parseInt(seatLabel.substring(1)) - 1;
            } catch (Exception e) {
                System.out.println("Invalid seat number! Booking aborted.");
                return;
            }

            if (row < 0 || row >= show.getTotalRows() || col < 0 || col >= show.getSeatsPerRow()) {
                System.out.println("Seat " + seatLabel + " does not exist on this screen! Booking aborted.");
                return;
            }

            if (show.isSeatBooked(row, col)) {
                System.out.println("Seat " + seatLabel + " is already booked! Booking aborted.");
                return;
            }

            if (chosenSeats.contains(seatLabel)) {
                System.out.println("Seat " + seatLabel + " was already selected in this turn! Booking aborted.");
                return;
            }

            chosenSeats.add(seatLabel);

            if (row < 2) {
                seatTotalPrice += 250.0;
            } else {
                seatTotalPrice += 150.0;
            }
        }

        System.out.println("\n--- Food Combo Menu ---");
        System.out.println("1. Popcorn (Rs 120)");
        System.out.println("2. Popcorn + Coke (Rs 200)");
        System.out.println("3. Nachos (Rs 150)");
        System.out.println("4. No food (Rs 0)");
        System.out.print("Choose a food combo (1-4): ");

        int foodChoice = 4;
        try {
            foodChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            foodChoice = 4;
        }

        String foodCombo = "No food";
        double foodPrice = 0.0;

        if (foodChoice == 1) {
            foodCombo = "Popcorn (Rs 120)";
            foodPrice = 120.0;
        } else if (foodChoice == 2) {
            foodCombo = "Popcorn + Coke (Rs 200)";
            foodPrice = 200.0;
        } else if (foodChoice == 3) {
            foodCombo = "Nachos (Rs 150)";
            foodPrice = 150.0;
        } else {
            foodCombo = "No food";
            foodPrice = 0.0;
        }

        for (int i = 0; i < chosenSeats.size(); i++) {
            String label = chosenSeats.get(i);
            int r = label.charAt(0) - 'A';
            int c = Integer.parseInt(label.substring(1)) - 1;
            show.bookSeat(r, c);
        }

        double totalAmount = seatTotalPrice + foodPrice;
        int bookingId = getNextBookingId(bookingList);

        Booking newBooking = new Booking(bookingId, show.getShowId(), show.getMovieName(),
                show.getShowTime(), customerName, chosenSeats, foodCombo, totalAmount, "BOOKED", 0.0);

        bookingList.add(newBooking);
        FileHelper.saveBookings(bookingList, BOOKINGS_FILE);

        System.out.println("\nBooking successful!");
        newBooking.printTicket();
    }

    public static void cancelBooking(Scanner scanner, ArrayList<Show> showList, ArrayList<Booking> bookingList) {
        System.out.print("\nEnter Booking ID to cancel: ");
        int bookingId;
        try {
            bookingId = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid Booking ID.");
            return;
        }

        Booking booking = findBookingById(bookingList, bookingId);
        if (booking == null) {
            System.out.println("Booking not found with ID: " + bookingId);
            return;
        }

        if (booking.getStatus().equals("CANCELLED")) {
            System.out.println("This booking is already cancelled.");
            return;
        }

        System.out.print("How many hours are left before the show starts? ");
        double hoursLeft;
        try {
            hoursLeft = Double.parseDouble(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid hours entered.");
            return;
        }

        double refundPercent = (hoursLeft >= 2.0) ? 0.80 : 0.50;
        double refundAmount = booking.getTotalAmount() * refundPercent;

        Show show = findShowById(showList, booking.getShowId());
        if (show != null) {
            for (int i = 0; i < booking.getSeatLabels().size(); i++) {
                String label = booking.getSeatLabels().get(i);
                int r = Character.toUpperCase(label.charAt(0)) - 'A';
                int c = Integer.parseInt(label.substring(1)) - 1;
                show.freeSeat(r, c);
            }
        }

        booking.setStatus("CANCELLED");
        booking.setRefundAmount(refundAmount);
        FileHelper.saveBookings(bookingList, BOOKINGS_FILE);

        System.out.println("\n----------------------------------------");
        System.out.println("   BOOKING CANCELLED SUCCESSFULLY       ");
        System.out.println("----------------------------------------");
        System.out.println("Booking ID     : #" + booking.getBookingId());
        System.out.println("Original Total : Rs " + booking.getTotalAmount());
        System.out.println("Hours Left     : " + hoursLeft + " hrs");
        System.out.println("Refund Rate    : " + (int) (refundPercent * 100) + "%");
        System.out.println("Refund Amount  : Rs " + refundAmount);
        System.out.println("Retained by Cinema: Rs " + (booking.getTotalAmount() - refundAmount));
        System.out.println("Seats freed    : " + booking.getSeatLabels());
        System.out.println("----------------------------------------\n");
    }

    public static void viewBusinessReport(ArrayList<Show> showList, ArrayList<Booking> bookingList) {
        System.out.println("\n==========================================================================");
        System.out.println("                     SCREEN SEAT BUSINESS REPORT                          ");
        System.out.println("==========================================================================");

        if (showList.isEmpty()) {
            System.out.println("No shows available to report.");
            return;
        }

        System.out.println("\n--- SHOW-WISE PERFORMANCE ---");
        System.out.printf("%-8s %-22s %-8s %-12s %-12s %-12s\n",
                "Show ID", "Movie Name", "Time", "Occupancy", "Rate %", "Revenue (Rs)");
        System.out.println("--------------------------------------------------------------------------");

        double totalCinemaRevenue = 0.0;

        for (int i = 0; i < showList.size(); i++) {
            Show s = showList.get(i);
            int bookedSeats = s.getBookedSeatCount();
            int totalSeats = s.getTotalSeats();
            double occupancyRate = (totalSeats > 0) ? ((bookedSeats * 100.0) / totalSeats) : 0.0;

            double showRevenue = 0.0;
            for (int b = 0; b < bookingList.size(); b++) {
                Booking booking = bookingList.get(b);
                if (booking.getShowId() == s.getShowId()) {
                    if (booking.getStatus().equals("BOOKED")) {
                        showRevenue += booking.getTotalAmount();
                    } else if (booking.getStatus().equals("CANCELLED")) {
                        showRevenue += (booking.getTotalAmount() - booking.getRefundAmount());
                    }
                }
            }

            totalCinemaRevenue += showRevenue;

            String seatRatio = bookedSeats + "/" + totalSeats;
            System.out.printf("%-8d %-22s %-8s %-12s %-12.1f Rs %-10.2f\n",
                    s.getShowId(), s.getMovieName(), s.getShowTime(), seatRatio, occupancyRate, showRevenue);
        }

        System.out.println("\n--- MOVIE-WISE REVENUE SUMMARY ---");
        ArrayList<String> distinctMovies = new ArrayList<String>();
        for (int i = 0; i < showList.size(); i++) {
            String mName = showList.get(i).getMovieName();
            if (!distinctMovies.contains(mName)) {
                distinctMovies.add(mName);
            }
        }

        for (int m = 0; m < distinctMovies.size(); m++) {
            String movie = distinctMovies.get(m);
            double movieRevenue = 0.0;
            for (int b = 0; b < bookingList.size(); b++) {
                Booking booking = bookingList.get(b);
                if (booking.getMovieName().equalsIgnoreCase(movie)) {
                    if (booking.getStatus().equals("BOOKED")) {
                        movieRevenue += booking.getTotalAmount();
                    } else if (booking.getStatus().equals("CANCELLED")) {
                        movieRevenue += (booking.getTotalAmount() - booking.getRefundAmount());
                    }
                }
            }
            System.out.printf("Movie: %-25s -> Revenue: Rs %.2f\n", movie, movieRevenue);
        }

        int activeCount = 0;
        int cancelledCount = 0;
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getStatus().equals("BOOKED")) {
                activeCount++;
            } else {
                cancelledCount++;
            }
        }

        System.out.println("\n--- OVERALL CINEMA SUMMARY ---");
        System.out.println("Total Bookings Processed : " + bookingList.size());
        System.out.println("Active Confirmed Bookings: " + activeCount);
        System.out.println("Cancelled Bookings       : " + cancelledCount);
        System.out.printf("Net Cinema Revenue       : Rs %.2f\n", totalCinemaRevenue);
        System.out.println("*(Note: Cancelled bookings contribute only the retained cancellation fee)*");
        System.out.println("==========================================================================\n");
    }
}
