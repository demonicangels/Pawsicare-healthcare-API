package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.security.token.AccessToken;
import com.example.pawsicare.business.security.token.AccessTokenDecoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.*;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.persistence.converters.AppointmentEntityConverter;
import com.example.pawsicare.persistence.converters.PetEntityConverter;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import com.example.pawsicare.persistence.entity.AppointmentEntity;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.PetEntity;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AppointmentManagerImplTest {

    /**
     * @verifies return a fully created appointment with all fields
     * @see AppointmentManagerImpl#createAppointment(com.example.pawsicare.domain.Appointment)
     */
    @Test
    void createAppointment_shouldReturnAFullyCreatedAppointmentWithAllFields() throws Exception {
        // Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        DoctorEntity doctorEntity = DoctorEntity.builder()
                .id(1L)
                .phoneNumber("09876422")
                .email("nia@mail.com")
                .password("123")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .phoneNumber("09876422")
                .email("nia@mail.com")
                .password("123")
                .build();

        PetEntity petEntity = PetEntity.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        Pet pet = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");

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

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(doctor)
                .pet(pet)
                .build();

        when(doctorManager.getDoctor(appointment.getDoctor().getId())).thenReturn(doctor);
        when(userEntityConverter.toClientEntity(client)).thenReturn(clientEntity);
        when(userEntityConverter.toDoctorEntity(doctor)).thenReturn(doctorEntity);
        when(petEntityConverter.toEntity(pet)).thenReturn(petEntity);


        // Act
        sut.createAppointment(appointment);

        // Assert
        verify(appointmentRepository, times(1))
                .updateAppointmentEntityByDateAndAndDateAndStartAndDoctor(
                        any(LocalDateTime.class),
                        any(DoctorEntity.class),
                        any(ClientEntity.class),
                        any(PetEntity.class));

        verify(userEntityConverter, times(1)).toDoctorEntity(doctor);
        verify(userEntityConverter, times(1)).toClientEntity(client);
        verify(petEntityConverter, times(1)).toEntity(pet);

        verifyNoMoreInteractions(userEntityConverter, appointmentRepository, petEntityConverter);
    }

    /**
     * @verifies update the correct appointment with the correct values
     * @see AppointmentManagerImpl#rescheduleAppointment(Appointment)
     */
    @Test
    void rescheduleAppointment_shouldUpdateTheCorrectAppointmentWithTheCorrectValues() throws Exception {
        // Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        DoctorEntity doctorEntity = DoctorEntity.builder()
                .id(1L)
                .phoneNumber("09876422")
                .email("nia@mail.com")
                .password("123")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .phoneNumber("09876422")
                .email("nia@mail.com")
                .password("123")
                .build();

        PetEntity petEntity = PetEntity.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        Pet pet = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");

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

        // Original appointment data
        Appointment originalAppointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(client)
                .doctor(doctor)
                .pet(pet)
                .build();

        originalAppointment.setId(3L);

        when(userEntityConverter.toClientEntity(client)).thenReturn(clientEntity);
        when(userEntityConverter.toDoctorEntity(doctor)).thenReturn(doctorEntity);
        when(petEntityConverter.toEntity(pet)).thenReturn(petEntity);


        // Act
        sut.rescheduleAppointment(originalAppointment);

        // Assert
        verify(appointmentRepository, times(1)).updateAppointmentEntityByDoctorAndClientAndPet(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(DoctorEntity.class),
                any(ClientEntity.class),
                any(PetEntity.class));
        verify(appointmentRepository, times(1)).updateAppointmentEntityByIdAndDoctorAndClientAndPet(
                anyLong(),
                any(DoctorEntity.class));
        verify(userEntityConverter, times(2)).toDoctorEntity(doctor);
        verify(userEntityConverter, times(1)).toClientEntity(client);
        verify(petEntityConverter, times(1)).toEntity(pet);
    }


    /**
     * @verifies return all users appointments when present
     * @see AppointmentManagerImpl#getUsersAppointments(long)
     */
    @Test
    void getUsersAppointments_shouldReturnAllUsersAppointmentsWhenPresent() throws Exception {

        // Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.of(2024, 2,11), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.of(2024, 2,11), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.of(2024, 2,11), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        AppointmentEntity appointmentEntity2 = AppointmentEntity.builder()
                .id(2L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.of(2024, 2,11), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Long userId = 1L;

        List<AppointmentEntity> appointmentList = Arrays.asList(appointmentEntity, appointmentEntity2);

        when(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId, userId))
                .thenReturn(appointmentList);

        when(appointmentEntityConverter.fromEntity(appointmentEntity)).thenReturn(appointment);
        when(appointmentEntityConverter.fromEntity(appointmentEntity2)).thenReturn(appointment2);

        // Act
        List<Appointment> returnedAppointments = sut.getUsersAppointments(userId).get();

        List<Appointment> expectedAppointments = appointmentList.stream()
                .map(appointmentEntityConverter::fromEntity)
                .toList();

        // Assert
        assertNotNull(returnedAppointments);

        assertEquals(expectedAppointments, returnedAppointments);
    }

    /**
     * @verifies return an empty list if the user has no appointments
     * @see AppointmentManagerImpl#getUsersAppointments(long)
     */
    @Test
    void getUsersAppointments_shouldReturnAnEmptyListIfTheUserHasNoAppointments() throws Exception {
        // Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Long userId = 1L;

        when(appointmentEntityConverter.fromEntity(any(AppointmentEntity.class))).thenReturn(appointment);
        when(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId, userId))
                .thenReturn(new ArrayList<>());  // Return an empty list directly

        // Act
        List<Appointment> returnedAppointments = sut.getUsersAppointments(userId).get();

        // Assert
        assertTrue(returnedAppointments.isEmpty());
    }

    /**
     * @verifies return all doctors available slots when present
     * @see AppointmentManagerImpl#getDoctorSchedule(long)
     */
    @Test
    void getDoctorSchedule_shouldReturnAllDoctorsAvailableSlotsWhenPresent() throws Exception {
        // Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        AppointmentEntity appointmentEntity2 = AppointmentEntity.builder()
                .id(2L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Long userId = 1L;

        List<AppointmentEntity> appointmentList = Arrays.asList(appointmentEntityConverter.toEntity(appointment), appointmentEntityConverter.toEntity(appointment2));

        when(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId, userId))
                .thenReturn(appointmentList);

        when(appointmentEntityConverter.toEntity(appointment)).thenReturn(appointmentEntity);
        when(appointmentEntityConverter.toEntity(appointment2)).thenReturn(appointmentEntity2);

        // Act
        List<Appointment> returnedAppointments = sut.getUsersAppointments(userId).orElse(new ArrayList<>());

        // Assert
        assertNotNull(returnedAppointments);
        assertEquals(appointmentList, returnedAppointments);
    }

    /**
     * @verifies return an empty list if the doctor has no available slots left
     * @see AppointmentManagerImpl#getDoctorSchedule(long)
     */
    @Test
    void getDoctorSchedule_shouldReturnAnEmptyListIfTheDoctorHasNoAvailableSlotsLeft() throws Exception {
        // Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        Long docId = 1L;

        when(appointmentRepository.getDocSchedule(docId)).thenReturn(Collections.emptyList());

        // Act
        List<Appointment> returnedAppointments = sut.getDoctorSchedule(docId);

        // Assert
        assertNotNull(returnedAppointments);
        assertTrue(returnedAppointments.isEmpty());
    }

    /**
     * @verifies verify if the deleteById method of the repository is being called
     * @see AppointmentManagerImpl#cancelAppointment(long)
     */
    @Test
    void cancelAppointment_shouldVerifyIfTheDeleteByIdMethodOfTheRepositoryIsBeingCalled() throws Exception {
        //Arrange
        AppointmentEntityConverter appointmentEntityConverter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                appointmentEntityConverter,
                accessTokenDecoder,
                doctorManager,
                userEntityConverter,
                petEntityConverter
        );

        //Act
        sut.cancelAppointment(1L);

        //Assert
        verify(appointmentRepository, times(1)).deleteById(1L);
    }

    /**
     * @verifies a list with created appointments in the specified range
     * @see AppointmentManagerImpl#createDoctorSchedule(long docId, DayOfWeek, DayOfWeek, LocalTime, LocalTime)
     */
    @Test
    void createDoctorSchedule_shouldAListWithCreatedAppointmentsInTheSpecifiedRange() throws Exception {
        // Arrange
        AccessTokenDecoder accessTokenDecoder = mock(AccessTokenDecoder.class);
        DoctorManager doctorManager = mock(DoctorManager.class);
        AppointmentEntityConverter converter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(
                appointmentRepository,
                converter,
                accessTokenDecoder,
                doctorManager,
                mock(UserEntityConverter.class),
                mock(PetEntityConverter.class)
        );

        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA1MDAxODU2LCJleHAiOjE3MDUxMDk4NTYsInJvbGUiOiJDbGllbnQiLCJ1c2VySWQiOjF9.R6uLPzsD2ZtzScAe28PyaGYfhnAEAgCX7VcqU0_Fn7X_lt01rh17BVU8NWMeiUUC";
        DayOfWeek startDay = DayOfWeek.Monday;
        DayOfWeek endDay = DayOfWeek.Friday;
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        Instant now = Instant.now();
        Date expiration = Date.from(now.plus(30, ChronoUnit.HOURS));

        AccessToken mockedToken = AccessTokenImpl.builder()
                .userId(2L)
                .role(Role.Doctor)
                .expiration(expiration).build();

        when(accessTokenDecoder.decode(token)).thenReturn(mockedToken);

        Long doctorId = 2L;
        Doctor doctor = Doctor.builder()
                .id(doctorId)
                .name("Ana")
                .build();

        when(doctorManager.getDoctor(doctorId)).thenReturn(doctor);

        when(converter.toEntity(any(Appointment.class))).thenReturn(new AppointmentEntity());


        // Act
        List<Appointment> createdAppointments = sut.createDoctorSchedule(doctorId, startDay, endDay, startTime, endTime);
        List<Appointment> expectedAppointments = sut.createDoctorSchedule(doctorId, startDay, endDay, startTime, endTime);

        // Assert
        assertNotNull(createdAppointments);
        assertEquals(expectedAppointments.size(), createdAppointments.size());
        for (Appointment appointment : createdAppointments) {
            assertNotNull(appointment.getDateAndStart());
            assertNotNull(appointment.getDateAndEnd());
            assertEquals(doctor, appointment.getDoctor());
        }

        verify(appointmentRepository, times(createdAppointments.size() * 2)).save(any(AppointmentEntity.class));
    }
}
