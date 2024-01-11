package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.converters.PetConverter;
import com.example.pawsicare.business.dto.PetDTO;
import com.example.pawsicare.business.responses.GetAllPetsResponse;
import com.example.pawsicare.domain.Gender;
import com.example.pawsicare.domain.Pet;
import com.example.pawsicare.persistence.converters.PetEntityConverter;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.PetEntity;
import com.example.pawsicare.persistence.jparepositories.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    /**
     * @verifies return a pet when the id matches
     * @see PetManagerImpl#getPet(long)
     */
    @Test
    void getPet_shouldReturnAPetWhenTheIdMatches() throws Exception {
        //Arrange
        PetRepository petRepositoryMock = mock(PetRepository.class);
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);

        Pet pet1 = new Pet(1L,1L,"maia", Gender.FEMALE,"Cat","12/12/2020",null,"helloo");

        PetEntity petEntity = PetEntity.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        when(petEntityConverter.fromEntity(petEntity)).thenReturn(pet1);
        when(petEntityConverter.toEntity((pet1))).thenReturn(petEntity);
        when(petRepositoryMock.getPetEntityById(pet1.getId())).thenReturn(petEntity);

        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock,petEntityConverter);

        //Act

         Pet pet = sut.getPet(pet1.getId());

        //Assert
        assertNotNull(pet);
        assertEquals(pet.getId(), pet1.getId());

    }

    /**
     * @verifies create pet object with all fields when created
     * @see PetManagerImpl#createPet(Pet)
     */
    @Test
    void createPet_shouldCreatePetObjectWithAllFieldsWhenCreated() throws Exception {

        //Arrange
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);
        PetRepository petRepository = mock(PetRepository.class);

        PetManagerImpl sut = new PetManagerImpl(petRepository,petEntityConverter);

        PetEntity petEntity = PetEntity.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        Pet pet = Pet.builder()
                .id(1L)
                .name("maia")
                .gender(Gender.FEMALE)
                .build();

        when(petRepository.save(petEntity)).thenReturn(petEntity);
        when(petEntityConverter.fromEntity(petEntity)).thenReturn(pet);
        when(petEntityConverter.toEntity(pet)).thenReturn(petEntity);
        //Act

        Pet savedPet = sut.createPet(pet);

        //Assert
        assertNotNull(savedPet);
        assertEquals(savedPet.getId(),pet.getId());
        assertEquals(savedPet.getName(),pet.getName());


    }

    /**
     * @verifies verify if the deleteById method of the repository is being called
     * @see PetManagerImpl#deletePet(long)
     */
    @Test
    void deletePet_shouldVerifyIfTheDeleteByIdMethodOfTheRepositoryIsBeingCalled() throws Exception {

        //Arrange
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);
        PetRepository petRepository = mock(PetRepository.class);

        PetManagerImpl sut = new PetManagerImpl(petRepository,petEntityConverter);

        //Act

        sut.deletePet(1L);

        //Assert
        verify(petRepository, times(1)).deleteById(1L);
    }

    /**
     * @verifies verify the repository method is called correctly
     * @see PetManagerImpl#updatePet(Pet)
     */
    @Test
    void updatePet_shouldVerifyTheRepositoryMethodIsCalledCorrectly() throws Exception {
        // Arrange
        PetRepository petRepositoryMock = mock(PetRepository.class);
        PetEntityConverter petEntityConverterMock = mock(PetEntityConverter.class);

        PetEntity updatePetEntity = PetEntity.builder()
                .id(1L)
                .name("Fluffy")
                .information("Cute pet")
                .build();

        Pet pet = Pet.builder()
                .id(1L)
                .name(null)
                .information(null)
                .build();

        when(petRepositoryMock.getPetEntityById(pet.getId())).thenReturn(updatePetEntity);

        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock,petEntityConverterMock);

        // Act
        sut.updatePet(pet);

        // Assert
        verify(petRepositoryMock).getPetEntityById(pet.getId());
        verify(petRepositoryMock).updatePetEntityById(
                pet.getId(),
                updatePetEntity.getName(),
                updatePetEntity.getInformation()
        );
    }

    /**
     * @verifies set the values of the variables from the object from the db when their values are null or empty
     * @see PetManagerImpl#updatePet(Pet)
     */
    @Test
    void updatePet_shouldSetTheValuesOfTheVariablesFromTheObjectFromTheDbWhenTheirValuesAreNullOrEmpty() throws Exception {
        // Arrange
        PetEntityConverter petEntityConverter = mock(PetEntityConverter.class);
        PetRepository petRepositoryMock = mock(PetRepository.class);

        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock,petEntityConverter);

        PetEntity updatePetEntity = PetEntity.builder()
                .id(1L)
                .name("Fluffy")
                .information("Cute pet")
                .build();

        Pet pet = Pet.builder()
                .id(1L)
                .name(null)
                .information(null)
                .build();

        when(petRepositoryMock.getPetEntityById(pet.getId())).thenReturn(updatePetEntity);

        // Act
        sut.updatePet(pet);

        // Assert
        verify(petRepositoryMock).getPetEntityById(pet.getId());
        verify(petRepositoryMock).updatePetEntityById(
                pet.getId(),
                updatePetEntity.getName(),
                updatePetEntity.getInformation()
        );
        assertEquals(pet.getName(), updatePetEntity.getName());
        assertEquals(pet.getInformation(), updatePetEntity.getInformation());
    }
}
