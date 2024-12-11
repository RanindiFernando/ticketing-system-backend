package Ticketing.System.service;

/**
 * The Customer class simulates a customer in the ticketing system.
 * A customer periodically attempts to retrieve tickets from the shared ticket pool.
 * This class implements the Runnable interface to enable multithreading.
 */

public class Customer implements Runnable {

    private final TicketService ticketService; // Reference to the TicketService to interact with the ticket pool
    private final int retrievalInterval; // Time interval (in milliseconds) between ticket retrieval attempts
    private final int customerID;  // Unique identifier for the customer

    /**
     * Constructor to initialize the Customer instance.
     *
     * @param ticketService     The TicketService to manage ticket operations.
     * @param retrievalInterval The time interval between ticket retrieval attempts.
     * @param customerID        The unique ID of the customer.
     */

    public Customer(TicketService ticketService, int retrievalInterval, int customerID) {
        this.ticketService = ticketService;
        this.retrievalInterval = retrievalInterval;
        this.customerID = customerID;
    }

    /**
     * The run method is executed when the thread starts.
     * The customer repeatedly attempts to retrieve tickets from the pool
     * at specified intervals while the simulation is running.
     */

    @Override
    public void run() {
        while (ticketService.isSimulationRunning()) { // Check if the simulation is active
            try {
                Thread.sleep(retrievalInterval); // Pause the thread for the specified interval
                ticketService.retrieveTickets(customerID); // Attempt to retrieve tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status of the thread
            }
        }
    }
}
