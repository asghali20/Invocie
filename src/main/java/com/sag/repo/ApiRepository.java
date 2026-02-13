package com.sag.repo;

import com.sag.model.v1.Client;
import com.sag.model.v1.Invoice;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApiRepository {

    private final ConcurrentHashMap<String, Object> dataMap;

    @Autowired
    public ApiRepository(ConcurrentHashMap<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public Boolean addClient(Client client) {
        dataMap.put(client.getId(), client);

        return true;
    }

    public Client getClient(String id) {
        return (Client) dataMap.get(id);
    }

    public Boolean addInvoice(Invoice invoice) {

        dataMap.put(invoice.getId(), invoice);

        return true;
    }

    public Invoice getInvoice(String id) {
        return (Invoice) dataMap.get(id);
    }

    public List<Invoice> getInvoicesForClient(String clientId) {
        List<Invoice> invoices = new ArrayList<>();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            try {
                if (entry.getValue() instanceof Invoice) {

                    if (((Invoice) entry.getValue()).getClientId().equals(clientId)) {

                        invoices.add((Invoice) entry.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return invoices;
    }

}
