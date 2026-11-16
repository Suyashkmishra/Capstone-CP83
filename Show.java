public class Show {
    private int showId;
    private String movieName;
    private String showTime;
    private int totalRows;
    private int seatsPerRow;
    private boolean[][] seatBooked;

    public Show(int showId, String movieName, String showTime, int totalRows, int seatsPerRow) {
        this.showId = showId;
        this.movieName = movieName;
        this.showTime = showTime;
        this.totalRows = totalRows;
        this.seatsPerRow = seatsPerRow;
        this.seatBooked = new boolean[totalRows][seatsPerRow];
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

    public int getTotalRows() {
        return totalRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public boolean isSeatBooked(int row, int col) {
        return seatBooked[row][col];
    }

    public void bookSeat(int row, int col) {
        seatBooked[row][col] = true;
    }

    public void freeSeat(int row, int col) {
        seatBooked[row][col] = false;
    }

    public int getTotalSeats() {
        return totalRows * seatsPerRow;
    }

    public int getBookedSeatCount() {
        int count = 0;
        for (int r = 0; r < totalRows; r++) {
            for (int c = 0; c < seatsPerRow; c++) {
                if (seatBooked[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void displaySeatMap() {
        System.out.println("\n----------------- SEAT MAP FOR SHOW " + showId + " (" + movieName + ") -----------------");
        System.out.print("       ");
        for (int c = 1; c <= seatsPerRow; c++) {
            System.out.print(" " + c + "  ");
        }
        System.out.println();

        for (int r = 0; r < totalRows; r++) {
            char rowLetter = (char) ('A' + r);
            String category = (r < 2) ? "Premium (Rs 250)" : "Classic (Rs 150)";
            System.out.printf("Row %c [%-17s]: ", rowLetter, category);

            for (int c = 0; c < seatsPerRow; c++) {
                if (seatBooked[r][c]) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------- [SCREEN THIS WAY] -------------------------\n");
    }
}
