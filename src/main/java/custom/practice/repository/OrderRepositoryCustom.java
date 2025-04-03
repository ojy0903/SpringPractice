package custom.practice.repository;

import custom.practice.domain.Order;
import custom.practice.domain.OrderSearch;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findAll(OrderSearch orderSearch);
}
