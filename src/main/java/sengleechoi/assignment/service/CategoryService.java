package sengleechoi.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sengleechoi.assignment.domain.Category;
import sengleechoi.assignment.domain.CategoryRepository;
import sengleechoi.assignment.dto.GeneralResponse;
import sengleechoi.assignment.dto.category.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
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
        List<Category> categories = new ArrayList<>(getCategoriesFromDatabase().values())
                .stream()
                .filter(Predicate.not(Category::hasParentCategory))
                .collect(Collectors.toList());
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

    public GeneralResponse modifyParentCategory(Long categoryId, ParentModificationRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException()); // 추후 예외처리 필요(NOT FOUND 등 응답처리)
        Category parentCategory = categoryRepository.findById(request.getParentCategoryId())
                .orElseThrow(() -> new RuntimeException()); // 추후 예외처리 필요(NOT FOUND 등 응답처리)

        category.mapParentCategory(parentCategory);
        categoryRepository.save(category);

        return new GeneralResponse(200, "상위 카테고리가 추가되었습니다.");
    }
}
