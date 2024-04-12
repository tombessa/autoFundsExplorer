package com.github.tombessa.autofundsexplorer.rest;

import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import com.google.gson.JsonArray;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author antonyonne.bessa
 */

@RestController
@RequestMapping("/api/autoFundsExplorer")
public class AutoFundsController {
    private static final Logger logger = LoggerFactory.getLogger(AutoFundsController.class);
    private final AutoFundsExplorerService autoFundsExplorerService;
    private final MapperFacade mapperFacade;

    public AutoFundsController(AutoFundsExplorerService autoFundsExplorerService, MapperFacade mapperFacade) {
        this.autoFundsExplorerService = autoFundsExplorerService;
        this.mapperFacade = mapperFacade;
    }

    @ApiOperation("Retorna os últimos patrimônios do Ticket")
    @GetMapping("/{ticket}")
    public List<PatrimonialDTO> patrimonials(@AuthenticationPrincipal User user, @PathVariable("ticket") @NotNull String ticket)  {
        List<PatrimonialDTO> ret = new ArrayList<>();
        ret = this.autoFundsExplorerService.patrimonials(ticket, ret);
        return ret;
    }

    @ApiOperation("Retorna o ranking")
    @GetMapping("/ranking")
    public JsonArray ranking(@AuthenticationPrincipal User user)  {
        return this.autoFundsExplorerService.ranking();
    }

}
