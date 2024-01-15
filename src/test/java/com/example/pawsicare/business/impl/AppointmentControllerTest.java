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
import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.AccessTokenDecoder;
import com.example.pawsicare.controller.AppointmentController;
import com.example.pawsicare.domain.*;
import com.example.pawsicare.domain.managerinterfaces.AppointmentManager;
import com.example.pawsicare.domain.managerinterfaces.ClientManager;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.domain.managerinterfaces.PetManager;
import com.example.pawsicare.persistence.converters.AppointmentEntityConverter;
import com.example.pawsicare.persistence.converters.PetEntityConverter;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.entity.*;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
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
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

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

        UserEntity userEntity = UserEntity.builder()
                .id(docId)
                .role(Role.Doctor.ordinal()).build();

        List<Appointment> appointmentList = List.of(appointment);

        when(appointmentManager.getDoctorSchedule(docId)).thenReturn(appointmentList);
        when(converter.toDTO(any())).thenReturn(appointmentDTO);
        when(accessToken.getId()).thenReturn(docId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Doctor.name())).thenReturn(true);
        when(userRepository.getUserEntityById(docId)).thenReturn(Optional.ofNullable(userEntity));



        List<AppointmentDTO> appointmentDTOList = appointmentList.stream().map(converter::toDTO).toList();

        // Act
        ResponseEntity<GetAppointmentsResponse> responseEntity = appointmentController.getDoctorsSchedule(docId);

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
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        Long userId = 1L;
        String token = "zdr";

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

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Doctor.ordinal()).build();

        List<Appointment> appointmentList = List.of(appointment);

        when(appointmentManager.getUsersAppointments(userId)).thenReturn(Optional.of(appointmentList));
        when(converter.toDTO(any())).thenReturn(appointmentDTO);
        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Doctor.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));


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
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        Long userId = 1L;
        String token = "zdr";

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Doctor.ordinal()).build();

        when(appointmentManager.getUsersAppointments(userId)).thenReturn(Optional.empty());
        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Doctor.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));

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
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(9,0);

        long docId = 1L;
        String token = "zdr";

        CreateDoctorScheduleRequest request = new CreateDoctorScheduleRequest();
        request.setToken(token);
        request.setStartDay(DayOfWeek.Monday);
        request.setEndDay(DayOfWeek.Friday);
        request.setStartHour(startTime.toString());
        request.setEndHour(endTime.toString());

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.of(LocalDate.now(), startTime))
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), endTime))
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.of(LocalDate.now(), startTime))
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), endTime))
                .build();

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.of(LocalDate.now(), startTime))
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), endTime))
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(docId)
                .role(Role.Doctor.ordinal()).build();

        List<Appointment> scheduleList = List.of(appointment);

        when(appointmentManager.createDoctorSchedule(docId,request.getStartDay(),request.getEndDay(),startTime,endTime)).thenReturn(scheduleList);
        when(converter.fromStringToTime(startTime.toString())).thenReturn(startTime);
        when(converter.fromStringToTime(endTime.toString())).thenReturn(endTime);
        when(appointmentEntityConverter.toEntity(appointment)).thenReturn(appointmentEntity);
        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
        when(accessToken.getId()).thenReturn(docId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Doctor.name())).thenReturn(true);
        when(userRepository.getUserEntityById(docId)).thenReturn(Optional.ofNullable(userEntity));

        when(appointmentManager.createDoctorSchedule(
                docId,
                request.getStartDay(),
                request.getEndDay(),
                converter.fromStringToTime(request.getStartHour()),
                converter.fromStringToTime(request.getEndHour()))).thenReturn(scheduleList);

        // Act
        ResponseEntity<GetAppointmentsResponse> responseEntity = appointmentController.createDoctorSchedule(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(scheduleList.stream().map(converter::toDTO).toList(), Objects.requireNonNull(responseEntity.getBody()).getAppointments());
        verify(appointmentManager, times(1)).createDoctorSchedule(
                docId,
                request.getStartDay(),
                request.getEndDay(),
                LocalTime.of(8, 0),
                LocalTime.of(9, 0)
        );
        verify(converter, times(2)).toDTO(any());
    }

    @Test
    void testCreateAppointment() {
        // Arrange
        String token = "zdr";

        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .token(token)
                .date("2023-11-27")
                .start("11:00")
                .end("12:00")
                .clientId(1L)
                .doctorId(2L)
                .petId(3L)
                .build();
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Doctor.ordinal()).build();

        PetEntity petEntity = PetEntity.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        Pet pet1 = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");

        PetDTO petDTO = PetDTO.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        DoctorEntity doctorEntity = DoctorEntity.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        Client client = Client.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        ClientDTO clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(doctor)
                .pet(pet1)
                .build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(clientDTO)
                .doctor(doctorDTO)
                .pet(petDTO)
                .build();

        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
        when(converter.fromDTO(appointmentDTO)).thenReturn(appointment);
        when(petConverter.toDTO(pet1)).thenReturn(petDTO);
        when(doctorConverter.toDTO(doctor)).thenReturn(doctorDTO);
        when(clientConverter.toDTO(client)).thenReturn(clientDTO);
        when(clientManager.getClient(1L)).thenReturn(client);
        when(doctorManager.getDoctor(2L)).thenReturn(doctor);
        when(petManager.getPet(3L)).thenReturn(pet1);
        when(userEntityConverter.toDoctorEntity(doctor)).thenReturn(doctorEntity);
        when(userEntityConverter.toClientEntity(client)).thenReturn(clientEntity);
        when(petEntityConverter.toEntity(pet1)).thenReturn(petEntity);
        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Client.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));


        // Act
        ResponseEntity<Void> responseEntity = appointmentController.createAppointment(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testCreateAppointmentUnauthorized() {
        String token = "zdr";
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .token(token)
                .date("2023-11-27")
                .start("11:00")
                .end("12:00")
                .clientId(1L)
                .doctorId(2L)
                .petId(3L)
                .build();
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        AccessToken accessToken = mock(AccessToken.class);
        UserRepository userRepository = mock(UserRepository.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Doctor.ordinal()).build();

        Pet pet1 = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");

        PetDTO petDTO = PetDTO.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        Client client = Client.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        ClientDTO clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
        when(converter.fromDTO(appointmentDTO)).thenReturn(appointment);
        when(petConverter.toDTO(pet1)).thenReturn(petDTO);
        when(doctorConverter.toDTO(doctor)).thenReturn(doctorDTO);
        when(clientConverter.toDTO(client)).thenReturn(clientDTO);
        when(clientManager.getClient(1L)).thenReturn(client);
        when(doctorManager.getDoctor(2L)).thenReturn(doctor);
        when(petManager.getPet(3L)).thenReturn(pet1);
        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Doctor.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));

        // Act
        ResponseEntity<Void> responseEntity = appointmentController.createAppointment(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void testRescheduleAppointment() {
        // Arrange
        String token = "zdr";
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder()
                .token(token)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(2023,11,27,12,00,00))
                .build();
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Client.ordinal()).build();

        Client client = Client.builder()
                .id(userId).role(Role.Client).build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(null)
                .build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();



        when(converter.fromDTO(any(AppointmentDTO.class))).thenReturn(appointment);
        when(converter.toDTO(any(Appointment.class))).thenReturn(appointmentDTO);
        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Client.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));
        when(appointmentManager.getUsersAppointments(userId)).thenReturn(Optional.of(List.of(appointment)));

        // Act
        ResponseEntity<Void> responseEntity = appointmentController.rescheduleAppointment(request);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    void testRescheduleAppointmentUnauthorized() {
        // Arrange
        String token = "zdr";
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder()
                .token(token)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(2023,11,27,12,00,00))
                .build();
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = mock(ClientConverter.class);
        DoctorConverter doctorConverter = mock(DoctorConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        AppointmentConverter converter = mock(AppointmentConverter.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Client.ordinal()).build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();



        when(converter.fromDTO(appointmentDTO)).thenReturn(appointment);
        when(converter.toDTO(appointment)).thenReturn(appointmentDTO);
        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Client.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));

        // Act
        ResponseEntity<Void> responseEntity = appointmentController.rescheduleAppointment(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void testCancelAppointment() {
        // Arrange
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = new ClientConverter();
        DoctorConverter doctorConverter = new DoctorConverter();
        PetConverter petConverter = new PetConverter();
        AppointmentConverter converter = new AppointmentConverter(doctorConverter,clientConverter,petConverter);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        Client client = new Client();
        client.setId(2L);

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(null)
                .build();

        long appointmentId = 1L;
        long userId = 2L;
        String token = "some token";

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Client.ordinal()).build();

        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Client.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));
        when(appointmentManager.getUsersAppointments(userId)).thenReturn(Optional.of(List.of(appointment)));


        // Act
        ResponseEntity<Void> responseEntity = appointmentController.cancelAppointment(appointmentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(appointmentManager, times(1)).cancelAppointment(appointmentId);
    }
    @Test
    void testCancelAppointmentUnauthorized() {
        // Arrange
        AppointmentManager appointmentManager = mock(AppointmentManager.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        ClientManager clientManager = mock(ClientManager.class);
        PetManager petManager = mock(PetManager.class);
        ClientConverter clientConverter = new ClientConverter();
        DoctorConverter doctorConverter = new DoctorConverter();
        PetConverter petConverter = new PetConverter();
        AppointmentConverter converter = new AppointmentConverter(doctorConverter,clientConverter,petConverter);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        UserRepository userRepository = mock(UserRepository.class);
        AccessToken accessToken = mock(AccessToken.class);
        AppointmentController appointmentController = new AppointmentController(appointmentManager,
                doctorManager,
                clientManager,
                petManager,
                converter,
                clientConverter,
                doctorConverter,
                petConverter,
                accessTokenDecoder,
                userRepository,
                accessToken);

        long appointmentId = 1L;
        long userId = 2L;
        String token = "some token";

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .role(Role.Client.ordinal()).build();

        when(accessToken.getId()).thenReturn(userId);
        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
        when(accessToken.hasRole(Role.Client.name())).thenReturn(true);
        when(userRepository.getUserEntityById(userId)).thenReturn(Optional.ofNullable(userEntity));

        // Act
        ResponseEntity<Void> responseEntity = appointmentController.cancelAppointment(appointmentId);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }
}