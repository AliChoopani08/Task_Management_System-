package com.Ali_Choopani.Task_Management_System.repositories;

import com.Ali_Choopani.Task_Management_System.dto.project.MemberSummary;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.ProjectRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    @EntityGraph(attributePaths = {"user","project"})
    Optional<ProjectMember> findByProjectIdAndMemberIdAndRole(Long projectId, Long memberId, ProjectRole role);

    Optional<ProjectMember> findByProjectIdAndMemberId(Long projectId, Long memberId);

    Set<ProjectMember> findByMemberId(Long memberId);

    boolean existsByProjectIdAndMemberId(Long projectId, Long memberId);

    @Query("""
            SELECT pm 
            FROM ProjectMember pm
            WHERE pm.member.id = :memberId AND pm.role = :memberRole AND pm.project.title = :projectTitle
            """)
    Optional<ProjectMember> existsByMemberIdAndRoleAndProjectTitle(@Param("memberId") Long memberId, @Param("memberRole")ProjectRole role,
                                                                   @Param("projectTitle") String projectTitle);

    @Query("""
            SELECT new com.Ali_Choopani.Task_Management_System.dto.project
            .MemberSummary(m.id, concat(pr.firstName, ' ', pr.surname), pm.role)
            FROM ProjectMember pm
            JOIN pm.project p
            JOIN pm.member m
            JOIN m.profile pr
            WHERE p.id = :projectId AND pm.role != ROLE_MANAGER
            """)
    Set<MemberSummary> findMembersOfProjectByProjectId(@Param("projectId") Long projectId);

    Optional<Set<ProjectMember>> findByMemberIdAndRole(Long memberId, ProjectRole role);
}
