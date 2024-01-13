package com.example.pawsicare.business.impl;
import com.example.pawsicare.business.converters.AppointmentConverter;
import com.example.pawsicare.business.converters.ClientConverter;
import com.example.pawsicare.business.converters.DoctorConverter;
import com.example.pawsicare.business.converters.PetConverter;
import com.example.pawsicare.business.dto.AppointmentDTO;
import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.dto.PetDTO;
import com.example.pawsicare.business.requests.CreateAppointmentRequest;
import com.example.pawsicare.business.requests.CreateDoctorScheduleRequest;
import com.example.pawsicare.business.requests.UpdateAppointmentRequest;
import com.example.pawsicare.business.responses.CreateAppointmentResponse;
import com.example.pawsicare.business.responses.GetAppointmentsResponse;
import com.example.pawsicare.business.responses.UpdateAppointmentResponse;
import com.example.pawsicare.business.security.token.AccessTokenDecoder;
import com.example.pawsicare.controller.AppointmentController;
import com.example.pawsicare.domain.*;
import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import com.example.pawsicare.persistence.entity.AppointmentEntity;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Test
    void testGetDoctorsSchedule() {
        // Arrange
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter);

        Long docId = 1L;
        String token = "testToken";

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.now().plusHours(2))
                .build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.now().plusHours(2))
                .build();

        List<Appointment> appointmentList = List.of(appointment);

        when(appointmentManager.getDoctorSchedule(docId)).thenReturn(appointmentList);
        when(converter.toDTO(any())).thenReturn(appointmentDTO);


        List<AppointmentDTO> appointmentDTOList = appointmentList.stream().map(converter::toDTO).toList();

        // Act
        ResponseEntity<GetAppointmentsResponse> responseEntity = appointmentController.getDoctorsSchedule(docId, token);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointmentDTOList, Objects.requireNonNull(responseEntity.getBody()).getAppointments());
        verify(appointmentManager, times(1)).getDoctorSchedule(docId);
        verify(converter, times(2)).toDTO(any());
    }

    @Test
    void testGetUsersAppointments() {
        // Arrange
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter);

        Long userId = 1L;

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(4L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.now().plusHours(2))
                .build();

        Appointment appointment = Appointment.builder()
                .id(3L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.now().plusHours(2))
                .build();

        List<Appointment> appointmentList = List.of(appointment);

        when(appointmentManager.getUsersAppointments(userId)).thenReturn(Optional.of(appointmentList));
        when(converter.toDTO(any())).thenReturn(appointmentDTO);

        List<AppointmentDTO> appointmentDTOList = appointmentList.stream().map(converter::toDTO).toList();

        // Act
        ResponseEntity<GetAppointmentsResponse> responseEntity = appointmentController.getUsersAppointments(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointmentDTOList, Objects.requireNonNull(responseEntity.getBody()).getAppointments());
        verify(appointmentManager, times(1)).getUsersAppointments(userId);
        verify(converter, times(2)).toDTO(any());
    }
    @Test
    void testGetUsersAppointmentsNotFound() {
        // Arrange
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter);

        Long userId = 1L;

        when(appointmentManager.getUsersAppointments(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<GetAppointmentsResponse> responseEntity = appointmentController.getUsersAppointments(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(appointmentManager, times(1)).getUsersAppointments(userId);
        verify(converter, never()).toDTO(any());
    }

    @Test
    void testCreateDoctorSchedule() {
        // Arrange
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter);

        CreateDoctorScheduleRequest request = new CreateDoctorScheduleRequest();
        request.setToken("testToken");
        request.setStartDay(DayOfWeek.Monday);
        request.setEndDay(DayOfWeek.Friday);

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.now().plusHours(1))
                .build();

        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(9,0);

        List<Appointment> scheduleList = List.of(appointment);

        when(converter.fromStringToTime(anyString())).thenReturn(LocalTime.of(8, 0));
        when(appointmentManager.createDoctorSchedule(request.getToken(),request.getStartDay(),request.getEndDay(),startTime,endTime)).thenReturn(scheduleList);

        when(appointmentManager.createDoctorSchedule(
                request.getToken(),
                request.getStartDay(),
                request.getEndDay(),
                LocalTime.of(8, 0),
                LocalTime.of(17, 0)
        )).thenReturn(scheduleList);

        // Act
        ResponseEntity<GetAppointmentsResponse> responseEntity = appointmentController.createDoctorSchedule(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(scheduleList, Objects.requireNonNull(responseEntity.getBody()).getAppointments());
        verify(appointmentManager, times(1)).createDoctorSchedule(
                request.getToken(),
                request.getStartDay(),
                request.getEndDay(),
                LocalTime.of(8, 0),
                LocalTime.of(17, 0)
        );
        verify(converter, times(scheduleList.size())).toDTO(any());
    }

//    @Test
//    void testCreateAppointment() {
//        // Arrange
//        AppointmentManager appointmentManager = mock(AppointmentManager.class);
//        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
//                .date("2023-11-27")
//                .start("11:00")
//                .end("12:00")
//                .clientId(1L)
//                .doctorId(2L)
//                .petId(3L)
//                .build();
//        DoctorManager doctorManager = mock(DoctorManager.class);
//        ClientManager clientManager = mock(ClientManager.class);
//        PetManager petManager = mock(PetManager.class);
//        ClientConverter clientConverter = mock(ClientConverter.class);
//        DoctorConverter doctorConverter = mock(DoctorConverter.class);
//        PetConverter petConverter = mock(PetConverter.class);
//        AppointmentConverter converter = mock(AppointmentConverter.class);
//
//        AppointmentController appointmentController = new AppointmentController(appointmentManager,
//                doctorManager,
//                clientManager,
//                petManager,
//                converter,
//                clientConverter,
//                doctorConverter,
//                petConverter);
//
//        Pet pet1 = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");
//
//        PetDTO petDTO = PetDTO.builder()
//                .id(1L)
//                .name("maia")
//                .gender(Gender.FEMALE)
//                .build();
//
//        Appointment appointment = Appointment.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//        Doctor doctor = Doctor.builder()
//                .id(1L)
//                .name("Amara")
//                .field("cardiology")
//                .email("amara@mail.com")
//                .phoneNumber("+316395853793")
//                .password("123")
//                .build();
//
//        DoctorDTO doctorDTO = DoctorDTO.builder()
//                .id(1L)
//                .name("Amara")
//                .field("cardiology")
//                .email("amara@mail.com")
//                .phoneNumber("+316395853793")
//                .password("123")
//                .build();
//
//        Client client = Client.builder()
//                .id(1L)
//                .name("Nikol")
//                .email("nikol@mail.com")
//                .password("123").build();
//
//        ClientDTO clientDTO = ClientDTO.builder()
//                .id(1L)
//                .name("Nikol")
//                .email("nikol@mail.com")
//                .password("123").build();
//
//        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
//        when(converter.fromDTO(appointmentDTO)).thenReturn(appointment);
//        when(petConverter.toDTO(pet1)).thenReturn(petDTO);
//        when(doctorConverter.toDTO(doctor)).thenReturn(doctorDTO);
//        when(clientConverter.toDTO(client)).thenReturn(clientDTO);
//        when(clientManager.getClient(1L)).thenReturn(client);
//        when(doctorManager.getDoctor(2L)).thenReturn(doctor);
//        when(petManager.getPet(3L)).thenReturn(pet1);
//        when(appointmentManager.createAppointment(any())).thenReturn(Optional.of(appointment));
//
//        // Act
//        ResponseEntity<CreateAppointmentResponse> responseEntity = appointmentController.createAppointment(request);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody().getAppointment());
//    }
//
//    @Test
//    void testCreateAppointmentBadRequest() {
//        AppointmentManager appointmentManager = mock(AppointmentManager.class);
//        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
//                .date("2023-11-27")
//                .start("11:00")
//                .end("12:00")
//                .clientId(1L)
//                .doctorId(2L)
//                .petId(3L)
//                .build();
//        DoctorManager doctorManager = mock(DoctorManager.class);
//        ClientManager clientManager = mock(ClientManager.class);
//        PetManager petManager = mock(PetManager.class);
//        ClientConverter clientConverter = mock(ClientConverter.class);
//        DoctorConverter doctorConverter = mock(DoctorConverter.class);
//        PetConverter petConverter = mock(PetConverter.class);
//        AppointmentConverter converter = mock(AppointmentConverter.class);
//
//        AppointmentController appointmentController = new AppointmentController(appointmentManager,
//                doctorManager,
//                clientManager,
//                petManager,
//                converter,
//                clientConverter,
//                doctorConverter,
//                petConverter);
//
//        Pet pet1 = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");
//
//        PetDTO petDTO = PetDTO.builder()
//                .id(1L)
//                .name("maia")
//                .gender(Gender.FEMALE)
//                .build();
//
//        Appointment appointment = Appointment.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//        Doctor doctor = Doctor.builder()
//                .id(1L)
//                .name("Amara")
//                .field("cardiology")
//                .email("amara@mail.com")
//                .phoneNumber("+316395853793")
//                .password("123")
//                .build();
//
//        DoctorDTO doctorDTO = DoctorDTO.builder()
//                .id(1L)
//                .name("Amara")
//                .field("cardiology")
//                .email("amara@mail.com")
//                .phoneNumber("+316395853793")
//                .password("123")
//                .build();
//
//        Client client = Client.builder()
//                .id(1L)
//                .name("Nikol")
//                .email("nikol@mail.com")
//                .password("123").build();
//
//        ClientDTO clientDTO = ClientDTO.builder()
//                .id(1L)
//                .name("Nikol")
//                .email("nikol@mail.com")
//                .password("123").build();
//
//        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
//        when(converter.fromDTO(appointmentDTO)).thenReturn(appointment);
//        when(petConverter.toDTO(pet1)).thenReturn(petDTO);
//        when(doctorConverter.toDTO(doctor)).thenReturn(doctorDTO);
//        when(clientConverter.toDTO(client)).thenReturn(clientDTO);
//        when(clientManager.getClient(1L)).thenReturn(client);
//        when(doctorManager.getDoctor(2L)).thenReturn(doctor);
//        when(petManager.getPet(3L)).thenReturn(pet1);
//        when(appointmentManager.createAppointment(any())).thenReturn(Optional.of(new Appointment()));
//
//        // Act
//        ResponseEntity<CreateAppointmentResponse> responseEntity = appointmentController.createAppointment(request);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void testRescheduleAppointment() {
//        // Arrange
//        AppointmentManager appointmentManager = mock(AppointmentManager.class);
//        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder()
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(2023,11,27,12,00,00))
//                .build();
//        DoctorManager doctorManager = mock(DoctorManager.class);
//        ClientManager clientManager = mock(ClientManager.class);
//        PetManager petManager = mock(PetManager.class);
//        ClientConverter clientConverter = mock(ClientConverter.class);
//        DoctorConverter doctorConverter = mock(DoctorConverter.class);
//        PetConverter petConverter = mock(PetConverter.class);
//        AppointmentConverter converter = mock(AppointmentConverter.class);
//
//        AppointmentController appointmentController = new AppointmentController(appointmentManager,
//                doctorManager,
//                clientManager,
//                petManager,
//                converter,
//                clientConverter,
//                doctorConverter,
//                petConverter);
//
//        Appointment appointment = Appointment.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//
//        when(appointmentManager.rescheduleAppointment(any(Appointment.class))).thenReturn(Optional.of(appointment));
//        when(converter.fromDTO(any(AppointmentDTO.class))).thenReturn(appointment);
//        when(converter.toDTO(any(Appointment.class))).thenReturn(appointmentDTO);
//
//        // Act
//        ResponseEntity<UpdateAppointmentResponse> responseEntity = appointmentController.rescheduleAppointment(request);
//
//        // Assert
//        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody().getUpdatedAppointment());
//    }
//
//    @Test
//    void testRescheduleAppointmentNotModified() {
//        // Arrange
//        AppointmentManager appointmentManager = mock(AppointmentManager.class);
//        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder()
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(2023,11,27,12,00,00))
//                .build();
//        DoctorManager doctorManager = mock(DoctorManager.class);
//        ClientManager clientManager = mock(ClientManager.class);
//        PetManager petManager = mock(PetManager.class);
//        ClientConverter clientConverter = mock(ClientConverter.class);
//        DoctorConverter doctorConverter = mock(DoctorConverter.class);
//        PetConverter petConverter = mock(PetConverter.class);
//        AppointmentConverter converter = mock(AppointmentConverter.class);
//
//        AppointmentController appointmentController = new AppointmentController(appointmentManager,
//                doctorManager,
//                clientManager,
//                petManager,
//                converter,
//                clientConverter,
//                doctorConverter,
//                petConverter);
//
//        Appointment appointment = Appointment.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
//                .id(1L)
//                .dateAndStart(LocalDateTime.now())
//                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
//                .client(null)
//                .doctor(null)
//                .build();
//
//
//        when(appointmentManager.rescheduleAppointment(any(Appointment.class))).thenReturn(Optional.of(appointment));
//        when(converter.fromDTO(appointmentDTO)).thenReturn(appointment);
//        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
//
//        // Act
//        ResponseEntity<UpdateAppointmentResponse> responseEntity = appointmentController.rescheduleAppointment(request);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_MODIFIED, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void testCancelAppointment() {
//        // Arrange
//        AppointmentManager appointmentManager = mock(AppointmentManager.class);
//        DoctorManager doctorManager = mock(DoctorManager.class);
//        ClientManager clientManager = mock(ClientManager.class);
//        PetManager petManager = mock(PetManager.class);
//        ClientConverter clientConverter = new ClientConverter();
//        DoctorConverter doctorConverter = new DoctorConverter();
//        PetConverter petConverter = new PetConverter();
//        AppointmentConverter converter = new AppointmentConverter(doctorConverter,clientConverter,petConverter);
//
//        AppointmentController appointmentController = new AppointmentController(appointmentManager,
//                doctorManager,
//                clientManager,
//                petManager,
//                converter,
//                clientConverter,
//                doctorConverter,
//                petConverter);
//
//        long appointmentId = 1L;
//
//        // Act
//        ResponseEntity<Void> responseEntity = appointmentController.cancelAppointment(appointmentId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        verify(appointmentManager, times(1)).cancelAppointment(appointmentId);
//    }
}