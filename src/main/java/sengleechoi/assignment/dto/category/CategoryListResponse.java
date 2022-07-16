package sengleechoi.assignment.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryListResponse {
    private List<CategoryElement> categories;

    public static CategoryListResponse from(List<CategoryElement> categories) {
        return new CategoryListResponse(categories);
    }
}
