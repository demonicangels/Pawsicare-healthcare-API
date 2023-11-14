package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.DoctorDTO;
import com.example.pawsicare.business.responses.createDoctorResponse;
import com.example.pawsicare.business.responses.getAllDoctorsResponse;
import com.example.pawsicare.business.responses.updateDoctorResponse;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jpaRepositories.doctorRepository;
import com.example.pawsicare.persistence.jpaRepositories.userRepository;
import com.example.pawsicare.persistence.userEntityConverter;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class DoctorManagerImplTest {
    /**
     * @verifies return a filled in doctor object when the doctor is created
     * @see doctorManagerImpl#createDoctor(Doctor)
     */
    @Test
     void createDoctor_shouldReturnAFilledInDoctorObjectWhenTheDoctorIsCreated() throws Exception {
        //Arrange
        userRepository userRepositoryMock = mock(userRepository.class);
        doctorRepository doctorRepositoryMock = mock(doctorRepository.class);
        userEntityConverter userEntityConverterMock = mock(userEntityConverter.class);
        doctorConverter doctorConverterMock = mock(doctorConverter.class);


        DoctorEntity doctor = DoctorEntity.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        DoctorDTO doctor3 = DoctorDTO.builder()
                .id(1L)
                .name("Amara")
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        when(userRepositoryMock.save(doctor)).thenReturn(doctor);
        when(userEntityConverterMock.fromDoctorEntity(doctor)).thenReturn(doctor2);
        when(userEntityConverterMock.toDoctorEntity(doctor2)).thenReturn(doctor);
        when(doctorConverterMock.toDTO(doctor2)).thenReturn(doctor3);

        //Act

        doctorManagerImpl sut = new doctorManagerImpl(userRepositoryMock,userEntityConverterMock,doctorRepositoryMock);
        createDoctorResponse sutResponse = createDoctorResponse.builder()
                .doctor(doctorConverterMock.toDTO(sut.createDoctor(userEntityConverterMock.fromDoctorEntity(doctor))))
                .build();

        //Assert
        Assertions.assertNotNull(sutResponse.getDoctor());
        assertThat(sutResponse.getDoctor().getName()).isEqualTo("Amara");
        assertThat(sutResponse.getDoctor().getId()).isNotZero();

        verify(userRepositoryMock, times(1)).save(doctor);
        verify(userEntityConverterMock, times(2)).fromDoctorEntity(doctor);
        verify(doctorConverterMock, times(1)).toDTO(doctor2);
        verify(userEntityConverterMock, times(1)).toDoctorEntity(doctor2);

    }

    /**
     * @verifies return a doctor object with updated fields
     * @see doctorManagerImpl #updateDoctor()( Doctor )
     */
    @Test
     void updateDoctor_shouldReturnADoctorObjectWithUpdatedFields() throws Exception {
       //Arrange

       userRepository userRepositoryMock = mock(userRepository.class);
       doctorRepository doctorRepositoryMock = mock(doctorRepository.class);
       userEntityConverter userEntityConverterMock = mock(userEntityConverter.class);
       doctorConverter doctorConverterMock = mock(doctorConverter.class);

       DoctorEntity updateDoctor = DoctorEntity.builder()
                        .name("Nia")
                        .field("neurology")
                        .email("nia@mail.com")
                        .build();

       Doctor doctor = Doctor.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       DoctorDTO doctorDTO = DoctorDTO.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       when(userRepositoryMock.save(updateDoctor)).thenReturn(updateDoctor);
       when(userEntityConverterMock.fromDoctorEntity(updateDoctor)).thenReturn(doctor);
       when(userEntityConverterMock.toUserEntity(doctor)).thenReturn(updateDoctor);
       when(userEntityConverterMock.toDoctorEntity(doctor)).thenReturn(updateDoctor);
       when(doctorConverterMock.toDTO(doctor)).thenReturn(doctorDTO);
       when(doctorConverterMock.fromDTO(doctorDTO)).thenReturn(doctor);


        doctorManagerImpl sut = new doctorManagerImpl(userRepositoryMock,userEntityConverterMock,doctorRepositoryMock);

        //Act

        updateDoctorResponse sutResponse = updateDoctorResponse.builder()
                .updatedDoctor(doctorConverterMock.toDTO(sut.updateDoctor(userEntityConverterMock.fromDoctorEntity(updateDoctor))))
                .build();

        //Assert
        Assertions.assertEquals(updateDoctor,userEntityConverterMock.toDoctorEntity(doctorConverterMock.fromDTO(sutResponse.getUpdatedDoctor())));
        assertThat(updateDoctor.getId()).isEqualTo(sutResponse.getUpdatedDoctor().getId());
        assertThat(updateDoctor.getName()).isEqualTo(sutResponse.getUpdatedDoctor().getName());

    }

    /**
     * @verifies return a list with all doctors when doctors are present
     * @see doctorManagerImpl #getDoctors()( Doctor )
     */
    @Test
     void getDoctor_shouldReturnAListWithAllDoctorsWhenDoctorsArePresent() throws Exception {
        //Arrange
       userRepository userRepositoryMock = mock(userRepository.class);
       doctorRepository doctorRepositoryMock = mock(doctorRepository.class);
       userEntityConverter userEntityConverterMock = mock(userEntityConverter.class);
       doctorConverter doctorConverterMock = mock(doctorConverter.class);


       DoctorEntity updateDoctor = DoctorEntity.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       Doctor doctor = Doctor.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       DoctorDTO doctorDTO = DoctorDTO.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       when(userRepositoryMock.findByRole(1)).thenReturn(new ArrayList<UserEntity>());
       when(userEntityConverterMock.fromUserEntity(updateDoctor)).thenReturn(doctor);
       when(doctorConverterMock.toDTO(doctor)).thenReturn(doctorDTO);

        when(userRepositoryMock.findByRole(1)).thenReturn((List<UserEntity>) Arrays.asList(UserEntity.builder()
                .id(1L)
                .name("Zara")
                .password("123")
                .email("maia@gmail.com")
                .phoneNumber("+1234567").build(),

                UserEntity.builder()
                        .id(2L)
                        .name("Maia")
                        .password("123")
                        .email("maia@gmail.com")
                        .phoneNumber("+1234567")
                        .build()));

       doctorManagerImpl sut =  new doctorManagerImpl(userRepositoryMock,userEntityConverterMock,doctorRepositoryMock);

        //Act
        getAllDoctorsResponse sutResponse = getAllDoctorsResponse.builder()
                .doctors(sut.getDoctors().stream().map(doctorConverterMock :: toDTO).toList())
                .build();
        //Assert
        Assertions.assertNotNull(sutResponse.getDoctors());
        assertThat(sutResponse.getDoctors()).hasSize(2);
    }

    /**
     * @verifies return an empty list when no doctors are present
     * @see doctorManagerImpl #getDoctors()( Doctor )
     */
    @Test
     void getDoctor_shouldReturnAnEmptyListWhenNoDoctorsArePresent() throws Exception {
       //Arrange
       userRepository userRepositoryMock = mock(userRepository.class);
       doctorRepository doctorRepositoryMock = mock(doctorRepository.class);
       userEntityConverter userEntityConverterMock = mock(userEntityConverter.class);
       doctorConverter doctorConverterMock = mock(doctorConverter.class);

       DoctorEntity updateDoctor = DoctorEntity.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       Doctor doctor = Doctor.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       DoctorDTO doctorDTO = DoctorDTO.builder()
               .name("Nia")
               .field("neurology")
               .email("nia@mail.com")
               .build();

       when(userRepositoryMock.findByRole(1)).thenReturn(new ArrayList<UserEntity>());
       when(userEntityConverterMock.fromDoctorEntity(updateDoctor)).thenReturn(doctor);
       when(userEntityConverterMock.toUserEntity(doctor)).thenReturn(updateDoctor);
       when(userEntityConverterMock.toDoctorEntity(doctor)).thenReturn(updateDoctor);
       when(doctorConverterMock.toDTO(doctor)).thenReturn(doctorDTO);
       when(doctorConverterMock.fromDTO(doctorDTO)).thenReturn(doctor);

       doctorManagerImpl sut =  new doctorManagerImpl(userRepositoryMock,userEntityConverterMock,doctorRepositoryMock);

       //Act
        getAllDoctorsResponse sutResponse = getAllDoctorsResponse.builder()
                .doctors(sut.getDoctors().stream().map(doctorConverterMock :: toDTO).toList())
                .build();

       //Assert
        assertThat(sutResponse.getDoctors()).isEmpty();
    }
}
