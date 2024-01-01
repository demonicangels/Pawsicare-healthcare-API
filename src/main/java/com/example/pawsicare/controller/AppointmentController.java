package com.example.pawsicare.controller;

import com.example.pawsicare.business.dto.AppointmentDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.impl.AppointmentConverter;
import com.example.pawsicare.business.impl.ClientConverter;
import com.example.pawsicare.business.impl.DoctorConverter;
import com.example.pawsicare.business.impl.PetConverter;
import com.example.pawsicare.business.requests.CreateAppointmentRequest;
import com.example.pawsicare.business.requests.CreateDoctorScheduleRequest;
import com.example.pawsicare.business.requests.UpdateAppointmentRequest;
import com.example.pawsicare.business.responses.*;
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.impl.AccessTokenDecoderEncoderImpl;
import com.example.pawsicare.domain.DayOfWeek;
import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
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
    private final AccessTokenDecoderEncoderImpl accessTokenService;



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
//
//
//        AccessToken tokenClaims =  accessTokenService.decode(request.getToken());
//
//        Long doctorId = tokenClaims.getId();
//
//        DoctorDTO doctor = doctorConverter.toDTO(doctorManager.getDoctor(doctorId));
//
//        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
//        LocalDateTime endDateTime = currentDateTime.plusWeeks(numberOfWeeks);
//
//        List<AppointmentDTO> appointments = new ArrayList<>();
//
//        while (currentDateTime.isBefore(endDateTime)){
//            DayOfWeek dayOfWeek = DayOfWeek.values()[currentDateTime.get(ChronoField.DAY_OF_WEEK) - 1];
//
//            if(dayOfWeek.compareTo(request.getStartDay()) >= 0 && dayOfWeek.compareTo(request.getEndDay()) <= 0){
//                if(currentDateTime.getHour() >= startTime.getHour() && currentDateTime.getHour() < endTime.getHour()){
//                    LocalDateTime appStart = currentDateTime;
//                    LocalDateTime appEnd = currentDateTime.plusMinutes(appointmentDurationInMinutes);
//                    appointments.add(AppointmentDTO.builder()
//                            .dateAndStart(appStart)
//                            .dateAndEnd(appEnd)
//                            .doctor(doctor).build());
//                }
//            }
//
//            currentDateTime = currentDateTime.plusHours(1);
//        }

        return ResponseEntity.ok(GetAppointmentsResponse.builder()
                .appointments(schedule).build());
    }

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

        Optional<AppointmentDTO> app = Optional.ofNullable(appointmentManager.createAppointment(converter.fromDTO(appointment))
                .map(converter :: toDTO)
                .orElse(null));

        if(app.isPresent()){
            CreateAppointmentResponse appointmentResponse = CreateAppointmentResponse.builder()
                   .appointment(app.get())
                   .build();
         return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResponse);
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @RolesAllowed({"Doctor","Client"})
    @PutMapping(params = "id")
    public ResponseEntity<UpdateAppointmentResponse> rescheduleAppointment (@RequestBody @Valid UpdateAppointmentRequest appointmentRequest){
        AppointmentDTO appointment = AppointmentDTO.builder()
                .dateAndStart(appointmentRequest.getDateAndStart())
                .dateAndEnd(appointmentRequest.getDateAndEnd())
                .build();

        Optional<AppointmentDTO> api = Optional.ofNullable(appointmentManager.rescheduleAppointment(converter.fromDTO(appointment)).map(converter :: toDTO).orElse(null));

        if(api.isPresent()){
            UpdateAppointmentResponse updateAppointmentResponse = UpdateAppointmentResponse.builder()
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
}
