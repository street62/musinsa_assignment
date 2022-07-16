package sengleechoi.assignment.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sengleechoi.assignment.domain.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CategoryListResponse {
    private List<CategoryElement> categories;

    public static CategoryListResponse from(List<Category> categories) {
        List<CategoryElement> categoryElements = categories.stream()
                .map(CategoryElement::from)
                .collect(Collectors.toList());
        return new CategoryListResponse(categoryElements);
    }
}
