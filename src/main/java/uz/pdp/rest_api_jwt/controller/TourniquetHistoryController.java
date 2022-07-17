package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.payload.TourniquetHistoryDto;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.TourniquetHistoryService;
import java.util.UUID;


@RestController
@RequestMapping("/api/tourniquetHistory")
public class TourniquetHistoryController {


    @Autowired
    TourniquetHistoryService tourniquetHistoryService;


    @PreAuthorize(value ="hasAnyRole('DIRECTOR','MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?>getTourniquetHistory(@PathVariable UUID id){
        ApiResponse tourniquetHistory = tourniquetHistoryService.getTourniquetHistory(id);
        return ResponseEntity.status(tourniquetHistory.isSuccess()?200:409).body(tourniquetHistory);
    }


    @PreAuthorize(value ="hasAnyRole('MANAGER','DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getTourniquetHistory(){
        ApiResponse getTourniquetCards= tourniquetHistoryService.getTourniquetHistory();
        return ResponseEntity.status(getTourniquetCards.isSuccess()?200:409).body(getTourniquetCards);
    }


   // @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?>addTourniquetHistory(@RequestBody TourniquetHistoryDto historyDto){
        ApiResponse apiResponse = tourniquetHistoryService.addTourniquetHistory(historyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?>editTourniquetHistory(@PathVariable UUID id, @RequestBody TourniquetHistoryDto historyDto){
        ApiResponse apiResponse = tourniquetHistoryService.editTourniquetHistory(id, historyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteTourniquetHistory(@PathVariable UUID id){
        ApiResponse apiResponse = tourniquetHistoryService.deleteTourniquetHistory(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
