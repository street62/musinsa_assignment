package sengleechoi.assignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c1 from Category c1 left join fetch c1.parentCategory where c1.name = :name")
    public List<Category> findCategoriesByName(@Param("name") String name);
}
