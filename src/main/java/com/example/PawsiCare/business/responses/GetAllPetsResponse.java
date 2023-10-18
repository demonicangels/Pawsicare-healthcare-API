package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.domain.Pet;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllPetsResponse {
    List<Pet> pets;
}
