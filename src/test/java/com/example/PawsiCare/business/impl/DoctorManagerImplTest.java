package com.example.PawsiCare.business.impl;

import com.example.PawsiCare.business.domain.Doctor;
import com.example.PawsiCare.business.repositories.DoctorRepository;
import com.example.PawsiCare.business.responses.CreateDoctorResponse;
import com.example.PawsiCare.business.responses.GetAllDoctorsResponse;
import com.example.PawsiCare.business.responses.UpdateDoctorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class DoctorManagerImplTest {
    /**
     * @verifies return a filled in doctor object when the doctor is created
     * @see DoctorManagerImpl#createDoctor(Doctor)
     */
    @Test
    public void createDoctor_shouldReturnAFilledInDoctorObjectWhenTheDoctorIsCreated() throws Exception {
        //Arrange
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Amara")
                .age(22)
                .field("cardiology")
                .email("amara@mail.com")
                .phoneNumber("+316395853793")
                .password("123")
                .build();

        when(doctorRepositoryMock.createDoctor(doctor)).thenReturn(doctor);

        //Act
        DoctorManagerImpl sut = new DoctorManagerImpl(doctorRepositoryMock);
        CreateDoctorResponse sutResponse = CreateDoctorResponse.builder()
                .doctor(sut.createDoctor(doctor))
                .build();
        //Assert
        Assertions.assertNotNull(sutResponse.getDoctor());
        assertThat(sutResponse.getDoctor().getName()).isEqualTo("Amara");
        assertThat(sutResponse.getDoctor().getId()).isNotEqualTo(0);

    }

    /**
     * @verifies return a doctor object with updated fields
     * @see DoctorManagerImpl#updateDoctor(long, Doctor)(com.example.PawsiCare.business.domain.Doctor)
     */
    @Test
    public void updateDoctor_shouldReturnADoctorObjectWithUpdatedFields() throws Exception {
        //Arrange
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        Doctor updateDoctor = Doctor.builder()
                        .name("Nia")
                        .field("neurology")
                        .age(13)
                        .email("nia@mail.com")
                        .build();

        when(doctorRepositoryMock.updateDoctor(1, updateDoctor)).thenReturn(updateDoctor);
        DoctorManagerImpl sut = new DoctorManagerImpl(doctorRepositoryMock);

        //Act
        UpdateDoctorResponse sutResponse = UpdateDoctorResponse.builder()
                .updatedDoctor(sut.updateDoctor(1,updateDoctor))
                .build();
        //Assert
        Assertions.assertEquals(updateDoctor,sutResponse.getUpdatedDoctor());
        assertThat(updateDoctor.getId()).isEqualTo(sutResponse.getUpdatedDoctor().getId());
        assertThat(updateDoctor.getName()).isEqualTo(sutResponse.getUpdatedDoctor().getName());

    }

    /**
     * @verifies return a list with all doctors when doctors are present
     * @see DoctorManagerImpl#getDoctors()(com.example.PawsiCare.business.domain.Doctor)
     */
    @Test
    public void getDoctor_shouldReturnAListWithAllDoctorsWhenDoctorsArePresent() throws Exception {
        //Arrange
        DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
        when(doctorRepositoryMock.getDoctors()).thenReturn(Arrays.asList(new Doctor(1, "Zara", 35, "123", "ne znam", "neurology", "maia@gmail.com", "+1234567"), new Doctor(2, "Maia", 35, "123", "ne znam", "neurology", "maia@gmail.com", "+1234567")));
        DoctorManagerImpl sut = new DoctorManagerImpl(doctorRepositoryMock);

        //Act
        GetAllDoctorsResponse sutResponse = GetAllDoctorsResponse.builder()
                .doctors(sut.getDoctors())
                .build();
        //Assert
        Assertions.assertNotNull(sutResponse.getDoctors());
        assertThat(sutResponse.getDoctors().size()).isEqualTo(2);
    }

    /**
     * @verifies return an empty list when no doctors are present
     * @see DoctorManagerImpl#getDoctors()(com.example.PawsiCare.business.domain.Doctor)
     */
    @Test
    public void getDoctor_shouldReturnAnEmptyListWhenNoDoctorsArePresent() throws Exception {
       //Arrange
       DoctorRepository doctorRepositoryMock = mock(DoctorRepository.class);
       when(doctorRepositoryMock.getDoctors()).thenReturn(new ArrayList<Doctor>());
       DoctorManagerImpl sut =  new DoctorManagerImpl(doctorRepositoryMock);

       //Act
        GetAllDoctorsResponse sutResponse = GetAllDoctorsResponse.builder()
                .doctors(sut.getDoctors())
                .build();
       //Assert
        assertThat(sutResponse.getDoctors().isEmpty());
    }
}
