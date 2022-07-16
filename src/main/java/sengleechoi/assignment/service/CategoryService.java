package sengleechoi.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sengleechoi.assignment.domain.Category;
import sengleechoi.assignment.domain.CategoryRepository;
import sengleechoi.assignment.dto.category.CategoryElement;
import sengleechoi.assignment.dto.category.CategoryListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryListResponse getCategoriesByName(String categoryName) {
        List<Category> categories = categoryRepository.findCategoriesByName(categoryName);
        List<CategoryElement> categoryElements = categories.stream()
                .map(CategoryElement::from)
                .collect(Collectors.toList());

        return CategoryListResponse.from(categoryElements);
    }
}
