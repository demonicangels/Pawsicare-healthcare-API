package com.example.PawsiCare.controller;


import com.example.PawsiCare.business.requests.SendEmailRequest;
import com.example.PawsiCare.domain.managerInterfaces.EmailManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/contacts")
@AllArgsConstructor
public class EmailController {

    private final EmailManager emailManager;
    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestBody @Valid SendEmailRequest emailRequest)
    {
        try{
            emailManager.sendEmail(emailRequest);
            return ResponseEntity.ok("Email sent successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
