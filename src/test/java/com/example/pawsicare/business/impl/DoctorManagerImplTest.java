package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.converters.DoctorConverter;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.responses.CreateDoctorResponse;
import com.example.pawsicare.business.responses.GetAllDoctorsResponse;
import com.example.pawsicare.business.responses.UpdateDoctorResponse;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.managerinterfaces.DoctorManager;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.DoctorRepository;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import com.example.pawsicare.persistence.converters.UserEntityConverter;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DoctorManagerImplTest {

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
        assertNotNull(sutResponse.getDoctor());
        assertThat(sutResponse.getDoctor().getName()).isEqualTo("Amara");
        assertThat(sutResponse.getDoctor().getId()).isNotZero();

        verify(userRepositoryMock, times(1)).save(doctor);
        verify(userEntityConverterMock, times(2)).fromDoctorEntity(doctor);
        verify(doctorConverterMock, times(1)).toDTO(doctor2);
        verify(userEntityConverterMock, times(1)).toDoctorEntity(doctor2);

    }


    /**
     * @verifies return a list with all doctors when doctors are present
     * @see DoctorManagerImpl#getDoctors()
     */
    @Test
    void getDoctors_shouldReturnAListWithAllDoctorsWhenDoctorsArePresent() throws Exception {
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
        assertNotNull(sutResponse.getDoctors());
        assertThat(sutResponse.getDoctors()).hasSize(2);
    }

    /**
     * @verifies return an empty list when no doctors are present
     * @see DoctorManagerImpl#getDoctors()
     */
    @Test
    void getDoctors_shouldReturnAnEmptyListWhenNoDoctorsArePresent() throws Exception {
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

    /**
     * @verifies return doctor when one with matching id is found
     * @see DoctorManagerImpl#getDoctor(long)
     */
    @Test
    void getDoctor_shouldReturnDoctorWhenOneWithMatchingIdIsFound() throws Exception {
        //Arrange
        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);

        DoctorEntity updateDoctor = DoctorEntity.builder()
                .id(1L)
                .name("Nia")
                .field("neurology")
                .email("nia@mail.com")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Nia")
                .field("neurology")
                .email("nia@mail.com")
                .build();

        when(userEntityConverterMock.fromDoctorEntity(updateDoctor)).thenReturn(doctor);
        when(userRepositoryMock.getUserEntityById(doctor.getId())).thenReturn(Optional.of(updateDoctor));
        //Act

        Doctor doc = sut.getDoctor(doctor.getId());

        //Assert
        assertNotNull(doc);
        assertEquals(doctor,doc);
        assertEquals(doctor.getId(),doc.getId());
    }

    /**
     * @verifies return a list of doctors from the specified field
     * @see DoctorManagerImpl#getDoctorsByField(String)
     */
    @Test
    void getDoctorsByField_shouldReturnAListOfDoctorsFromTheSpecifiedField() throws Exception {

        //Arrange
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        DoctorEntity updateDoctor = DoctorEntity.builder()
                .id(1L)
                .name("Nia")
                .field("neurology")
                .email("nia@mail.com")
                .build();

        DoctorEntity doctorEntity = DoctorEntity.builder()
                .id(2L)
                .name("Ana")
                .field("neurology")
                .email("ana@mail.com")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Nia")
                .field("neurology")
                .email("nia@mail.com")
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(2L)
                .name("Ana")
                .field("neurology")
                .email("ana@mail.com")
                .build();


        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);



        List<DoctorEntity> doctorEntities = Arrays.asList(doctorEntity,updateDoctor);

        when(userEntityConverterMock.fromDoctorEntity(updateDoctor)).thenReturn(doctor);
        when(userEntityConverterMock.fromDoctorEntity(doctorEntity)).thenReturn(doctor2);
        when(doctorRepositoryMock.getDoctorEntitiesByField(doctor.getField())).thenReturn(Optional.of(doctorEntities));

        //Act

        List<Doctor> doctors = sut.getDoctorsByField(doctor.getField());

        //Assert
        assertNotNull(doctors);
        assertFalse(doctors.isEmpty());
        assertEquals(2, doctors.size());
    }

    /**
     * @verifies verify if the deleteById method of the repository is being called
     * @see DoctorManagerImpl#deleteDoctor(long)
     */
    @Test
    void deleteDoctor_shouldVerifyIfTheDeleteByIdMethodOfTheRepositoryIsBeingCalled() throws Exception {
        //Arrange
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverterMock,userRepositoryMock,doctorRepositoryMock,passwordEncoderMock);

        //Act
        sut.deleteDoctor(1L);

        //Assert
        verify(userRepositoryMock, times(1)).deleteUserEntityById(1L);
    }

    /**
     * @verifies verify the repository method is called correctly
     * @see DoctorManagerImpl#updateDoctor(Doctor)
     */
    @Test
    void updateDoctor_shouldVerifyTheRepositoryMethodIsCalledCorrectly() throws Exception {

        //Arrange
        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        DoctorEntity updateDoctor = DoctorEntity.builder()
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

        sut.updateDoctor(userEntityConverterMock.fromDoctorEntity(updateDoctor));


        //Assert
        verify(userRepositoryMock).updateUserEntityById(updateDoctor.getId(),updateDoctor.getEmail(),updateDoctor.getPhoneNumber(),updateDoctor.getPassword());
    }

    /**
     * @verifies set the values of the variables from the object from the db when their values are null
     * @see DoctorManagerImpl#updateDoctor(Doctor)
     */
    @Test
    void updateDoctor_shouldSetTheValuesOfTheVariablesFromTheObjectFromTheDbWhenTheirValuesAreNull() throws Exception {

        PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);

        // Arrange
        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverter,userRepositoryMock,doctorRepository,passwordEncoderMock);

        DoctorEntity updateDoctor = DoctorEntity.builder()
                .id(1L)
                .phoneNumber("09876422")
                .email("nia@mail.com")
                .password("123")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .phoneNumber("09876422")
                .email(null)
                .password(null)
                .build();

        when(userRepositoryMock.getUserEntityById(updateDoctor.getId())).thenReturn(Optional.of(updateDoctor));
        when(passwordEncoderMock.encode("123")).thenReturn("123");

        // Act
        sut.updateDoctor(doctor);

        // Assert
        verify(userRepositoryMock, times(2)).getUserEntityById(updateDoctor.getId());
        verify(userRepositoryMock).updateUserEntityById(
                updateDoctor.getId(),
                updateDoctor.getEmail(),
                updateDoctor.getPhoneNumber(),
                updateDoctor.getPassword()
        );
    }

    /**
     * @verifies set the values of the variables from the object from the db when their values are empty
     * @see DoctorManagerImpl#updateDoctor(Doctor)
     */
    @Test
    void updateDoctor_shouldSetTheValuesOfTheVariablesFromTheObjectFromTheDbWhenTheirValuesAreEmpty() throws Exception {
        // Arrange
        UserEntityConverter userEntityConverter = mock(UserEntityConverter.class);
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserRepository userRepository = mock(UserRepository.class);

        DoctorManagerImpl sut = new DoctorManagerImpl(userEntityConverter, userRepository, doctorRepositoryMock, passwordEncoder);

        DoctorEntity updateDoctorEntity = DoctorEntity.builder()
                .id(1L)
                .email("email@example.com")
                .phoneNumber("123456789")
                .password("newPassword")
                .build();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .email("")
                .phoneNumber("")
                .password("")
                .build();

        when(userRepository.getUserEntityById(doctor.getId())).thenReturn(Optional.ofNullable(updateDoctorEntity));

        // Act
        sut.updateDoctor(doctor);

        // Assert
        verify(userRepository, times(2)).getUserEntityById(doctor.getId());
        verify(userRepository).updateUserEntityById(
                doctor.getId(),
                updateDoctorEntity.getEmail(),
                updateDoctorEntity.getPhoneNumber(),
                updateDoctorEntity.getPassword()
        );

        assertEquals(doctor.getEmail(), updateDoctorEntity.getEmail());
        assertEquals(doctor.getPassword(), updateDoctorEntity.getPassword());
    }
}

