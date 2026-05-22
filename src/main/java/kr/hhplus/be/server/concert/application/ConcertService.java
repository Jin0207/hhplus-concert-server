package kr.hhplus.be.server.concert.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.concert.domain.model.Concert;
import kr.hhplus.be.server.concert.domain.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;
    
    public Concert getConcert(Long id){
        Concert concert = concertRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.CONCERT_NOT_FOUND));
        
        log.debug("콘서트 정보 - {}", concert);
        return concert;
    }

    public List<Concert> getConcertList(){
        List<Concert> concerts = concertRepository.findConcerts(null);
        return concerts;
    }
}
