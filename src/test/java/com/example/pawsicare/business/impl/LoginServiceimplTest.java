package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.LoginResponse;
import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class LoginServiceimplTest {

    //partial integration tests
    //TODO test also the generate access token method

    /**
     * @verifies return a user when credentials are correct
     * @see LoginServiceimpl#userLogin(LoginUserRequest)
     */
    @Test
     void userLogin_shouldReturnAUserWhenCredentialsAreCorrect() throws Exception {

        //Arrange

        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        ClientConverter clientConverterMock = mock(ClientConverter.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        User user = Client.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        ClientDTO userDTO = ClientDTO.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        LoginServiceimpl sut = new LoginServiceimpl(doctorConverterMock,clientConverterMock,userEntityConverterMock,userRepositoryMock,passwordEncoder,accessTokenEncoder);

        when(userRepositoryMock.findUserEntityByEmail("nikol@mail.com")).thenReturn(Optional.ofNullable(userEntity));
        when(userEntityConverterMock.fromUserEntity(userEntity)).thenReturn(Client.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build());
        when(clientConverterMock.fromDTO(any(ClientDTO.class))).thenReturn(Client.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build());
        when(sut.passMatch("123","123")).thenReturn(true);
        when(passwordEncoder.matches("123","123")).thenReturn(true);
        when(sut.generateAccessToken(clientConverterMock.fromDTO(userDTO))).thenReturn("947563794");
        when(accessTokenEncoder.encode(new AccessTokenImpl(1L, Role.Client))).thenReturn("947563794");

        //Act
        LoginUserRequest request = LoginUserRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword()).build();

        LoginResponse response = sut.userLogin(request);

        //Assert
        String accessToken = response.getAccessToken();
        assertNotNull(accessToken);
        assertTrue(accessToken.contains("947563794"));
    }

    /**
     * @verifies return exception if credentials are not correct
     * @see LoginServiceimpl#userLogin(LoginUserRequest)
     */
    @Test
    void userLogin_shouldReturnExceptionIfCredentialsAreNotCorrect() throws Exception {
        try{
            UserRepository userRepositoryMock = mock(UserRepository.class);
            DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
            ClientConverter clientConverterMock = mock(ClientConverter.class);
            UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
            PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
            AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

            UserEntity userEntity = UserEntity.builder()
                    .id(1L)
                    .name("Nikol")
                    .email("nikol@mail.com")
                    .password("123").build();

            User user = Client.builder()
                    .id(1L)
                    .name("Nikol")
                    .email("nikol@mail.com")
                    .password("123").build();

            ClientDTO userDTO = ClientDTO.builder()
                    .id(1L)
                    .name("Nikol")
                    .email("nikol@mail.com")
                    .password("123").build();

            LoginServiceimpl sut = new LoginServiceimpl(doctorConverterMock,clientConverterMock,userEntityConverterMock,userRepositoryMock,passwordEncoder,accessTokenEncoder);

            when(userRepositoryMock.findUserEntityByEmail("nikol@mail.com")).thenReturn(Optional.ofNullable(userEntity));
            when(userEntityConverterMock.fromUserEntity(userEntity)).thenReturn(Client.builder()
                    .id(1L)
                    .name("Nikol")
                    .email("nikol@mail.com")
                    .password("123").build());
            when(clientConverterMock.fromDTO(any(ClientDTO.class))).thenReturn(Client.builder()
                    .id(1L)
                    .name("Nikol")
                    .email("nikol@mail.com")
                    .password("123").build());
            when(sut.passMatch("123","999")).thenReturn(false);

            //Act
            LoginUserRequest request = LoginUserRequest.builder()
                    .email(user.getEmail())
                    .password("999").build();

            LoginResponse response = sut.userLogin(request);

            //Assert
            assertThat(sut.passMatch("123","999")).isFalse();
            assertThrows(InvalidCredentialsException.class, () -> sut.userLogin(request));

        }catch (InvalidCredentialsException e){

        }
    }
}
