package com.example.pawsicare.persistence;

import com.example.pawsicare.domain.Doctor;
import com.example.pawsicare.domain.Role;
import org.springframework.stereotype.Repository;
import com.example.pawsicare.persistence.fakeRepositoryInterfaces.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeDoctorRepositoryImpl implements DoctorRepository {

    private final List<Doctor> Doctors;
    private Long doctorId = 3L;

    static String birthday = "1-1-1999";

    public FakeDoctorRepositoryImpl(){
        this.Doctors = new ArrayList<>();

        Doctors.add(Doctor.builder()
                .id(1L)
                .name("Maia")
                .birthday(birthday)
                .password("123")
                .image("https://media.istockphoto.com/id/916855812/photo/smiling-veterinarian-with-dog-and-digital-tablet.jpg?s=612x612&w=0&k=20&c=5i3S8AaEEqDuNvos2sFabaep7BeLwcHGwW_thNpyszE=")
                .email("maia@gmail.com")
                .phoneNumber("+1234567")
                .role(Role.Doctor)
                .field("neurology")
                .build());



        Doctors.add(Doctor.builder()
                .id(2L)
                .name("Nia")
                .birthday(birthday)
                .password("123")
                .image("https://media.istockphoto.com/id/1386206447/photo/brown-border-collie-dog-during-visit-in-vet.jpg?s=612x612&w=0&k=20&c=xVRfoZh1CLbKRoGYOIucSSO6nobJSQVmUw9a5Cpo3UA=")
                .email("nia@gmail.com")
                .phoneNumber("+157897")
                .role(Role.Doctor)
                .field("neurology")
                .build());

        Doctors.add(Doctor.builder()
                .id(3L)
                .name("Ana")
                .birthday(birthday)
                .password("123")
                .image("https://img1.wsimg.com/isteam/stock/88197")
                .email("nia@gmail.com")
                .phoneNumber("+157897")
                .role(Role.Doctor)
                .field("cardiology")
                .build());
    }


    @Override
    public Doctor createDoctor(Doctor doctor) {
        doctor.setId(doctorId);
        doctorId++;

        Doctors.add(doctor);
        return doctor;
    }

    @Override
    public Doctor updateDoctor(long id, Doctor doctor) {
        Optional<Doctor> doc = this.Doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            int index = Doctors.indexOf(doc.get());

            Doctor doctorDTO = doc.get();
            doctorDTO.setName(doctor.getName());
            doctorDTO.setField(doctor.getField());
            doctorDTO.setAge(doctor.getAge());
            doctorDTO.setDescription(doctor.getDescription());
            doctorDTO.setEmail(doctor.getEmail());
            doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
            doctorDTO.setPassword(doctor.getPassword());


            Doctors.set(index, doctorDTO);

            return doctorDTO;
        }
        return null;
    }

    @Override
    public Doctor getDoctor(long id) {
        Optional<Doctor> doc = this.Doctors.stream().filter(d -> d.getId() == id).findFirst();
        if(doc.isPresent()){
            return doc.get();
        }
        return null;
    }

    @Override
    public List<Doctor> getDoctors() {
        return this.Doctors;
    }

    @Override
    public void deleteDoctor(long id) {
        Doctors.removeIf(d -> d.getId() == id);
    }
}
