package com.grocery.booking.dto.cartOrder.projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroceryOrderView
{
    @Id
    private String id;
    private String groceryOrders;
}
