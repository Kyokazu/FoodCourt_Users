package com.foodcourt.proyect.infrastructure.controller;



import com.foodcourt.proyect.infrastructure.dto.ListPlateDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;
import com.foodcourt.proyect.infrastructure.dto.PlateDTO;
import com.foodcourt.proyect.infrastructure.handler.PlateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plate")
public class PlateController {

    private final PlateHandler plateHandler;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/createPlate")
    @Qualifier("createPlate")
    public ResponseEntity<PlateDTO> createPlate(@RequestBody PlateDTO plate) {
        return new ResponseEntity<>(plateHandler.createPlate(plate), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/updatePlate")
    @Qualifier("updatePlate")
    public ResponseEntity<PlateDTO> updateFields(@RequestBody PlateDTO plate) {
        return new ResponseEntity<>(plateHandler.updatePriceOrDescription(plate), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/enableUnablePlate")
    @Qualifier("enableUnablePlate")
    public ResponseEntity<PlateDTO> enableUnablePlate(@RequestBody PlateDTO plate) {
        return new ResponseEntity<PlateDTO>(plateHandler.enableUnablePlate(plate), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/listPlate")
    @Qualifier("/listPlate")
    public ResponseEntity<List<ListPlateDTO>> listPlate(@RequestBody PageDTO pageDTO) {
        return new ResponseEntity<List<ListPlateDTO>>(plateHandler.listPlate(pageDTO), HttpStatus.ACCEPTED);
    }

}
