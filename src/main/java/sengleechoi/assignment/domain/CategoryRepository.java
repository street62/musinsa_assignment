package sengleechoi.assignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c1 from Category c1 left join fetch c1.parentCategory")
    public List<Category> findAllCategories();
}
