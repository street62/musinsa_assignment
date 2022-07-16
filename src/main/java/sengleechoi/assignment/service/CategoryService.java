package sengleechoi.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sengleechoi.assignment.domain.Category;
import sengleechoi.assignment.domain.CategoryRepository;
import sengleechoi.assignment.dto.category.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long categoryId) {
        return getCategoriesFromDatabase().get(categoryId);
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(getCategoriesFromDatabase().values())
                .stream()
                .filter(Predicate.not(Category::hasParentCategory))
                .collect(Collectors.toList());
    }

    private Map<Long, Category> getCategoriesFromDatabase() {
        return categoryRepository.findAllCategories()
                .stream()
                .peek(category -> category.mapParentCategory(category.getParentCategory()))
                .collect(Collectors.toMap(Category::getId, Function.identity()));
    }

    public Category createCategory(CategoryCreationRequest request) {
        Category category = new Category(request.getName());
        return categoryRepository.save(category);
    }

    public void modifyParentCategory(Long categoryId, ParentModificationRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException()); // 추후 예외처리 필요(NOT FOUND 등 응답처리)
        Category parentCategory = categoryRepository.findById(request.getParentCategoryId())
                .orElseThrow(() -> new RuntimeException()); // 추후 예외처리 필요(NOT FOUND 등 응답처리)

        category.mapParentCategory(parentCategory);
    }

    public void modifyCategoryName(Long categoryId, CategoryNameModificationRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException()); // 추후 예외처리 필요(NOT FOUND 등 응답처리)
        category.modifyName(request.getName());

    }
}
