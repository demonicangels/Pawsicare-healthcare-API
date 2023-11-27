package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.AppointmentDTO;
import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.dto.PetDTO;
import com.example.pawsicare.domain.Appointment;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.Pet;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppointmentConverterTest {

    /**
     * @verifies return an AppointmentDTO after successful conversion
     * @see AppointmentConverter#toDTO(Appointment)
     */
    @Test
    void toDTO_shouldReturnAnAppointmentDTOAfterSuccessfulConversion() throws Exception {
        //Arrange
        AppointmentConverter converterMock = mock(AppointmentConverter.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        ClientConverter clientConverterMock = mock(ClientConverter.class);
        PetConverter petConverterMock = mock(PetConverter.class);

        Doctor doc = Doctor.builder()
                .id(1L)
                .name("Ana")
                .email("ana@mail.com")
                .password("123")
                .field("neurology")
                .build();

        DoctorDTO docDTO = DoctorDTO.builder()
                .id(1L)
                .name("Ana")
                .email("ana@mail.com")
                .password("123")
                .field("neurology")
                .build();

        Client cli = Client.builder()
                .id(2L)
                .name("Nia")
                .email("nia@gmail.com")
                .password("123")
                .build();

        ClientDTO cliDTO = ClientDTO.builder()
                .id(2L)
                .name("Nia")
                .email("nia@gmail.com")
                .password("123")
                .build();

        Pet pet = Pet.builder()
                .id(1L)
                .name("Zara")
                .birthday("2023-05-11")
                .build();

        PetDTO petDTO = PetDTO.builder()
                .id(1L)
                .name("Zara")
                .birthday("2023-05-11")
                .build();

        Appointment appointment = Appointment.builder()
                        .id(1L)
                        .dateAndEnd(LocalDateTime.of(2023,05,11,11,30))
                        .dateAndStart(LocalDateTime.of(2023,05,11,10,0))
                        .doctor(doc)
                        .client(cli)
                        .pet(pet).build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndEnd(LocalDateTime.of(2023,05,11,11,30))
                .dateAndStart(LocalDateTime.of(2023,05,11,10,0))
                .doctor(docDTO)
                .client(cliDTO)
                .pet(petDTO).build();


        when(converterMock.toDTO(appointment)).thenReturn(appointmentDTO);
        when(doctorConverterMock.toDTO(doc)).thenReturn(docDTO);
        when(clientConverterMock.toDTO(cli)).thenReturn(cliDTO);
        when(petConverterMock.toDTO(pet)).thenReturn(petDTO);

        //Act

        AppointmentConverter converter = new AppointmentConverter(doctorConverterMock,clientConverterMock,petConverterMock);
        AppointmentDTO actualResult = converter.toDTO(appointment);

        //Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isNotZero();
        assertThat(actualResult.getId()).isEqualTo(appointmentDTO.getId());
        assertThat(actualResult.getClass()).isEqualTo(AppointmentDTO.class);
    }

    /**
     * @verifies return an Appointment after successful conversion
     * @see AppointmentConverter#fromDTO(AppointmentDTO)
     */
    @Test
    void fromDTO_shouldReturnAnAppointmentAfterSuccessfulConversion() throws Exception {

        AppointmentConverter converterMock = mock(AppointmentConverter.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        ClientConverter clientConverterMock = mock(ClientConverter.class);
        PetConverter petConverterMock = mock(PetConverter.class);

        Doctor doc = Doctor.builder()
                .id(1L)
                .name("Ana")
                .email("ana@mail.com")
                .password("123")
                .field("neurology")
                .build();

        DoctorDTO docDTO = DoctorDTO.builder()
                .id(1L)
                .name("Ana")
                .email("ana@mail.com")
                .password("123")
                .field("neurology")
                .build();

        Client cli = Client.builder()
                .id(2L)
                .name("Nia")
                .email("nia@gmail.com")
                .password("123")
                .build();

        ClientDTO cliDTO = ClientDTO.builder()
                .id(2L)
                .name("Nia")
                .email("nia@gmail.com")
                .password("123")
                .build();

        Pet pet = Pet.builder()
                .id(1L)
                .name("Zara")
                .birthday("2023-05-11")
                .build();

        PetDTO petDTO = PetDTO.builder()
                .id(1L)
                .name("Zara")
                .birthday("2023-05-11")
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .dateAndEnd(LocalDateTime.of(2023,05,11,11,30))
                .dateAndStart(LocalDateTime.of(2023,05,11,10,0))
                .doctor(doc)
                .client(cli)
                .pet(pet).build();

        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .id(1L)
                .dateAndEnd(LocalDateTime.of(2023,05,11,11,30))
                .dateAndStart(LocalDateTime.of(2023,05,11,10,0))
                .doctor(docDTO)
                .client(cliDTO)
                .pet(petDTO).build();


        when(converterMock.fromDTO(appointmentDTO)).thenReturn(appointment);
        when(doctorConverterMock.fromDTO(docDTO)).thenReturn(doc);
        when(clientConverterMock.fromDTO(cliDTO)).thenReturn(cli);
        when(petConverterMock.fromDTO(petDTO)).thenReturn(pet);

        //Act

        AppointmentConverter converter = new AppointmentConverter(doctorConverterMock,clientConverterMock,petConverterMock);
        Appointment actualResult = converter.fromDTO(appointmentDTO);

        //Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getDateAndEnd()).isNotNull();
        assertThat(actualResult.getDateAndStart()).isEqualTo(appointmentDTO.getDateAndStart());
        assertThat(actualResult.getClass()).isEqualTo(Appointment.class);

    }
}
