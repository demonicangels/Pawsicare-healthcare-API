package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.client;
import com.example.pawsicare.domain.doctor;
import com.example.pawsicare.domain.role;
import com.example.pawsicare.domain.user;
import com.example.pawsicare.persistence.entity.clientEntity;
import com.example.pawsicare.persistence.entity.doctorEntity;
import com.example.pawsicare.persistence.entity.userEntity;
import org.springframework.stereotype.Service;

@Service
public class userEntityConverter {

    public user fromUserEntity (userEntity entity){
        if (entity.getRole().equals(0)) {
            return fromClientEntity((clientEntity) entity);
        } else if (entity.getRole().equals(1)) {
            return fromDoctorEntity((doctorEntity) entity);
        } else {
            throw new IllegalArgumentException("Unsupported entity type.");
        }
    }
    public userEntity toUserEntity (user user){

        if (user.getRole().equals(0)) {
            return toClientEntity((client) user);
        } else if (user.getRole().equals(1)) {
            return toDoctorEntity((doctor) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type.");
        }
    }

    public client fromClientEntity (clientEntity client){

         return com.example.pawsicare.domain.client.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public clientEntity toClientEntity (client client){

        return clientEntity.builder()
                .id(client.getId())
                .name(client.getName())
                .birthday(client.getBirthday())
                .password(client.getPassword())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public doctor fromDoctorEntity (doctorEntity doctor){
        return com.example.pawsicare.domain.doctor.builder()
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

    public doctorEntity toDoctorEntity (doctor doctor){

        doctorEntity entity = doctorEntity.builder()
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

    public role toRole(userEntity entity){

        return switch (entity.getRole()){
            case 0 ->  role.Client;
            case 1 ->  role.Doctor;
            default -> throw new IllegalArgumentException("Role not recognized");
        };
    }
}
