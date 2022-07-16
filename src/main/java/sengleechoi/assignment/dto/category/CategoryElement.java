package sengleechoi.assignment.dto.category;

import lombok.Builder;
import lombok.Getter;
import sengleechoi.assignment.domain.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CategoryElement {
    private Long categoryId;
    private String categoryName;
    private List<CategoryElement> subCategories;

    public static CategoryElement from(Category category) {
        List<CategoryElement> subCategories = category.getSubCategories().stream()
                .map(CategoryElement::from)
                .collect(Collectors.toList());

        return CategoryElement.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .subCategories(subCategories)
                .build();
    }
}
