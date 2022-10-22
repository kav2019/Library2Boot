package ru.kovshov.Library2Boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kovshov.Library2Boot.models.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer> {
}
