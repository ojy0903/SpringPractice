package custom.practice.service;

import custom.practice.domain.*;
import custom.practice.exception.NotEnoughStockException;
import custom.practice.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @PersistenceContext EntityManager em;

    @Test
    public void 상품주문() {

        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder;
        Optional<Order> byId = orderRepository.findById(orderId);
        if (byId.isPresent()) {
            getOrder = byId.get();
        } else {
            throw new IllegalStateException("해당 Id의 주문 존재하지 않음");
        }

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() {
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        try {
            orderService.order(member.getId(), item.getId(), orderCount);
        } catch (NotEnoughStockException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }

    @Test
    public void 주문취소() {

        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder;
        Optional<Order> byId = orderRepository.findById(orderId);
        if (byId.isPresent()) {
            getOrder = byId.get();
        } else {
            throw new IllegalStateException("해당 Id의 주문 존재하지 않음");
        }

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고 증가");
    }
    
    

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}