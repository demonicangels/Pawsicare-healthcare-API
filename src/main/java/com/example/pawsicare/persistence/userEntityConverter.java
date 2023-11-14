package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class userEntityConverter {

    public User fromUserEntity (UserEntity entity){
        if (entity.getRole().equals(0)) {
            return fromClientEntity((ClientEntity) entity);
        } else if (entity.getRole().equals(1)) {
            return fromDoctorEntity((DoctorEntity) entity);
        } else {
            throw new IllegalArgumentException("Unsupported entity type.");
        }
    }
    public UserEntity toUserEntity (User user){

        if (user.getRole().equals(0)) {
            return toClientEntity((Client) user);
        } else if (user.getRole().equals(1)) {
            return toDoctorEntity((Doctor) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type.");
        }
    }

    public Client fromClientEntity (ClientEntity client){

         return Client.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public ClientEntity toClientEntity (Client client){

        return ClientEntity.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public Doctor fromDoctorEntity (DoctorEntity doctor){
        return Doctor.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .birthday(doctor.getBirthday())
                .password(doctor.getPassword())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .description(doctor.getDescription())
                .field(doctor.getField())
                .image(doctor.getImage())
                //.role(toRole(doctor))
                .build();
    }

    public DoctorEntity toDoctorEntity (Doctor doctor){

        DoctorEntity entity = DoctorEntity.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .birthday(doctor.getBirthday())
                .password(doctor.getPassword())
                .email(doctor.getEmail())
                .field(doctor.getField())
                .phoneNumber(doctor.getPhoneNumber())
                .image(doctor.getImage())
                //.role(doctor.getRole().ordinal())
                .build();

        return entity;
    }

    public Role toRole(UserEntity entity){

        return switch (entity.getRole()){
            case 0 ->  Role.Client;
            case 1 ->  Role.Doctor;
            default -> throw new IllegalArgumentException("Role not recognized");
        };
    }
}
