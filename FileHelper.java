import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileHelper {

    public static void saveShows(ArrayList<Show> showList, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < showList.size(); i++) {
                Show s = showList.get(i);
                writer.write(s.getShowId() + "," + s.getMovieName() + "," + s.getShowTime() + ","
                        + s.getTotalRows() + "," + s.getSeatsPerRow() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving shows to file: " + e.getMessage());
        }
    }

    public static ArrayList<Show> loadShows(String fileName) {
        ArrayList<Show> showList = new ArrayList<Show>();
        File file = new File(fileName);
        if (!file.exists()) {
            return showList;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int showId = Integer.parseInt(parts[0]);
                    String movieName = parts[1];
                    String showTime = parts[2];
                    int totalRows = Integer.parseInt(parts[3]);
                    int seatsPerRow = Integer.parseInt(parts[4]);

                    Show show = new Show(showId, movieName, showTime, totalRows, seatsPerRow);
                    showList.add(show);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading shows file: " + e.getMessage());
        }
        return showList;
    }

    public static void saveBookings(ArrayList<Booking> bookingList, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < bookingList.size(); i++) {
                Booking b = bookingList.get(i);
                String seatsJoined = "";
                for (int j = 0; j < b.getSeatLabels().size(); j++) {
                    seatsJoined += b.getSeatLabels().get(j);
                    if (j < b.getSeatLabels().size() - 1) {
                        seatsJoined += ";";
                    }
                }
                writer.write(b.getBookingId() + "," + b.getShowId() + "," + b.getMovieName() + ","
                        + b.getShowTime() + "," + b.getCustomerName() + "," + seatsJoined + ","
                        + b.getFoodCombo() + "," + b.getTotalAmount() + "," + b.getStatus() + ","
                        + b.getRefundAmount() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving bookings to file: " + e.getMessage());
        }
    }

    public static ArrayList<Booking> loadBookings(String fileName) {
        ArrayList<Booking> bookingList = new ArrayList<Booking>();
        File file = new File(fileName);
        if (!file.exists()) {
            return bookingList;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 10) {
                    int bookingId = Integer.parseInt(parts[0]);
                    int showId = Integer.parseInt(parts[1]);
                    String movieName = parts[2];
                    String showTime = parts[3];
                    String customerName = parts[4];

                    ArrayList<String> seatLabels = new ArrayList<String>();
                    String[] seats = parts[5].split(";");
                    for (int s = 0; s < seats.length; s++) {
                        if (seats[s].length() > 0) {
                            seatLabels.add(seats[s]);
                        }
                    }

                    String foodCombo = parts[6];
                    double totalAmount = Double.parseDouble(parts[7]);
                    String status = parts[8];
                    double refundAmount = Double.parseDouble(parts[9]);

                    Booking booking = new Booking(bookingId, showId, movieName, showTime, customerName,
                            seatLabels, foodCombo, totalAmount, status, refundAmount);
                    bookingList.add(booking);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading bookings file: " + e.getMessage());
        }
        return bookingList;
    }
}
