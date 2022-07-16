package sengleechoi.assignment.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sengleechoi.assignment.domain.Category;

@Getter
@AllArgsConstructor
public class CategoryCreationResponse {
    private Long categoryId;

    public static CategoryCreationResponse from(Category category) {
        return new CategoryCreationResponse(category.getId());
    }
}

