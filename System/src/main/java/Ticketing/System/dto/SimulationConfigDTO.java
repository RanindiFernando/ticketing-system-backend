package Ticketing.System.dto;


/**
 * Data Transfer Object (DTO) for simulation configuration.
 * Used to pass configuration parameters between the frontend and backend.
 */

public class SimulationConfigDTO {


    private int ticketReleaseRate;    // Time interval (in milliseconds) between ticket releases by vendors
    private int ticketRetrievalRate; // Time interval (in milliseconds) between ticket retrieval by customers
    private int maxTicketCapacity;   // Maximum number of tickets the system can hold

    // Getters and setters for the fields to access and modify the configuration parameters.
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public void setTicketRetrievalRate(int ticketRetrievalRate) {
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}


