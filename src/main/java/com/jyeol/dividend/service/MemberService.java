package com.jyeol.dividend.service;

import com.jyeol.dividend.model.Auth;
import com.jyeol.dividend.model.MemberDto;
import com.jyeol.dividend.persist.MemberRepository;
import com.jyeol.dividend.persist.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }

    public MemberDto register(Auth.SignUp member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return MemberDto.fromEntity(memberRepository.save(member.toEntity()));
    }

    public MemberDto authenticate(Auth.SignIn member) {
        MemberEntity memberEntity = memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

        if (!passwordEncoder.matches(member.getPassword(), memberEntity.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return MemberDto.fromEntity(memberEntity);
    }
}
