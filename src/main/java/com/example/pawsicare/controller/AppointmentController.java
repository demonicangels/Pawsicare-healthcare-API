package com.example.pawsicare.controller;

import com.example.pawsicare.business.dto.AppointmentDTO;
import com.example.pawsicare.business.converters.AppointmentConverter;
import com.example.pawsicare.business.converters.ClientConverter;
import com.example.pawsicare.business.converters.DoctorConverter;
import com.example.pawsicare.business.converters.PetConverter;
import com.example.pawsicare.business.requests.CreateAppointmentRequest;
import com.example.pawsicare.business.requests.CreateDoctorScheduleRequest;
import com.example.pawsicare.business.requests.UpdateAppointmentRequest;
import com.example.pawsicare.business.responses.*;
import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
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

    //TODO do the authentication for the appointment controller
    @RolesAllowed({"Doctor", "Client"})
    @GetMapping("/getDocSchedule")
    public ResponseEntity<GetAppointmentsResponse> getDoctorsSchedule(@Valid @RequestParam(name = "docId") Long docId, @RequestParam(name = "token") String token){
        List<AppointmentDTO> schedule = appointmentManager.getDoctorSchedule(docId)
                .stream()
                .map(converter::toDTO)
                .toList();

        return ResponseEntity.ok().body(GetAppointmentsResponse.builder()
                .appointments(schedule)
                .build());
    }
    @RolesAllowed({"Doctor", "Client"})
    @GetMapping(params = "userId")
    public ResponseEntity<GetAppointmentsResponse> getUsersAppointments(@Valid @RequestParam(name = "userId")long userId){

        //if the user id is the same as the one from the token get the appointments otherwise return error check
        Optional<List<AppointmentDTO>> app = appointmentManager.getUsersAppointments(userId)
                        .map(appointments -> appointments.stream()
                                .map(converter::toDTO)
                                .toList())
                        .or(() -> Optional.of(new ArrayList<>()));


        if(!app.isEmpty() && !app.get().isEmpty()){

            GetAppointmentsResponse appointmentsResponse = GetAppointmentsResponse.builder()
                    .appointments(app.get())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(appointmentsResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RolesAllowed({"Doctor"})
    @PostMapping("/schedule")
    public ResponseEntity<GetAppointmentsResponse> createDoctorSchedule(@RequestBody @Valid CreateDoctorScheduleRequest request){
        LocalTime startTime = converter.fromStringToTime(request.getStartHour());
        LocalTime endTime = converter.fromStringToTime(request.getEndHour());

        List<AppointmentDTO> schedule = appointmentManager.createDoctorSchedule(request.getToken(),
                request.getStartDay(),
                request.getEndDay(),
                startTime,
                endTime).stream().map(converter::toDTO).toList();

        return ResponseEntity.ok(GetAppointmentsResponse.builder()
                .appointments(schedule).build());
    }

    @Transactional
    @RolesAllowed({"Client"})
    @PostMapping
    public ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody @Valid CreateAppointmentRequest appointmentRequest){

        LocalDateTime dateAndStart = converter.dateAndTime(appointmentRequest.getDate(),appointmentRequest.getStart());
        LocalDateTime dateAndEnd = converter.dateAndTime(appointmentRequest.getDate(),appointmentRequest.getEnd());
        AppointmentDTO appointment = AppointmentDTO.builder()
                .dateAndStart(dateAndStart)
                .dateAndEnd(dateAndEnd)
                .client(clientConverter.toDTO(clientManager.getClient(appointmentRequest.getClientId())))
                .doctor(doctorConverter.toDTO(doctorManager.getDoctor(appointmentRequest.getDoctorId())))
                .pet(petConverter.toDTO(petManager.getPet(appointmentRequest.getPetId())))
                .build();

        appointmentManager.createAppointment(converter.fromDTO(appointment));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    @RolesAllowed({"Doctor","Client"})
    @PutMapping()
    public ResponseEntity<Void> rescheduleAppointment (@RequestBody @Valid UpdateAppointmentRequest appointmentRequest){
        AppointmentDTO appointment = AppointmentDTO.builder()
                .id(appointmentRequest.getId())
                .dateAndStart(appointmentRequest.getDateAndStart())
                .dateAndEnd(appointmentRequest.getDateAndEnd())
                .doctor(appointmentRequest.getDoctor())
                .client(appointmentRequest.getClient())
                .pet(appointmentRequest.getPet())
                .build();

        appointmentManager.rescheduleAppointment(converter.fromDTO(appointment));


        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }
    @Transactional
    @RolesAllowed({"Doctor","Client"})
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> cancelAppointment(@RequestParam(name = "id")long id, @RequestParam(name = "token") String token){
        appointmentManager.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }
}
