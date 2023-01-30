package com.adidas.common.prioritysaleservice.job;


import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.service.RestService;
import com.adidas.common.service.impl.RestServiceImpl;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter @Setter
@Builder
public class EmailJob extends AdiClubMemberInfoDto implements Runnable {
    private final Runnable routineToExecute;

    @SneakyThrows
    @Override
    public void run() {
        routineToExecute.run();
    }

}
