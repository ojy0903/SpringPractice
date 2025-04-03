package custom.practice.service;

import custom.practice.domain.*;
import custom.practice.repository.ItemRepository;
import custom.practice.repository.MemberRepository;
import custom.practice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Optional<Member> memberById = memberRepository.findById(memberId);
        Member member;
        if (memberById.isPresent()) {
            member = memberById.get();
        } else {
            throw new IllegalStateException("해당 Id의 회원이 존재하지 않습니다");
        }

        Optional<Item> itemById = itemRepository.findById(itemId);
        Item item;
        if (itemById.isPresent()) {
            item = itemById.get();
        } else {
            throw new IllegalStateException("해당 Id의 상품이 존재하지 않습니다");
        }

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Optional<Order> orderById = orderRepository.findById(orderId);
        Order order;
        if (orderById.isPresent()) {
            order = orderById.get();
        } else {
            throw new IllegalStateException("해당 Id의 상품이 존재하지 않습니다");
        }

        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {

        return orderRepository.findAll(orderSearch);
    }
}
