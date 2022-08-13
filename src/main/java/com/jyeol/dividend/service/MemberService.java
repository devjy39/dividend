package com.jyeol.dividend.service;

import com.jyeol.dividend.exception.impl.UserException;
import com.jyeol.dividend.exception.type.UserError;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(()-> new UserException(UserError.NOT_EXIST_USER));
    }

    public MemberDto register(Auth.SignUp member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new UserException(UserError.ALREADY_EXIST_USER);
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return MemberDto.fromEntity(memberRepository.save(member.toEntity()));
    }

    @Transactional(readOnly = true)
    public MemberDto authenticate(Auth.SignIn member) {
        MemberEntity memberEntity = memberRepository.findByUsername(member.getUsername())
                .orElseThrow(()->new UserException(UserError.NOT_EXIST_USER));

        if (!passwordEncoder.matches(member.getPassword(), memberEntity.getPassword())) {
            throw new UserException(UserError.PASSWORD_MIS_MATCH);
        }

        return MemberDto.fromEntity(memberEntity);
    }
}
