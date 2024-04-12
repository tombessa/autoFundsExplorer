package com.github.tombessa.autofundsexplorer.rest;

import com.github.tombessa.autofundsexplorer.service.AutoFundsExplorerService;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 *
 * @author antonyonne.bessa
 */

@RestController
@RequestMapping("/api/autoFundsExplorer")
public class AutoFundsController {
    private static final Logger logger = LoggerFactory.getLogger(AutoFundsController.class);
    private final AutoFundsExplorerService autoFundsExplorerService;

    public AutoFundsController(AutoFundsExplorerService autoFundsExplorerService) {
        this.autoFundsExplorerService = autoFundsExplorerService;
    }

    @ApiOperation("Retorna os últimos patrimônios do Ticket")
    @GetMapping("/{ticket}")
    public Object me(@AuthenticationPrincipal User user, @PathVariable("ticket") @NotNull String ticket)  {
        return this.autoFundsExplorerService.patrimonials(ticket);
    }

    @ApiOperation("Retorna o ranking")
    @GetMapping("/ranking")
    public Object me(@AuthenticationPrincipal User user)  {
        return this.autoFundsExplorerService.ranking();
    }

}
