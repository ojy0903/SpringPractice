package custom.practice.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder //상속관계에서 자식이 부모 타입의 변수에 접근하기 위해 @SuperBuilder 적용
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book extends Item{

    private String author;
    private String isbn;

}
