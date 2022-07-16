package sengleechoi.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sengleechoi.assignment.domain.Category;
import sengleechoi.assignment.domain.CategoryRepository;
import sengleechoi.assignment.dto.GeneralResponse;
import sengleechoi.assignment.dto.category.CategoryCreationRequest;
import sengleechoi.assignment.dto.category.CategoryCreationResponse;
import sengleechoi.assignment.dto.category.CategoryListResponse;
import sengleechoi.assignment.dto.category.CategoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse getCategoriesById(Long categoryId) {
        Map<Long, Category> categories = getCategoriesFromDatabase();
        return CategoryResponse.from(categories.get(categoryId));
    }

    public CategoryListResponse getAllCategories() {
        List<Category> categories = new ArrayList<>(getCategoriesFromDatabase().values());
        return CategoryListResponse.from(categories);
    }

    private Map<Long, Category> getCategoriesFromDatabase() {
        return categoryRepository.findAllCategories()
                .stream()
                .peek(category -> category.mapParentCategory(category.getParentCategory()))
                .collect(Collectors.toMap(Category::getId, Function.identity()));
    }

    public CategoryCreationResponse createCategory(CategoryCreationRequest request) {
        Category category = new Category(request.getName());
        Category savedCategory = categoryRepository.save(category);
        return CategoryCreationResponse.from(savedCategory);
    }
}
