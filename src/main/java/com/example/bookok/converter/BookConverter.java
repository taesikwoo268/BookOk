package com.example.bookok.converter;

import com.example.bookok.dto.BookDTO;
import com.example.bookok.model.BookEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {
    @Autowired
    private ModelMapper modelMapper;

    // Chuyển từ Entity sang DTO
    public BookDTO toDTO(BookEntity entity) {
        BookDTO dto = modelMapper.map(entity, BookDTO.class);
        // Nếu có Category, hãy gán tên thủ công nếu ModelMapper không tự nhận
        if (entity.getCategory() != null) {
            dto.setCategoryName(entity.getCategory().getName());
        }
        return dto;
    }

    // Chuyển từ DTO sang Entity
    public BookEntity toEntity(BookDTO dto) {
        return modelMapper.map(dto, BookEntity.class);
    }
}
