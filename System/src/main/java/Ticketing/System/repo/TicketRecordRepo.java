package Ticketing.System.repo;

import Ticketing.System.model.TicketRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRecordRepo extends JpaRepository<TicketRecord, Long> {

    // Custom method to find the first ticket added by a vendor (not yet retrieved by a customer)
    TicketRecord findFirstByVendorIdNotNullAndCustomerIdNull();
}

