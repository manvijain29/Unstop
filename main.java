import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coach {
    private int rows;
    private int seatsPerRow;
    private int lastRowSeats;
    private List<Seat> bookedSeats;

    public Coach(int rows, int seatsPerRow, int lastRowSeats) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.lastRowSeats = lastRowSeats;
        this.bookedSeats = new ArrayList<>();
    }

    public boolean isAvailable(int row, int seat) {
        if (row < 1 || row > rows) {
            return false;
        }
        if (row == rows) {
            if (seat < 1 || seat > lastRowSeats) {
                return false;
            }
            return !bookedSeats.contains(new Seat(row, seat));
        }
        if (seat < 1 || seat > seatsPerRow) {
            return false;
        }
        return !bookedSeats.contains(new Seat(row, seat));
    }

    public List<Integer> bookSeats(int numSeats) {
        List<Seat> availableSeats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int seat = 1; seat <= (row == rows ? lastRowSeats : seatsPerRow); seat++) {
                if (isAvailable(row, seat)) {
                    availableSeats.add(new Seat(row, seat));
                } else {
                    availableSeats.clear();
                }
                if (availableSeats.size() == numSeats) {
                    for (Seat bookedSeat : availableSeats) {
                        bookedSeats.add(bookedSeat);
                    }
                    List<Integer> bookedSeatNumbers = new ArrayList<>();
                    for (Seat bookedSeat : availableSeats) {
                        bookedSeatNumbers.add(bookedSeat.getSeatNumber());
                    }
                    return bookedSeatNumbers;
                }
            }
        }
        return null;
    }

    public void printStatus() {
        for (int row = 1; row <= rows; row++) {
            for (int seat = 1; seat <= (row == rows ? lastRowSeats : seatsPerRow); seat++) {
                if (isAvailable(row, seat)) {
                    System.out.print(seat + " ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = 10;
        int seatsPerRow = 7;
        int lastRowSeats = 3;

        Coach coach = new Coach(rows, seatsPerRow, lastRowSeats);

        // Book some random seats to start with
        for (int i = 0; i < Math.random() * 40 + 10; i++) {
            int row = (int) (Math.random() * rows) + 1;
            int seat = (int) (Math.random() * (row == rows ? lastRowSeats : seatsPerRow)) + 1;
            if (coach.isAvailable(row, seat)) {
                coach.bookedSeats.add(new Seat(row, seat));
            }
        }

        while (true) {
            System.out.print("Enter number of seats to book (0 to exit): ");
            int numSeats = scanner.nextInt();
            if (numSeats == 0) {
                break;
            }

            List<Integer> bookedSeatNumbers = coach.bookSeats(numSeats);
            if (bookedSeatNumbers == null) {
                System.out.println("Sorry, no seats available.");
            } else {
                System.out.print("Booked seats: ");
