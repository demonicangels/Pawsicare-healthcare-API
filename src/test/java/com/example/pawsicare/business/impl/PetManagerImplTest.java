package com.example.pawsicare.business.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PetManagerImplTest {
//    /**
//     * @verifies return a response with list containing all pets with the specified ownerId when such pets are present
//     * @see PetManagerImpl#getPets(long)( Pet )
//     */
//    @Test
//     void createPet_shouldReturnAResponseWithListContainingAllPetsWithTheSpecifiedOwnerIdWhenSuchPetsArePresent() throws Exception {
//        //Arrange
//        PetRepository petRepositoryMock = mock(PetRepository.class);
//        when(petRepositoryMock.getPets(1)).thenReturn(Arrays.asList(new Pet(1,1,"maia","12/12/2020",2,"hi"), new Pet(2,1,"nia","13/12/2020",2,"hello")));
//        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock);
//
//        //Act
//        GetAllPetsResponse sutResponse = GetAllPetsResponse.builder()
//                .pets(sut.getPets(1))
//                .build();
//        //Assert
//        assertThat(sutResponse.getPets()).hasSize(2);
//    }
//
//
//    /**
//     * @verifies return a response with an empty list when there are no pets with the specified ownerId present
//     * @see PetManagerImpl#getPets(long)( Pet )
//     */
//    @Test
//     void createPet_shouldReturnAResponseWithAnEmptyListWhenThereAreNoPetsWithTheSpecifiedOwnerIdPresent() throws Exception {
//        //Arrange
//        PetRepository petRepositoryMock = mock(PetRepository.class);
//        when(petRepositoryMock.getPets(1)).thenReturn(new ArrayList<Pet>());
//        PetManagerImpl sut = new PetManagerImpl(petRepositoryMock);
//
//        //Act
//        GetAllPetsResponse sutResponse = GetAllPetsResponse.builder()
//                .pets(sut.getPets(1))
//                .build();
//        //Assert
//        assertThat(sutResponse.getPets()).isEmpty();
//    }
}
