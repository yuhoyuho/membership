package com.example.membership.Repository;

import com.example.membership.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // select * from member_table where memberEmail=?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
