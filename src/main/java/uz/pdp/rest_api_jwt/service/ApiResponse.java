package uz.pdp.rest_api_jwt.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.rest_api_jwt.entity.TourniquetCard;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse {

    private String message;
    private boolean success;
    private Object object;

    private List<Object> all;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object=object;
    }


    public ApiResponse(List<Object> all, boolean success, String message) {
        this.message=message;
        this.success=success;
        this.all= all;
    }



}
