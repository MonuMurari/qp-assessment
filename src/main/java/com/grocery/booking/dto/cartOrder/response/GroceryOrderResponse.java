package com.grocery.booking.dto.cartOrder.response;

import com.grocery.booking.dto.cartOrder.request.GroceryOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryOrderResponse
{
    private String id;
    private List<GroceryOrder> groceryOrders;
}
