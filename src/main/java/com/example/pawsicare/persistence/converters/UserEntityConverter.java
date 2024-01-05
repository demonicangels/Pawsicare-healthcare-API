package com.example.pawsicare.persistence.converters;

import com.example.pawsicare.domain.Client;
import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.Role;
import com.example.pawsicare.domain.User;
import com.example.pawsicare.persistence.entity.ClientEntity;
import com.example.pawsicare.persistence.entity.DoctorEntity;
import com.example.pawsicare.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;


@Service
public class UserEntityConverter {

    public User fromUserEntity (UserEntity entity){
        if (entity != null) {
            if (entity.getRole() == 0) {
                return fromClientEntity((ClientEntity) entity);
            } else if (entity.getRole() == 1) {
                return fromDoctorEntity((DoctorEntity) entity);
            } else {
                throw new IllegalArgumentException("Unsupported entity type.");
            }
        }
        else{
            throw new IllegalArgumentException("UserEntity cannot be null");
        }

    }
    public UserEntity toUserEntity (User user){

        if (user.getRole() == Role.Client) {
            return toClientEntity((Client) user);
        } else if (user.getRole() == Role.Doctor) {
            return toDoctorEntity((Doctor) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type.");
        }
    }

    public Client fromClientEntity (ClientEntity client){
        if(client == null){
            return null;
        }
         return Client.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                 .role(toRole(client))
                .build();
    }

    public ClientEntity toClientEntity (Client client){

        if(client == null){
            return null;
        }
        return ClientEntity.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .role(Role.Client.ordinal())
                .build();
    }

    public Doctor fromDoctorEntity (DoctorEntity doctor){
        if(doctor == null){
            return null;
        }
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
                .role(toRole(doctor))
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
                .role(Role.Doctor.ordinal())
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
