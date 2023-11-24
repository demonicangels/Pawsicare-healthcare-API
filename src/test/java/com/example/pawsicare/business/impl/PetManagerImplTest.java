package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.DTOs.PetDTO;
import com.example.pawsicare.business.responses.GetAllPetsResponse;
import com.example.pawsicare.domain.Gender;
import com.example.pawsicare.domain.Pet;
import com.example.pawsicare.persistence.PetEntityConverter;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.PetEntity;
import com.example.pawsicare.persistence.jpaRepositories.PetRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PetManagerImplTest {
    /**
     * @verifies return a response with list containing all pets with the specified ownerId when such pets are present
     * @see PetManagerImpl#getPets(long)( Pet )
     */
    @Test
     void getPets_shouldReturnAResponseWithListContainingAllPetsWithTheSpecifiedOwnerIdWhenSuchPetsArePresent() throws Exception {
        //Arrange
        PetRepository petRepositoryMock = mock(PetRepository.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);
        PetConverter petConverter = mock(PetConverter.class);

        ClientEntity client = ClientEntity.builder()
                        .id(1L)
                        .name("Nikol")
                        .email("nikol@mail.com")
                        .password("123").build();

        PetEntity petEntity1 = new PetEntity(1L,"maia",Gender.FEMALE,"Cat","12/12/2020","helloo", client);
        PetEntity petEntity2 = new PetEntity(2L,"nia",Gender.FEMALE,"Cat","13/12/2020","hiii",client);
        Pet pet1 = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");
        Pet pet2 = new Pet(2L,1L,"nia",Gender.FEMALE,"Cat","13/12/2020",null,"hiii");
        PetDTO petDTO1 = new PetDTO(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");
        PetDTO petDTO2 = new PetDTO(2L,1L,"nia", Gender.FEMALE,"Cat","13/12/2020",null,"hiii");

        when(petRepositoryMock.getPetEntitiesByClient_Id(1)).thenReturn(Arrays.asList(new PetEntity(1L,"maia", Gender.FEMALE,"Cat","12/12/2020","helloo", client), new PetEntity(2L,"nia", Gender.FEMALE,"Cat","13/12/2020","hiii",client)));
        when(petEntityConverter.fromEntity(petEntity1)).thenReturn(pet1);
        when(petEntityConverter.fromEntity(petEntity2)).thenReturn(pet2);
        when(petConverter.toDTO(pet1)).thenReturn(petDTO1);
        when(petConverter.toDTO(pet2)).thenReturn(petDTO2);

        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock,petEntityConverter);

        //Act
        GetAllPetsResponse sutResponse = GetAllPetsResponse.builder()
                .pets(sut.getPets(1L).stream().map(petConverter :: toDTO).toList())
                .build();
        //Assert
        assertThat(sutResponse.getPets()).hasSize(2);
        verify(petEntityConverter, times(1)).fromEntity(petEntity1);
    }


    /**
     * @verifies return a response with an empty list when there are no pets with the specified ownerId present
     * @see PetManagerImpl#getPets(long)(Pet)
     */
    @Test
     void getPets_shouldReturnAResponseWithAnEmptyListWhenThereAreNoPetsWithTheSpecifiedOwnerIdPresent() throws Exception {
        //Arrange
        PetRepository petRepositoryMock = mock(PetRepository.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);
        PetConverter petConverter = mock(PetConverter.class);
        Pet Pet = new Pet();
       List<PetDTO> petDTOList = new ArrayList<>();
       petDTOList.add(new PetDTO());
       petDTOList.add(new PetDTO());

        when(petRepositoryMock.getPetEntitiesByClient_Id(1L)).thenReturn(new ArrayList<>());
        when(petConverter.toDTO(Pet)).thenReturn(new PetDTO());

        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock,petEntityConverter);

        //Act
        GetAllPetsResponse sutResponse = GetAllPetsResponse.builder()
                .pets(sut.getPets(1L).stream().map(petConverter :: toDTO).toList())
                .build();
        //Assert
        assertThat(sutResponse.getPets()).isEmpty();
    }
}
