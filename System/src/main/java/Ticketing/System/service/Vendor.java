package Ticketing.System.service;

/**
 * The Vendor class simulates a vendor in the ticketing system.
 * A vendor periodically adds tickets to the shared ticket pool at a specified interval.
 * This class implements the Runnable interface to enable multithreading.
 */

public class Vendor implements Runnable {

    private final TicketService ticketService; // Reference to the TicketService to interact with the ticket pool
    private final int releaseInterval; // Time interval (in milliseconds) between ticket releases
    private final int vendorID; // Unique identifier for the vendor

    /**
     * Constructor to initialize the Vendor instance.
     *
     * @param ticketService   The TicketService to manage ticket operations.
     * @param releaseInterval The time interval between ticket releases.
     * @param vendorID        The unique ID of the vendor.
     */

    public Vendor(TicketService ticketService, int releaseInterval, int vendorID) {
        this.ticketService = ticketService;
        this.releaseInterval = releaseInterval;
        this.vendorID = vendorID;
    }

    /**
     * The run method is executed when the thread starts.
     * The vendor repeatedly adds tickets to the pool at specified intervals
     * while the simulation is running.
     */

    @Override
    public void run() {
        while (ticketService.isSimulationRunning()) { // Check if the simulation is active
            try {
                Thread.sleep(releaseInterval);  // Pause the thread for the specified interval
                ticketService.addTickets(vendorID); // Add tickets to the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore the interrupted status of the thread
            }
        }
    }
}

