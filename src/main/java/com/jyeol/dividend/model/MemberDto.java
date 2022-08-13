package com.jyeol.dividend.model;

import com.jyeol.dividend.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberDto {
    private String username;

    private List<String> roles;

    public static MemberDto fromEntity(MemberEntity memberEntity) {
        return MemberDto.builder()
                .username(memberEntity.getUsername())
                .roles(memberEntity.getRoles())
                .build();
    }
}
