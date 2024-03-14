package com.bank.payment.service;

import java.util.*;
import com.bank.payment.dto.model.AuctionLogsDTO;

public interface AuctionLogsService {
    List<AuctionLogsDTO> getAllAuctionLogs();
    AuctionLogsDTO getAuctionLogById(Long id);
    AuctionLogsDTO createNewAuctionLog(AuctionLogsDTO auctionLogsDTO);
    void deleteLogById(Long id);

}
