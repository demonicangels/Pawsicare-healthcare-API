package com.example.PawsiCare.business.responses;

import com.example.PawsiCare.business.domain.Pet;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class GetAllPetsResponse {
    List<Pet> pets;
}
