package com.example.delivery.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.commands.CreateShipmentCommand;
import com.example.delivery.application.ports.out.ShipmentRepository;
import com.example.delivery.application.services.CreateShipmentHandler;
import com.example.delivery.application.services.ListDeliveryTrackingHandler;
import com.example.delivery.interfaces.rest.dto.ShipmentCreationDto;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/delivery-tracking")
@AllArgsConstructor
public class ShipmentController {
    private CreateShipmentHandler createShipmentHandler;
    private ListDeliveryTrackingHandler listDeliveryTrackingHandler;

    @GetMapping()
    public ResponseEntity<List<ShipmentResponseDto>> listAll() {
        List<ShipmentResponseDto> items = listDeliveryTrackingHandler.shipmentsHandler();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<ShipmentResponseDto> create(@RequestBody ShipmentCreationDto dto) {
        CreateShipmentCommand cmd = new CreateShipmentCommand(
            dto.orderId(), 
            dto.deliveryTrackingId(), 
            dto.destinyAddress()
        );

        ShipmentResponseDto created = createShipmentHandler.handle(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    

}
