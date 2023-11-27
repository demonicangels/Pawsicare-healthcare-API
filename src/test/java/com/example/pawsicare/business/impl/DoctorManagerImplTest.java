package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.responses.CreateDoctorResponse;
import com.example.pawsicare.business.responses.GetAllDoctorsResponse;
import com.example.pawsicare.business.responses.UpdateDoctorResponse;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.DoctorRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import com.example.pawsicare.persistence.UserEntityConverter;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class DoctorManagerImplTest {
    //TODO test  getDoctor method using the id and getDoctorsByField

    /**
     * @verifies return a filled in doctor object when the doctor is created
     * @see DoctorManagerImpl#createDoctor(Doctor)
     */
    @Test
     void createDoctor_shouldReturnAFilledInDoctorObjectWhenTheDoctorIsCreated() throws Exception {
        //Arrange
        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);


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
        when(passwordEncoderMock.encode("123")).thenReturn("123");

        //Act

        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);
        CreateDoctorResponse sutResponse = CreateDoctorResponse.builder()
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
     * @see DoctorManagerImpl #updateDoctor()( Doctor )
     */
    @Test
     void updateDoctor_shouldReturnADoctorObjectWithUpdatedFields() throws Exception {
       //Arrange

       UserRepository userRepositoryMock = mock(UserRepository.class);
       DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
       UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
       DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
       PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

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
       when(passwordEncoderMock.encode("123")).thenReturn("123");


        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);

        //Act

        UpdateDoctorResponse sutResponse = UpdateDoctorResponse.builder()
                .updatedDoctor(doctorConverterMock.toDTO(sut.updateDoctor(userEntityConverterMock.fromDoctorEntity(updateDoctor))))
                .build();

        //Assert
        Assertions.assertEquals(updateDoctor,userEntityConverterMock.toDoctorEntity(doctorConverterMock.fromDTO(sutResponse.getUpdatedDoctor())));
        assertThat(updateDoctor.getId()).isEqualTo(sutResponse.getUpdatedDoctor().getId());
        assertThat(updateDoctor.getName()).isEqualTo(sutResponse.getUpdatedDoctor().getName());

    }

    /**
     * @verifies return a list with all doctors when doctors are present
     * @see DoctorManagerImpl #getDoctors()( Doctor )
     */
    @Test
     void getDoctor_shouldReturnAListWithAllDoctorsWhenDoctorsArePresent() throws Exception {
        //Arrange
       UserRepository userRepositoryMock = mock(UserRepository.class);
       DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
       UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
       DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
       PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);


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
       when(passwordEncoderMock.encode("123")).thenReturn("123");

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

       DoctorManagerImpl sut =  new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);

        //Act
        GetAllDoctorsResponse sutResponse = GetAllDoctorsResponse.builder()
                .doctors(sut.getDoctors().stream().map(doctorConverterMock :: toDTO).toList())
                .build();
        //Assert
        Assertions.assertNotNull(sutResponse.getDoctors());
        assertThat(sutResponse.getDoctors()).hasSize(2);
    }

    /**
     * @verifies return an empty list when no doctors are present
     * @see DoctorManagerImpl #getDoctors()( Doctor )
     */
    @Test
     void getDoctor_shouldReturnAnEmptyListWhenNoDoctorsArePresent() throws Exception {
       //Arrange
       UserRepository userRepositoryMock = mock(UserRepository.class);
       DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
       UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
       DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
       PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

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
       when(passwordEncoderMock.encode("123")).thenReturn("123");

       DoctorManagerImpl sut =  new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);

       //Act
        GetAllDoctorsResponse sutResponse = GetAllDoctorsResponse.builder()
                .doctors(sut.getDoctors().stream().map(doctorConverterMock :: toDTO).toList())
                .build();

       //Assert
        assertThat(sutResponse.getDoctors()).isEmpty();
    }
}
