package com.example.pawsicare.business.impl;

import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.persistence.AppointmentEntityConverter;
import com.example.pawsicare.persistence.entity.AppointmentEntity;
import com.example.pawsicare.persistence.jparepositories.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        AppointmentManagerImpl sut = new AppointmentManagerImpl(appointmentRepository, appointmentEntityConverter);


        Appointment appointment = Appointment.builder()
                .id(1L)
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

        when(appointmentEntityConverter.toEntity(appointment))
                .thenReturn(appointmentEntity);

        when(appointmentEntityConverter
                .fromEntity(appointmentEntity)).thenReturn(appointment);

        when(appointmentRepository.save(appointmentEntity))
                .thenAnswer(invocation -> {
                    AppointmentEntity savedEntity = invocation.getArgument(0);
                    savedEntity.setId(1L);
                    return savedEntity;
                });


        // Act
        Appointment createdAppointment = sut.createAppointment(appointment).get();

        // Assert
        assertNotNull(createdAppointment);
        assertEquals(appointment.getId(),createdAppointment.getId());


        verify(appointmentEntityConverter, times(1)).toEntity(any(Appointment.class));
        verify(appointmentRepository, times(1)).save(any(AppointmentEntity.class));
    }

    /**
     * @verifies update the correct appointment with the correct values
     * @see AppointmentManagerImpl#rescheduleAppointment(Appointment)
     */
    @Test
    void rescheduleAppointment_shouldUpdateTheCorrectAppointmentWithTheCorrectValues() throws Exception {

        // Arrange
        AppointmentEntityConverter converter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(appointmentRepository, converter);

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        appointment.setId(3L);

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(3L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        when(converter.fromEntity(appointmentEntity)).thenReturn(appointment);
        when(converter.toEntity(appointment)).thenReturn(appointmentEntity);
        when(appointmentRepository.save(converter.toEntity(appointment))).thenReturn(appointmentEntity);

        // Act

        Appointment updatedAppointment = sut.rescheduleAppointment(appointment).get();

        // Assert
        assertNotNull(updatedAppointment);
        verify(appointmentRepository).save(appointmentEntity);
        verify(converter, times(2)).toEntity(appointment);
        verify(converter,times(1)).fromEntity(appointmentEntity);
        assertEquals(appointment.getId(), updatedAppointment.getId());
    }

    /**
     * @verifies return all users appointments when present
     * @see AppointmentManagerImpl#getUsersAppointments(long)
     */
    @Test
    void getUsersAppointments_shouldReturnAllUsersAppointmentsWhenPresent() throws Exception {

        // Arrange
        AppointmentEntityConverter converter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(appointmentRepository, converter);

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

        List<AppointmentEntity> appointmentList = Arrays.asList(converter.toEntity(appointment), converter.toEntity(appointment2));

        when(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId, userId))
                .thenReturn(appointmentList);

        when(converter.toEntity(appointment)).thenReturn(appointmentEntity);
        when(converter.toEntity(appointment2)).thenReturn(appointmentEntity2);

        // Act
        List<Appointment> returnedAppointments = sut.getUsersAppointments(userId).orElse(new ArrayList<>());

        // Assert
        assertNotNull(returnedAppointments);
        assertEquals(appointmentList, returnedAppointments);
    }

    /**
     * @verifies return an empty list if the user has no appointments
     * @see AppointmentManagerImpl#getUsersAppointments(long)
     */
    @Test
    void getUsersAppointments_shouldReturnAnEmptyListIfTheUserHasNoAppointments() throws Exception {
        // Arrange
        AppointmentEntityConverter converter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(appointmentRepository, converter);

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndStart(LocalDateTime.now())
                .dateAndEnd(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
                .client(null)
                .doctor(null)
                .build();

        Long userId = 1L;

        when(converter.fromEntity(any(AppointmentEntity.class))).thenReturn(appointment);
        when(appointmentRepository.findAppointmentEntitiesByClient_IdOrDoctor_Id(userId, userId))
                .thenReturn(new ArrayList<>());  // Return an empty list directly

        // Act
        List<Appointment> returnedAppointments = sut.getUsersAppointments(userId).get();

        // Assert
        assertTrue(returnedAppointments.isEmpty());
    }

    /**
     * @verifies verify if the deleteById method of the repository is being called
     * @see AppointmentManagerImpl#cancelAppointment(long)
     */
    @Test
    void cancelAppointment_shouldVerifyIfTheDeleteByIdMethodOfTheRepositoryIsBeingCalled() throws Exception {
        //Arrange
        AppointmentEntityConverter converter = mock(AppointmentEntityConverter.class);
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);

        AppointmentManagerImpl sut = new AppointmentManagerImpl(appointmentRepository, converter);
        //Act
        sut.cancelAppointment(1L);

        //Assert
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
