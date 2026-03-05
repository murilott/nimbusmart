package com.example.delivery.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.delivery.application.commands.CancelShipmentCommand;
import com.example.delivery.application.commands.DeliverShipmentCommand;
import com.example.delivery.application.commands.DeliveredShipmentCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.application.services.CancelShipmentHandler;
import com.example.delivery.application.services.CreateDeliveryTrackingHandler;
import com.example.delivery.application.services.ListDeliveryTrackingHandler;
import com.example.delivery.application.services.NextDeliverHandler;
import com.example.delivery.application.services.NextDeliveringHandler;
import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.NextDeliveredRequestDto;
import com.example.delivery.interfaces.rest.dto.ShipmentCancelDto;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingResponseDto;
import com.example.delivery.interfaces.rest.dto.NextDeliverRequestDto;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

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
    private NextDeliveringHandler nextDeliveringHandler;
    private CancelShipmentHandler cancelShipmentHandler;

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

    @PostMapping("/next-deliver")
    public ResponseEntity<?> nextDeliver(@RequestBody NextDeliverRequestDto dto) {
        DeliverShipmentCommand cmd = new DeliverShipmentCommand(dto.id(), dto.days());

        Optional<ShipmentResponseDto> shipmentOpt = nextDeliverHandler.handle(cmd);

        return shipmentOpt
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("No pending shipment to transit")
                );
    }

    @PostMapping("/next-delivered")
    public ResponseEntity<?> nextDelivered(@RequestBody NextDeliveredRequestDto dto) {
        DeliveredShipmentCommand cmd = new DeliveredShipmentCommand(dto.id());

        Optional<ShipmentResponseDto> shipmentOpt = nextDeliveringHandler.handle(cmd);

        return shipmentOpt
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("No in transit shipment to deliver")
                );
    }

    @PostMapping("/cancel-shipment")
    public ResponseEntity<?> cancelShipment(@RequestBody ShipmentCancelDto dto) {
        CancelShipmentCommand cmd = new CancelShipmentCommand(dto.deliveryTrackingId(), dto.shipmentId());

        Optional<ShipmentResponseDto> shipmentOpt = cancelShipmentHandler.handle(cmd);

        return shipmentOpt
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Could not cancel shipment with id=" + cmd.shipmentId())
                );
    }
}
