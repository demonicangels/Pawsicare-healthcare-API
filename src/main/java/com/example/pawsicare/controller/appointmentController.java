package com.example.pawsicare.controller;

import com.example.pawsicare.business.DTOs.appointmentDTO;
import com.example.pawsicare.business.impl.appointmentConverter;
import com.example.pawsicare.business.requests.createAppointmentRequest;
import com.example.pawsicare.business.requests.updateAppointmentRequest;
import com.example.pawsicare.business.responses.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
public class appointmentController {

    private final com.example.pawsicare.domain.managerinterfaces.appointmentManager appointmentManager;
    private final com.example.pawsicare.domain.managerinterfaces.doctorManager doctorManager;
    private final com.example.pawsicare.domain.managerinterfaces.clientManager clientManager;
    private final com.example.pawsicare.domain.managerinterfaces.petManager petManager;
    private final appointmentConverter converter;
    private final com.example.pawsicare.business.impl.clientConverter clientConverter;
    private final com.example.pawsicare.business.impl.doctorConverter doctorConverter;
    private final com.example.pawsicare.business.impl.petConverter petConverter;

    @RolesAllowed({"Doctor", "Client"})
    @GetMapping(params = "userId")
    public ResponseEntity<getAppointmentsResponse> getUsersAppointments(@Valid @RequestParam(name = "userId")long userId){
        Optional<List<appointmentDTO>> app = appointmentManager.getUsersAppointments(userId)
                        .map(appointments -> appointments.stream()
                                .map(converter::toDTO)
                                .toList())
                        .or(() -> Optional.of(new ArrayList<>()));


        if(!app.isEmpty()){

            getAppointmentsResponse appointmentsResponse = getAppointmentsResponse.builder()
                    .appointments(app.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(appointmentsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RolesAllowed({"Client"})
    @PostMapping
    public ResponseEntity<createAppointmentResponse> createAppointment(@RequestBody @Valid createAppointmentRequest appointmentRequest){
        appointmentDTO appointment = appointmentDTO.builder()
                .dateAndStart(appointmentRequest.getDateAndStart())
                .dateAndEnd(appointmentRequest.getDateAndEnd())
                .client(clientConverter.toDTO(clientManager.getClient(appointmentRequest.getClientId())))
                .doctor(doctorConverter.toDTO(doctorManager.getDoctor(appointmentRequest.getDoctorId())))
                .pet(petConverter.toDTO(petManager.getPet(appointmentRequest.getPetId())))
                .build();

        Optional<appointmentDTO> app = Optional.ofNullable(appointmentManager.createAppointment(converter.fromDTO(appointment))
                .map(appointmentValue -> converter.toDTO(appointmentValue))
                .orElse(null));

        if(app.isPresent()){
            createAppointmentResponse appointmentResponse = createAppointmentResponse.builder()
                   .appointment(app.get())
                   .build();
         return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResponse);
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @RolesAllowed({"Doctor","Client"})
    @PutMapping(params = "id")
    public ResponseEntity<updateAppointmentResponse> rescheduleAppointment (@Valid @RequestParam(name = "id") long id, @RequestBody @Valid updateAppointmentRequest appointmentRequest){
        appointmentDTO appointment = appointmentDTO.builder()
                .dateAndStart(appointmentRequest.getDateAndStart())
                .dateAndEnd(appointmentRequest.getDateAndEnd())
                .build();

        Optional<appointmentDTO> api = Optional.ofNullable(appointmentManager.rescheduleAppointment(id, converter.fromDTO(appointment)).map(app -> converter.toDTO(app)).orElse(null));

        if(api.isPresent()){
            updateAppointmentResponse updateAppointmentResponse = com.example.pawsicare.business.responses.updateAppointmentResponse.builder()
                    .updatedAppointment(api.get())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateAppointmentResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
    @RolesAllowed({"Doctor","Client"})
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> cancelAppointment(@RequestParam(name = "id")long id){
        appointmentManager.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }
//TODO in order to make an appointment i need everyone's id and to get their values from the database and then save the appointment

//    @PostMapping(params = "test")
//    public ResponseEntity<AppointmentDTO> test(@RequestParam(name="test") @Valid int test) {
//        Optional<Client> clientOptional = Optional.ofNullable(clientManager.getClient(18));
//        Optional<Doctor> doctorOptional = Optional.ofNullable(doctorManager.getDoctor(1));
//
//
//        Optional<PetDTO> peti = Optional.ofNullable(petConverter.toDTO(petManager.getPet(9)));
//
//
//        if (clientOptional.isPresent() && doctorOptional.isPresent()) {
//            AppointmentDTO appointment = AppointmentDTO.builder()
//                    .dateAndStart(LocalDateTime.of(2023, 11, 07,12,0))
//                    .dateAndEnd(LocalDateTime.of(2023, 11, 07,13,0))
//                    .client(clientConverter.toDTO(clientOptional.get()))
//                    .doctor(doctorConverter.toDTO(doctorOptional.get()))
//                    .pet(peti.get())
//                    .build();
//
//            appointmentManager.createAppointment(converter.fromDTO(appointment));
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
//        } else {
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}
