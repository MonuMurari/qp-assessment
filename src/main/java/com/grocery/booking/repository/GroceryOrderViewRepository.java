package com.grocery.booking.repository;

import com.grocery.booking.dto.cartOrder.projection.GroceryOrderView;
import com.grocery.booking.dto.grocery.projection.GroceryItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryOrderViewRepository extends JpaRepository<GroceryOrderView,String>
{
}
