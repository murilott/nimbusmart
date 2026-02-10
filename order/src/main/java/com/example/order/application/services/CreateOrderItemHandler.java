package com.example.order.application.services;

import org.springframework.stereotype.Service;

import com.example.order.application.commands.CreateOrderItemCommand;
import com.example.order.application.ports.out.OrderItemRepository;
import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;
import com.example.order.domain.model.order.OrderItem;
import com.example.order.domain.vo.ItemSnapshot;
import com.example.order.infrastructure.messaging.out.InventoryGrpcGateway;
import com.example.order.interfaces.rest.dto.OrderItemResponseDto;
import com.example.order.interfaces.rest.mapper.OrderItemMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CreateOrderItemHandler {
    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper mapper;

    private final InventoryGrpcGateway inventoryGrpcGateway;

    public OrderItemResponseDto handle(CreateOrderItemCommand cmd) {
        Order order = orderRepository
            .findById(cmd.orderId())
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        ItemSnapshot itemSnapshot = inventoryGrpcGateway.checkAvailability(cmd.inventoryItemId(), cmd.quantity());

        if (itemSnapshot == null) {
            throw new EntityNotFoundException("InventoryItem does not exist");
        }

        OrderItem item = OrderItem.newOrderItem(order, itemSnapshot, cmd.quantity());

        order.addToOrder(item);

        orderRepository.save(order);

        boolean reserve = inventoryGrpcGateway.reserveItem(cmd.inventoryItemId(), cmd.quantity());

        log.info("Reserve Item status: {} | OrderItem id: {}", reserve, item.getId());

        return mapper.toDto(item);
    }
}
