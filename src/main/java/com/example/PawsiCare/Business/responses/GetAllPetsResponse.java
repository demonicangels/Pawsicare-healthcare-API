package com.example.PawsiCare.Business.responses;

import com.example.PawsiCare.Domain.Pet;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllPetsResponse {
    List<Pet> pets;
}
