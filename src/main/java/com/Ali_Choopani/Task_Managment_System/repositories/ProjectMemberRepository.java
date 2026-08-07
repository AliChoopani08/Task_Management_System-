package com.Ali_Choopani.Task_Managment_System.repositories;

import com.Ali_Choopani.Task_Managment_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Managment_System.entities.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    @Query("""
            SELECT pm
            FROM ProjectMember pm
            WHERE pm.project.id = :projectId AND pm.member.id = :memberId
            """)
    Optional<ProjectMember> findByProjectIdAndMemberId(@Param("projectId") Long projectId, @Param("memberId") Long memberId);

    @Query("""
            SELECT pm 
            FROM ProjectMember pm
            WHERE pm.member.id = :memberId AND pm.role = :memberRole AND pm.project.title = :projectTitle
            """)
    Optional<ProjectMember> existsByMemberIdAndRoleAndProjectTitle(@Param("memberId") Long memberId, @Param("memberRole")ProjectRole role,
                                                                   @Param("projectTitle") String projectTitle);
}
