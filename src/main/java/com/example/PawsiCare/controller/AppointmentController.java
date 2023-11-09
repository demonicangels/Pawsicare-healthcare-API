package com.example.PawsiCare.controller;

import com.example.PawsiCare.business.DTOs.AppointmentDTO;
import com.example.PawsiCare.business.impl.AppointmentConverter;
import com.example.PawsiCare.business.impl.ClientConverter;
import com.example.PawsiCare.business.impl.DoctorConverter;
import com.example.PawsiCare.business.impl.PetConverter;
import com.example.PawsiCare.domain.managerInterfaces.AppointmentManager;
import com.example.PawsiCare.business.requests.CreateAppointmentRequest;
import com.example.PawsiCare.business.requests.UpdateAppointmentRequest;
import com.example.PawsiCare.business.responses.*;
import com.example.PawsiCare.domain.managerInterfaces.ClientManager;
import com.example.PawsiCare.domain.managerInterfaces.DoctorManager;
import com.example.PawsiCare.domain.managerInterfaces.PetManager;
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
public class AppointmentController {

    private final AppointmentManager appointmentManager;
    private final DoctorManager doctorManager;
    private final ClientManager clientManager;
    private final PetManager petManager;
    private final AppointmentConverter converter;
    private final ClientConverter clientConverter;
    private final DoctorConverter doctorConverter;
    private final PetConverter petConverter;
    @GetMapping(params = "userId")
    public ResponseEntity<GetAppointmentsResponse> getUsersAppointments(@Valid @RequestParam(name = "userId")long userId){
        Optional<List<AppointmentDTO>> app = Optional.ofNullable(appointmentManager.getUsersAppointments(userId).get().stream()
                .map(converter :: toDTO)
                .toList());

        if(!app.isEmpty()){

            GetAppointmentsResponse appointmentsResponse = GetAppointmentsResponse.builder()
                    .appointments(app.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(appointmentsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody @Valid CreateAppointmentRequest appointmentRequest){
        AppointmentDTO appointment = AppointmentDTO.builder()
                .dateAndStart(appointmentRequest.getDateAndStart())
                .dateAndEnd(appointmentRequest.getDateAndEnd())
                .client(clientConverter.toDTO(clientManager.getClient(appointmentRequest.getClientId())))
                .doctor(doctorConverter.toDTO(doctorManager.getDoctor(appointmentRequest.getDoctorId())))
                .pet(petConverter.toDTO(petManager.getPet(appointmentRequest.getPetId())))
                .build();

        Optional<AppointmentDTO> app = Optional.ofNullable(converter.toDTO(appointmentManager.createAppointment(converter.fromDTO((appointment))).get()));

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
        AppointmentDTO appointment = AppointmentDTO.builder()
                .dateAndStart(appointmentRequest.getDateAndStart())
                .dateAndEnd(appointmentRequest.getDateAndEnd())
                .build();

        Optional<AppointmentDTO> api = Optional.ofNullable(converter.toDTO(appointmentManager.rescheduleAppointment(id, converter.fromDTO(appointment)).get()));
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
