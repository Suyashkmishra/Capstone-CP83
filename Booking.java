import java.util.ArrayList;

public class Booking {
    private int bookingId;
    private int showId;
    private String movieName;
    private String showTime;
    private String customerName;
    private ArrayList<String> seatLabels;
    private String foodCombo;
    double totalAmount;
    private String status;
    private double refundAmount;

    public Booking(int bookingId, int showId, String movieName, String showTime, String customerName,
                   ArrayList<String> seatLabels, String foodCombo, double totalAmount, String status, double refundAmount) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.movieName = movieName;
        this.showTime = showTime;
        this.customerName = customerName;
        this.seatLabels = seatLabels;
        this.foodCombo = foodCombo;
        this.totalAmount = totalAmount;
        this.status = status;
        this.refundAmount = refundAmount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getShowId() {
        return showId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<String> getSeatLabels() {
        return seatLabels;
    }

    public String getFoodCombo() {
        return foodCombo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void printTicket() {
        System.out.println("\n========================================");
        System.out.println("            CINEMA TICKET               ");
        System.out.println("========================================");
        System.out.println("Booking ID    : #" + bookingId);
        System.out.println("Customer Name : " + customerName);
        System.out.println("Movie         : " + movieName);
        System.out.println("Show Time     : " + showTime);
        System.out.print("Seats         : ");
        for (int i = 0; i < seatLabels.size(); i++) {
            System.out.print(seatLabels.get(i) + (i < seatLabels.size() - 1 ? ", " : ""));
        }
        System.out.println();
        System.out.println("Food Combo    : " + foodCombo);
        System.out.println("Total Amount  : Rs " + totalAmount);
        System.out.println("Status        : " + status);
        if (status.equals("CANCELLED")) {
            System.out.println("Refund Amount : Rs " + refundAmount);
        }
        System.out.println("========================================\n");
    }
}
