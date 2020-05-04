package com.example.demo.Model;

import com.example.demo.Entity.BookEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor(staticName = "of")
public class BookResponceDto {
    private final List<BookEntity> bookEntities;
    private final String message;
}
