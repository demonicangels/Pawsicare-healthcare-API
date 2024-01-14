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
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.AccessTokenDecoder;
import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
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
    private final AccessTokenDecoder accessTokenDecoder;
    private final UserRepository userRepository;

    @RolesAllowed({"Doctor", "Client"})
    @GetMapping("/getDocSchedule")
    public ResponseEntity<GetAppointmentsResponse> getDoctorsSchedule(@Valid @RequestParam(name = "docId") Long docId, @RequestParam(name = "token") String token){
        AccessToken accessToken = accessTokenDecoder.decode(token);
        Optional<UserEntity> user = userRepository.getUserEntityById(accessToken.getId());
        boolean isDoctor = accessToken.hasRole(Role.Doctor.name());
        boolean isClient = accessToken.hasRole(Role.Client.name());


        if(!user.isEmpty() && docId.equals(accessToken.getId()) && isDoctor || isClient ){

            List<AppointmentDTO> schedule = appointmentManager.getDoctorSchedule(docId)
                    .stream()
                    .map(converter::toDTO)
                    .toList();

            return ResponseEntity.ok().body(GetAppointmentsResponse.builder()
                    .appointments(schedule)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


    }
    @RolesAllowed({"Doctor", "Client"})
    @GetMapping(params = "userId")
    public ResponseEntity<GetAppointmentsResponse> getUsersAppointments(@Valid @RequestParam(name = "userId")long userId, @Valid @RequestParam(name="token") String token){

        AccessToken accessToken = accessTokenDecoder.decode(token);
        Optional<UserEntity> user = userRepository.getUserEntityById(accessToken.getId());
        boolean isDoctor = accessToken.hasRole(Role.Doctor.name());
        boolean isClient = accessToken.hasRole(Role.Client.name());

        if(!user.isEmpty() && userId == accessToken.getId() && isDoctor || isClient){

            Optional<List<AppointmentDTO>> app = appointmentManager.getUsersAppointments(userId)
                    .map(appointments -> appointments.stream()
                            .map(converter::toDTO)
                            .toList());

            if(!app.isEmpty()){
                GetAppointmentsResponse appointmentsResponse = GetAppointmentsResponse.builder()
                        .appointments(app.get())
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(appointmentsResponse);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @RolesAllowed({"Doctor"})
    @PostMapping("/schedule")
    public ResponseEntity<GetAppointmentsResponse> createDoctorSchedule(@RequestBody @Valid CreateDoctorScheduleRequest request){

        AccessToken accessToken = accessTokenDecoder.decode(request.getToken());
        boolean isDoctor = accessToken.hasRole(Role.Doctor.name());
        Optional<UserEntity> user = userRepository.getUserEntityById(accessToken.getId());


        if(!user.isEmpty() && isDoctor){
            LocalTime startTime = converter.fromStringToTime(request.getStartHour());
            LocalTime endTime = converter.fromStringToTime(request.getEndHour());

            List<AppointmentDTO> schedule = appointmentManager.createDoctorSchedule(accessToken.getId(),
                    request.getStartDay(),
                    request.getEndDay(),
                    startTime,
                    endTime).stream().map(converter::toDTO).toList();

            return ResponseEntity.ok(GetAppointmentsResponse.builder()
                    .appointments(schedule).build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Transactional
    @RolesAllowed({"Client"})
    @PostMapping
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid CreateAppointmentRequest appointmentRequest){

        AccessToken accessToken = accessTokenDecoder.decode(appointmentRequest.getToken());
        Optional<UserEntity> user = userRepository.getUserEntityById(accessToken.getId());
        boolean isClient = accessToken.hasRole(Role.Client.name());


        if(!user.isEmpty() && isClient){
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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Transactional
    @RolesAllowed({"Doctor","Client"})
    @PutMapping()
    public ResponseEntity<Void> rescheduleAppointment (@RequestBody @Valid UpdateAppointmentRequest appointmentRequest){

        AccessToken accessToken = accessTokenDecoder.decode(appointmentRequest.getToken());
        Optional<UserEntity> user = userRepository.getUserEntityById(accessToken.getId());
        boolean isClient = accessToken.hasRole(Role.Client.name());
        boolean isDoctor = accessToken.hasRole(Role.Doctor.name());
        Optional<List<Appointment>> appointments = appointmentManager.getUsersAppointments(accessToken.getId());

        if(user.isPresent() && (isClient || isDoctor) && appointments.isPresent()){
           boolean isCorrectUsersAppointments = false;
           for (Appointment a: appointments.get()){
                if(a.getClient().getId().equals(accessToken.getId())){
                    isCorrectUsersAppointments = true;
                }
           }

           if(isCorrectUsersAppointments){

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
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @Transactional
    @RolesAllowed({"Doctor","Client"})
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> cancelAppointment(@RequestParam(name = "id")long id, @RequestParam(name = "token") String token){
        AccessToken accessToken = accessTokenDecoder.decode(token);
        Optional<UserEntity> user = userRepository.getUserEntityById(accessToken.getId());
        boolean isClient = accessToken.hasRole(Role.Client.name());
        boolean isDoctor = accessToken.hasRole(Role.Doctor.name());
        Optional<List<Appointment>> appointments = appointmentManager.getUsersAppointments(accessToken.getId());

        if(!user.isEmpty() && !appointments.isEmpty() && isClient || isDoctor ){
            boolean isCorrectUsersAppointments = false;
            for (Appointment a: appointments.get()){
                if(a.getClient().getId().equals(accessToken.getId())){
                    isCorrectUsersAppointments = true;
                }
            }

            if(isCorrectUsersAppointments){
                appointmentManager.cancelAppointment(id);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
