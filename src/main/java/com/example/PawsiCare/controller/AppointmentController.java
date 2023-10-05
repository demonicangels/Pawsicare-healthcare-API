package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.AppointmentManager;
import com.example.PawsiCare.business.domain.Appointment;
import com.example.PawsiCare.business.requests.CreateAppointmentRequest;
import com.example.PawsiCare.business.requests.UpdateAppointmentRequest;
import com.example.PawsiCare.business.responses.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentManager appointmentManager;

    @GetMapping(params = "userId")
    public ResponseEntity<GetAppointmentsResponse> getUsersAppointments(@Valid @RequestParam(name = "userId")long userId){
        Optional<List<Appointment>> app = Optional.ofNullable(appointmentManager.getUsersAppointments(userId));
        if(app.isPresent()){

            GetAppointmentsResponse appointmentsResponse = GetAppointmentsResponse.builder()
                    .appointments(app.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(appointmentsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody @Valid CreateAppointmentRequest appointmentRequest){
        Appointment appointment = Appointment.builder()
                .dateAndTime(appointmentRequest.getDateAndTime())
                .clientId(appointmentRequest.getClientId())
                .doctorId(appointmentRequest.getDoctorId())
                .petId(appointmentRequest.getPetId())
                .build();

        Optional<Appointment> app = Optional.ofNullable(appointmentManager.createAppointment(appointment));

        if(app.isPresent()){
            CreateAppointmentResponse appointmentResponse = CreateAppointmentResponse.builder()
                   .appointment(app.get())
                   .build();
         return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResponse);
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping(params = "id")
    public ResponseEntity<UpdateAppointmentResponse> rescheduleAppointment (@Valid @RequestParam(name = "id") long id, @RequestBody @Valid UpdateAppointmentRequest appointmentRequest){
        Appointment appointment = Appointment.builder()
                .dateAndTime(appointmentRequest.getDateAndTime())
                .build();

        Optional<Appointment> api = Optional.ofNullable(appointmentManager.rescheduleAppointment(id, appointment));
        if(api.isPresent()){
            UpdateAppointmentResponse updateAppointmentResponse = UpdateAppointmentResponse.builder()
                    .updatedAppointment(api.get())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateAppointmentResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> cancelAppointment(@RequestParam(name = "id")long id){
        appointmentManager.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }



}
