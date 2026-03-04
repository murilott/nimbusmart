package com.example.delivery.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.commands.DeliverShipmentCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.application.services.CreateDeliveryTrackingHandler;
import com.example.delivery.application.services.ListDeliveryTrackingHandler;
import com.example.delivery.application.services.NextDeliverHandler;
import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingRequestDto;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingResponseDto;

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
public class DeliveryTrackingController {
    private CreateDeliveryTrackingHandler createDeliveryTrackingHandler;
    private ListDeliveryTrackingHandler listSDeliveryTrackingHandler;
    private NextDeliverHandler nextDeliverHandler;

    @GetMapping()
    public ResponseEntity<List<DeliveryTrackingResponseDto>> listAll() {
        List<DeliveryTrackingResponseDto> items = listSDeliveryTrackingHandler.handle();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<DeliveryTrackingResponseDto> create() {
        DeliveryTrackingResponseDto created = createDeliveryTrackingHandler.handle();

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/next-deliver")
    public ResponseEntity<?> nextDeliver(@RequestBody DeliveryTrackingRequestDto dto) {
        DeliverShipmentCommand cmd = new DeliverShipmentCommand(dto.id());

        nextDeliverHandler.handle(cmd);
    }
    
    @GetMapping("/next-delivered")
    public ResponseEntity<Shipment> nextDelivered(@RequestBody DeliveryTrackingRequestDto dto) {
        DeliverShipmentCommand cmd = new DeliverShipmentCommand(dto.id());
        
        // delivered handler
    }
    
    

}
