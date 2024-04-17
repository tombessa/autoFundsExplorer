package com.github.tombessa.autofundsexplorer.rest;

import com.github.tombessa.autofundsexplorer.model.dto.FIIDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PatrimonialDTO;
import com.github.tombessa.autofundsexplorer.model.dto.PropriedadeDTO;
import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import com.github.tombessa.autofundsexplorer.util.Constants;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @ApiOperation("Retorna as propriedades")
    @GetMapping("/propriedades/{ticket}")
    public List<PropriedadeDTO>  propriedades(@AuthenticationPrincipal User user, @PathVariable("ticket") @NotNull String ticket){
        List<PropriedadeDTO> ret = new ArrayList<>();
        ret = this.autoFundsExplorerService.propriedades(ticket, ret);
        return ret;
    }

    @ApiOperation("Retorna o ranking")
    @GetMapping("/ranking")
    public List<FIIDTO> ranking(@AuthenticationPrincipal User user,
                                @RequestParam String dataInicio,
                                @RequestParam String dataFim)  {
        List<FIIDTO> ret = new ArrayList<>();
        LocalDate oInicio=null;
        LocalDate oFim=null;
        DateTimeFormatter oFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        if(dataInicio!=null) oInicio = LocalDate.parse(dataInicio, oFormatter);
        if(dataFim!=null) oFim = LocalDate.parse(dataFim, oFormatter);
        if((oInicio!=null)&&(oFim==null)) oFim = LocalDate.now();

        if((oInicio!=null)&&(oInicio!=null)){
            ret = this.autoFundsExplorerService.rankingHistorico(ret, oInicio, oFim);
        }else{
            ret = this.autoFundsExplorerService.ranking(ret);
        }
        return ret;
    }

}
