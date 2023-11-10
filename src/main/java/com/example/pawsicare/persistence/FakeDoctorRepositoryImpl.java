package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.doctor;
import com.example.pawsicare.domain.role;
import org.springframework.stereotype.Repository;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeDoctorRepositoryImpl implements DoctorRepository {

    private final List<doctor> doctors;
    private Long doctorId = 3L;

    static String birthday = "1-1-1999";

    public FakeDoctorRepositoryImpl(){
        this.doctors = new ArrayList<>();

        doctors.add(doctor.builder()
                .id(1L)
                .name("Maia")
                .birthday(birthday)
                .password("123")
                .image("https://media.istockphoto.com/id/916855812/photo/smiling-veterinarian-with-dog-and-digital-tablet.jpg?s=612x612&w=0&k=20&c=5i3S8AaEEqDuNvos2sFabaep7BeLwcHGwW_thNpyszE=")
                .email("maia@gmail.com")
                .phoneNumber("+1234567")
                .role(role.Doctor)
                .field("neurology")
                .build());



        doctors.add(doctor.builder()
                .id(2L)
                .name("Nia")
                .birthday(birthday)
                .password("123")
                .image("https://media.istockphoto.com/id/1386206447/photo/brown-border-collie-dog-during-visit-in-vet.jpg?s=612x612&w=0&k=20&c=xVRfoZh1CLbKRoGYOIucSSO6nobJSQVmUw9a5Cpo3UA=")
                .email("nia@gmail.com")
                .phoneNumber("+157897")
                .role(role.Doctor)
                .field("neurology")
                .build());

        doctors.add(doctor.builder()
                .id(3L)
                .name("Ana")
                .birthday(birthday)
                .password("123")
                .image("https://img1.wsimg.com/isteam/stock/88197")
                .email("nia@gmail.com")
                .phoneNumber("+157897")
                .role(role.Doctor)
                .field("cardiology")
                .build());
    }


    @Override
    public doctor createDoctor(doctor doctor) {
        doctor.setId(doctorId);
        doctorId++;

        doctors.add(doctor);
        return doctor;
    }

    @Override
    public doctor updateDoctor(long id, doctor doctor) {
        Optional<com.example.pawsicare.domain.doctor> doc = this.doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            int index = doctors.indexOf(doc.get());

            com.example.pawsicare.domain.doctor doctorDTO = doc.get();
            doctorDTO.setName(doctor.getName());
            doctorDTO.setField(doctor.getField());
            doctorDTO.setAge(doctor.getAge());
            doctorDTO.setDescription(doctor.getDescription());
            doctorDTO.setEmail(doctor.getEmail());
            doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
            doctorDTO.setPassword(doctor.getPassword());


            doctors.set(index, doctorDTO);

            return doctorDTO;
        }
        return null;
    }

    @Override
    public doctor getDoctor(long id) {
        Optional<doctor> doc = this.doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            return doc.get();
        }
        return null;
    }

    @Override
    public List<doctor> getDoctors() {
        return this.doctors;
    }

    @Override
    public void deleteDoctor(long id) {
        doctors.removeIf(d -> d.getId() == id);
    }
}
