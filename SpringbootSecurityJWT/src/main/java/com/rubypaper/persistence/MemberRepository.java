package com.rubypaper.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubypaper.domain.Member;

// DB 데이터 저장소
public interface MemberRepository extends JpaRepository<Member, String> {

}
