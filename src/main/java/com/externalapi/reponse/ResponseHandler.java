package com.externalapi.reponse;
import com.externalapi.entity.Television;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*In this class i create a custom response
 with the data, message and info to retrieve in the controller
 */
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

}