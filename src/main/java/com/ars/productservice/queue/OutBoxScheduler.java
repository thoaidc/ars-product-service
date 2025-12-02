package com.ars.productservice.queue;

import com.ars.productservice.constants.OutBoxConstants;
import com.ars.productservice.service.OutBoxService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutBoxScheduler {
    private final OutBoxService outBoxService;

    public OutBoxScheduler(OutBoxService outBoxService) {
        this.outBoxService = outBoxService;
    }

    @Transactional
    @Scheduled(fixedDelay = OutBoxConstants.DELAY_TIME)
    public void sendOutBoxEvent() {
        outBoxService.processOutBoxEvent();
    }
}
