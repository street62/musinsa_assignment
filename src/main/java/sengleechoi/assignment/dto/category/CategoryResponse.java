package sengleechoi.assignment.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sengleechoi.assignment.domain.Category;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryResponse {
    private CategoryElement category;
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(CategoryElement.from(category));
    }
}
