package Ticketing.System.controller;

import Ticketing.System.dto.SimulationConfigDTO;
import Ticketing.System.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/") // Allow Cross-Origin requests from the specified origin
@RestController  // Marks this class as a RESTful controller
@RequestMapping("/api/simulation") // Base path for all endpoints in this controller
public class SimulationController {

    private final TicketService ticketService; // Dependency for handling ticket-related operations

    // Constructor injection for TicketService
    public SimulationController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Sets the total number of vendors for the simulation.
     *
     * @param vendorTotal The total number of vendors
     * @return A confirmation message
     */
    @PostMapping("/setVendorTotal")
    public String setVendorTotal(@RequestBody int vendorTotal) {
        ticketService.setVendorTotal(vendorTotal);
        return "Vendor total set to: " + vendorTotal;
    }

    /**
     * Sets the total number of customers for the simulation.
     *
     * @param customerTotal The total number of customers
     * @return A confirmation message
     */
    @PostMapping("/setCustomerTotal")
    public String setCustomerTotal(@RequestBody int customerTotal) {
        ticketService.setCustomerTotal(customerTotal);
        return "Customer total set to: " + customerTotal;
    }

    /**
     * Starts the simulation with the provided configuration.
     *
     * @param config The simulation configuration (release rate, retrieval rate, and max capacity)
     * @return A confirmation message
     */

    @PostMapping("/start")
    public String startSimulation(@RequestBody SimulationConfigDTO config) {
        ticketService.setSimulationConfig(config.getTicketReleaseRate(),
                config.getTicketRetrievalRate(), config.getMaxTicketCapacity());
        ticketService.simulateVendorsAndCustomers();
        return "Simulation started!";
    }

    /**
     * Stops the running simulation.
     *
     * @return A confirmation message
     */
    @PostMapping("/stop")
    public String stopSimulation() {
        ticketService.stopSimulation();
        return "Simulation stopped!";
    }

    /**
     * Retrieves the remaining tickets in the simulation.
     *
     * @return A map containing the count of remaining tickets
     */
    @GetMapping("/tickets")
    public Map<String, Integer> getRemainingTickets() {
        return Map.of("remainingTickets", ticketService.getRemainingTickets());
    }

    /**
     * Endpoint to fetch transaction logs from the JSON file.
     *
     * @return The content of the transaction logs as JSON.
     * @throws IOException If an error occurs while reading the file
     */
    @GetMapping("/transactions")
    public ResponseEntity<String> getTransactions() throws IOException {
        String filePath = "C:/Users/HKCS/OneDrive/Desktop/OOP SD2 CW/System/transaction_logs.json";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return ResponseEntity.ok(content);
    }
}
