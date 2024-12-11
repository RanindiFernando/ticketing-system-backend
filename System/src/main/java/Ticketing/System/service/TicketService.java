package Ticketing.System.service;

import Ticketing.System.model.TicketRecord;
import Ticketing.System.repo.TicketRecordRepo;
import Ticketing.System.util.JSONFileWriter;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The TicketService class manages the simulation of vendors and customers
 * interacting with a shared ticket pool. It handles adding and retrieving
 * tickets, logging transactions, and managing the simulation's lifecycle.
 */
@Service
public class TicketService {

    private final TicketRecordRepo ticketRecordRepository; // Repository to manage ticket records
    private final List<JsonObject> transactionLogs = new ArrayList<>(); // List to store transaction logs
    private static final String LOGS_FILE = "C:/Users/HKCS/OneDrive/Desktop/OOP SD2 CW/System/transaction_logs.json"; // Path to save transaction logs

    private int vendorTotal; // Total number of vendors
    private int customerTotal; // Total number of customers
    private int ticketReleaseRate; // Rate at which vendors release tickets
    private int customerRetrievalRate; // Rate at which customers retrieve tickets
    private int maxTicketCapacity; // Maximum capacity of the ticket pool
    private int remainingTickets = 0; // Tickets remaining in the pool
    private int currentTicketId = 0; // Counter for ticket IDs
    private boolean isSimulationRunning = false; // Flag indicating if the simulation is running
    private boolean isMaxCapacityReached = false; // Flag indicating if max ticket capacity is reached
    private boolean isUserStopped = false; // Flag for manual stop by user
    private boolean isSimulationAutomaticallyStopped = false; // Flag for automatic simulation stop

    private final ArrayList<Thread> vendorThreads = new ArrayList<>(); // List of vendor threads
    private final ArrayList<Thread> customerThreads = new ArrayList<>(); // List of customer threads

    /**
     * Constructor to initialize the TicketService with a ticket record repository.
     *
     * @param ticketRecordRepository The repository to manage ticket records.
     */
    public TicketService(TicketRecordRepo ticketRecordRepository) {
        this.ticketRecordRepository = ticketRecordRepository;
    }

    /**
     * Sets the configuration for the simulation.
     *
     * @param ticketReleaseRate    The rate at which tickets are released.
     * @param customerRetrievalRate The rate at which tickets are retrieved.
     * @param maxTicketCapacity     The maximum capacity of the ticket pool.
     */
    public void setSimulationConfig(int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Sets the total number of vendors.
     *
     * @param vendorTotal The total number of vendors.
     */
    public void setVendorTotal(int vendorTotal) {
        this.vendorTotal = vendorTotal;
    }

    /**
     * Sets the total number of customers.
     *
     * @param customerTotal The total number of customers.
     */
    public void setCustomerTotal(int customerTotal) {
        this.customerTotal = customerTotal;
    }

    /**
     * Starts the simulation by creating and starting vendor and customer threads.
     */
    public void simulateVendorsAndCustomers() {
        isSimulationRunning = true;

        // Create and start vendor threads
        for (int i = 1; i <= vendorTotal; i++) {
            Vendor vendor = new Vendor(this, ticketReleaseRate, i);
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads
        for (int i = 1; i <= customerTotal; i++) {
            Customer customer = new Customer(this, customerRetrievalRate, i);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
        }
    }

    /**
     * Stops the simulation by interrupting all threads and saving logs.
     */
    public void stopSimulation() {
        if (!isSimulationAutomaticallyStopped && !isUserStopped) {
            System.out.println("User stopped the simulation.");
            isUserStopped = true;
        }

        isSimulationRunning = false;

        // Interrupt all threads
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);

        // Wait for all threads to finish
        try {
            for (Thread thread : vendorThreads) {
                thread.join();
            }
            for (Thread thread : customerThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        vendorThreads.clear();
        customerThreads.clear();

        // Save transaction logs to file
        JSONFileWriter.writeLogsToJSON(transactionLogs, LOGS_FILE);
    }

    /**
     * Adds tickets to the pool by a vendor.
     *
     * @param vendorID The ID of the vendor adding tickets.
     */
    public synchronized void addTickets(int vendorID) {
        while (!isSimulationRunning) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (isMaxCapacityReached) {
            return;
        }

        int ticketsToAdd = Math.min(5, maxTicketCapacity - currentTicketId);
        for (int i = 0; i < ticketsToAdd; i++) {
            if (currentTicketId < maxTicketCapacity) {
                currentTicketId++;
                remainingTickets++;
                try {
                    TicketRecord ticket = new TicketRecord((long) vendorID, null);
                    ticketRecordRepository.save(ticket);

                    // Log the transaction
                    JsonObject logEntry = new JsonObject();
                    logEntry.addProperty("actionType", "ADD");
                    logEntry.addProperty("entityName", "Vendor-" + vendorID);
                    logEntry.addProperty("ticketCount", 1);
                    logEntry.addProperty("remainingTickets", remainingTickets);
                    transactionLogs.add(logEntry);
                } catch (Exception e) {
                    System.out.println("Error saving ticket to repository: " + e.getMessage());
                }
            }
        }
        System.out.println("Vendor " + vendorID + " added " + ticketsToAdd + " tickets. Current Pool: " + remainingTickets);

        if (currentTicketId >= maxTicketCapacity) {
            isMaxCapacityReached = true;
            System.out.println("Max capacity reached. Vendor " + vendorID + " stops adding tickets.");
        }

        notifyAll();
    }

    /**
     * Retrieves tickets from the pool by a customer.
     *
     * @param customerID The ID of the customer retrieving tickets.
     */
    public synchronized void retrieveTickets(int customerID) {
        while (remainingTickets <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        int ticketsToRetrieve = Math.min(4, remainingTickets);
        for (int i = 0; i < ticketsToRetrieve; i++) {
            remainingTickets--;
            TicketRecord ticket = ticketRecordRepository.findFirstByVendorIdNotNullAndCustomerIdNull();

            if (ticket != null) {
                ticket.setCustomerId((long) customerID);
                try {
                    ticketRecordRepository.save(ticket);

                    // Log the transaction
                    JsonObject logEntry = new JsonObject();
                    logEntry.addProperty("actionType", "RETRIEVE");
                    logEntry.addProperty("entityName", "Customer-" + customerID);
                    logEntry.addProperty("ticketCount", 1);
                    logEntry.addProperty("remainingTickets", remainingTickets);
                    transactionLogs.add(logEntry);

                } catch (Exception e) {
                    System.out.println("Error saving ticket to repository: " + e.getMessage());
                }
            }
        }
        System.out.println("Customer " + customerID + " retrieved " + ticketsToRetrieve + " tickets. Current Pool: " + remainingTickets);

        if (isMaxCapacityReached && remainingTickets == 0) {
            System.out.println("Simulation Ended");
            isSimulationAutomaticallyStopped = true;
            stopSimulation();
        }

        notifyAll();
    }

    /**
     * Checks if the simulation is currently running.
     *
     * @return True if the simulation is running, false otherwise.
     */
    public boolean isSimulationRunning() {
        return isSimulationRunning;
    }

    /**
     * Retrieves the number of tickets remaining in the pool.
     *
     * @return The remaining ticket count.
     */
    public int getRemainingTickets() {
        return remainingTickets;
    }
}
