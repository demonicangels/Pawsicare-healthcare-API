package com.example.pawsicare.business.impl;

import com.example.pawsicare.business.dto.ClientDTO;
import com.example.pawsicare.business.dto.DoctorDTO;
import com.example.pawsicare.business.exceptions.InvalidCredentialsException;
import com.example.pawsicare.business.requests.LoginUserRequest;
import com.example.pawsicare.business.responses.LoginResponse;
import com.example.pawsicare.business.security.token.AccessTokenEncoder;
import com.example.pawsicare.business.security.token.impl.AccessTokenImpl;
import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.UserEntityConverter;
import com.example.pawsicare.persistence.entity.UserEntity;
import com.example.pawsicare.persistence.jparepositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    //partial integration tests

    /**
     * @verifies return a user when credentials are correct
     * @see AuthenticationServiceImpl#loginUser(LoginUserRequest)
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

        AuthenticationServiceImpl sut = new AuthenticationServiceImpl(doctorConverterMock,clientConverterMock,userEntityConverterMock,userRepositoryMock,passwordEncoder,accessTokenEncoder);

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
        when(sut.passMatch("123", "123")).thenReturn(true);
        when(sut.generateAccessToken(clientConverterMock.fromDTO(userDTO))).thenReturn("947563794");
        when(accessTokenEncoder.generateAccessToken(new AccessTokenImpl(1L, Role.Client))).thenReturn("947563794");

        //Act
        LoginUserRequest request = LoginUserRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword()).build();

        LoginResponse response = sut.loginUser(request);

        //Assert
        String accessToken = response.getAccessToken();
        assertNotNull(accessToken);
        assertTrue(accessToken.contains("947563794"));
    }

    /**
     * @verifies return exception if credentials are not correct
     * @see AuthenticationServiceImpl#loginUser(LoginUserRequest)
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

            AuthenticationServiceImpl sut = new AuthenticationServiceImpl(doctorConverterMock,clientConverterMock,userEntityConverterMock,userRepositoryMock,passwordEncoder,accessTokenEncoder);

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

            LoginResponse response = sut.loginUser(request);

            //Assert
            assertThat(sut.passMatch("123","999")).isFalse();
            assertThrows(InvalidCredentialsException.class, () -> sut.loginUser(request));

        }catch (InvalidCredentialsException e){

        }
    }

    /**
     * @verifies return an accessToken based on the loggedIn user
     * @see AuthenticationServiceImpl#generateAccessToken(User)
     */
    @Test
    void generateAccessToken_shouldReturnAnAccessTokenBasedOnTheLoggedInUser() throws Exception {

        //Arrange

        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        ClientConverter clientConverterMock = mock(ClientConverter.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        AuthenticationServiceImpl sut = new AuthenticationServiceImpl(doctorConverterMock,clientConverterMock,userEntityConverterMock,userRepositoryMock,passwordEncoder,accessTokenEncoder);

        User user = Client.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123").build();

        when(sut.generateAccessToken(user)).thenReturn("9575635accessTokenForNikol64782956");

        String expectedAccessToken = "9575635accessTokenForNikol64782956";

        //Act

        String actual = sut.generateAccessToken(user);

        //Assert
        assertNotNull(actual);
        assertSame(actual, expectedAccessToken);
    }

    /**
     * @verifies return IllegalArgument exception when user is null
     * @see AuthenticationServiceImpl#generateAccessToken(User)
     */
    @Test
    void generateAccessToken_shouldReturnIllegalArgumentExceptionWhenUserIsNull() throws Exception {

        try{
            //Arrange
            UserRepository userRepositoryMock = mock(UserRepository.class);
            DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
            ClientConverter clientConverterMock = mock(ClientConverter.class);
            UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
            PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
            AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

            AuthenticationServiceImpl sut = new AuthenticationServiceImpl(doctorConverterMock, clientConverterMock, userEntityConverterMock, userRepositoryMock, passwordEncoder, accessTokenEncoder);

            when(sut.generateAccessToken(null)).thenThrow(new IllegalArgumentException("User cannot be null"));

            //Act and Assert
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> sut.generateAccessToken(null));
            assertNotNull(exception);
            assertEquals("User cannot be null", exception.getMessage());

        }catch (IllegalArgumentException e){

        }
    }

    /**
     * @verifies return doctor obj if a doctor is logged in
     * @see AuthenticationServiceImpl#loginUser(LoginUserRequest)
     */
    @Test
    void userLogin_shouldReturnDoctorObjIfADoctorIsLoggedIn() throws Exception {

        // Arrange
        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        ClientConverter clientConverterMock = mock(ClientConverter.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        AuthenticationServiceImpl sut = new AuthenticationServiceImpl(doctorConverterMock, clientConverterMock, userEntityConverterMock, userRepositoryMock, passwordEncoder, accessTokenEncoder);

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123")
                .role(1).build();

        DoctorDTO userDTO = DoctorDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .birthday(userEntity.getBirthday())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .field(null)
                .build();

        Doctor user = Doctor.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(Role.Doctor).build();

        LoginUserRequest loginRequest = LoginUserRequest.builder()
                .email(userEntity.getEmail())
                .password(userEntity.getPassword()).build();


        when(userRepositoryMock.findUserEntityByEmail(loginRequest.getEmail())).thenReturn(Optional.of(userEntity));
        when(userEntityConverterMock.fromUserEntity(userEntity)).thenReturn(user);
        when(sut.passMatch(eq(user.getPassword()), any())).thenReturn(true);
        when(sut.generateAccessToken(user)).thenReturn("mockedAccessToken");
        when(doctorConverterMock.fromDTO(userDTO)).thenReturn(user);
        when(doctorConverterMock.fromDTO(any(DoctorDTO.class))).thenReturn(user);
        when(sut.generateAccessToken(user)).thenReturn("mockedAccessToken");

        // Act
        String accessToken = sut.loginUser(loginRequest).getAccessToken();

        // Assert
        assertEquals("mockedAccessToken", accessToken);

    }

    /**
     * @verifies return client obj if a client is logged in
     * @see AuthenticationServiceImpl#loginUser(LoginUserRequest)
     */
    @Test
    void userLogin_shouldReturnClientObjIfAClientIsLoggedIn() throws Exception {
        // Arrange
        UserRepository userRepositoryMock = mock(UserRepository.class);
        DoctorConverter doctorConverterMock = mock(DoctorConverter.class);
        ClientConverter clientConverterMock = mock(ClientConverter.class);
        UserEntityConverter userEntityConverterMock = mock(UserEntityConverter.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AccessTokenEncoder accessTokenEncoder = mock(AccessTokenEncoder.class);

        AuthenticationServiceImpl sut = new AuthenticationServiceImpl(doctorConverterMock, clientConverterMock, userEntityConverterMock, userRepositoryMock, passwordEncoder, accessTokenEncoder);

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("Nikol")
                .email("nikol@mail.com")
                .password("123")
                .role(0).build();

        ClientDTO userDTO = ClientDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .birthday(userEntity.getBirthday())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();

        Client user = Client.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(Role.Client).build();

        LoginUserRequest loginRequest = LoginUserRequest.builder()
                .email(userEntity.getEmail())
                .password(userEntity.getPassword()).build();


        when(userRepositoryMock.findUserEntityByEmail(loginRequest.getEmail())).thenReturn(Optional.of(userEntity));
        when(userEntityConverterMock.fromUserEntity(userEntity)).thenReturn(user);
        when(sut.passMatch(eq(user.getPassword()), any())).thenReturn(true);
        when(sut.generateAccessToken(user)).thenReturn("mockedAccessToken");
        when(clientConverterMock.fromDTO(userDTO)).thenReturn(user);
        when(clientConverterMock.fromDTO(any(ClientDTO.class))).thenReturn(user);
        when(sut.generateAccessToken(user)).thenReturn("mockedAccessToken");

        // Act
        String accessToken = sut.loginUser(loginRequest).getAccessToken();

        // Assert
        assertEquals("mockedAccessToken", accessToken);

    }
}
