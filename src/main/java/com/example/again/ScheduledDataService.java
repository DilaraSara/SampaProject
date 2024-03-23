package com.example.again;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledDataService {

    @Scheduled(fixedRate = 10000) // Örneğin her 10 saniyede bir çalışır
    public void fetchDataRegularly() {
        // Veri çekme işlemini burada yapın
    }
}
