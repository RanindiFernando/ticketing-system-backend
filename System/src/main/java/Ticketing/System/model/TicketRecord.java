package Ticketing.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ticketId for each record
    private Long ticketId;  // Unique ticket ID
    private Long vendorId;  // Vendor who added the ticket
    private Long customerId; // Customer who retrieved the ticket (null initially)

    // No-argument constructor for JPA
    public TicketRecord() {}

    // Constructor to initialize ticket with vendorId and customerId
    public TicketRecord(Long vendorId, Long customerId) {
        this.vendorId = vendorId;
        this.customerId = customerId;
    }

    // Getters and Setters
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
