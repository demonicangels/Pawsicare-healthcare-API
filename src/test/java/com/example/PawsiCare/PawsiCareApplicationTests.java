package com.example.PawsiCare;

import com.example.PawsiCare.business.DoctorManager;
import com.example.PawsiCare.business.PetManager;
import com.example.PawsiCare.business.domain.Doctor;
import com.example.PawsiCare.business.domain.Pet;
import com.example.PawsiCare.business.repositories.DoctorRepository;
import com.example.PawsiCare.business.repositories.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;


@SpringBootTest
class PawsiCareApplicationTests {

//	private final DoctorManager doctorManager;
//	private final PetManager petManager;
//
//	@Autowired
//	PawsiCareApplicationTests(DoctorManager doctorManager, PetManager petManager) {
//		this.doctorManager = doctorManager;
//		this.petManager = petManager;
//	}
//
//	@Test
//	void getEntityById() {
//		Doctor doc = new Doctor(1,"Maia",30,"123","hi","cardiology","maia@gmail.com","+123456");
//		Doctor doc2 = new Doctor(2,"Nia",25,"123","ola","cardiology","nia@gmail.com","+123456");
//		doctorManager.createDoctor(doc);
//		doctorManager.createDoctor(doc2);
//
//		Doctor actual = doctorManager.getDoctor(2);
//
//		Doctor expected = doc2;
//
//		Assertions.assertSame(actual,expected);
//		Assertions.assertEquals(actual.getId(),expected.getId());
//	}
//
//	@Test
//	void updateDoctor(){
//		Doctor doc2 = new Doctor(1,"Maia",35,"123","ne znam","neurology","maia@gmail.com","+1234567");
//
//		Doctor actual = doctorManager.updateDoctor(1, doc2);
//		Doctor expected = doc2;
//
//		Assertions.assertEquals(actual.getAge(), expected.getAge());
//		Assertions.assertEquals(actual.getDescription(), expected.getDescription());
//		Assertions.assertEquals(actual.getField(),expected.getField());
//		Assertions.assertEquals(actual,expected);
//	}
//
//	@Test
//	void getPetsByOwnerId(){
//		Pet pet = new Pet(1,2,"ken","10.10.2020",3,"cuute");
//		Pet pet2 = new Pet(2,1,"prince","05.05.2011",12,"my love");
//		petManager.createPet(pet);
//		petManager.createPet(pet2);
//
//		List<Pet> actual = petManager.getPets(1);
//
//		int expectedNum = 1;
//
//		Assertions.assertEquals(expectedNum,actual.size());
//	}
//
//	@Test
//	void deleteDoctor(){
//		Doctor doc2 = new Doctor(3,"Zara",35,"123","ne znam","neurology","maia@gmail.com","+1234567");
//		doctorManager.createDoctor(doc2);
//
//		doctorManager.deleteDoctor(3);
//
//		List<Doctor> actual = doctorManager.getDoctors();
//
//		int expectedNum = 2;
//
//		Assertions.assertEquals(actual.size(),expectedNum);
//	}




}
