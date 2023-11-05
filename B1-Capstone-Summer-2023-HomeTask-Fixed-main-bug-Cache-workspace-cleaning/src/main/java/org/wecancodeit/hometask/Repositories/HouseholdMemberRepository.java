package org.wecancodeit.hometask.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.hometask.Models.HouseholdMember;
import java.util.List;


public interface HouseholdMemberRepository extends CrudRepository<HouseholdMember, Long> {
    HouseholdMember findById(long id);
    HouseholdMember findByName(String name);
    
}
