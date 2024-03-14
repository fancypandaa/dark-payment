package com.bank.payment.dto.mapper;

import com.bank.payment.domain.AuctionLogs;
import com.bank.payment.dto.model.AuctionLogsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuctionLogsMapper {
    AuctionLogsMapper INSTANCE = Mappers.getMapper(AuctionLogsMapper.class);
    AuctionLogsDTO auctionLogsToAuctionLogsDTO(AuctionLogs auctionLogs);
    AuctionLogs auctionLogsDTOToAuctionLogs(AuctionLogsDTO auctionLogsDTO);

}
