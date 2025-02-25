package com.example.membership.Service;

import com.example.membership.DTO.MemberDTO;
import com.example.membership.Entity.MemberEntity;
import com.example.membership.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // 1. DTO -> Entity
        // 2. Repository.save() 호출

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); // repository 객체의 save 메소드는 반드시 entity 객체를 넘겨줘야함.
    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 입력한 이메일로 DB에서 회원정보 조회
        // 2. 조회한 정보와 입력한 비밀번호가 일치하는지 판단

        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()) {
            // 조회결과가 있다면 = 해당 이메일을 가진 회원이 있다면
            MemberEntity memberEntity = byMemberEmail.get();

            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호가 일치하는 경우
                // Entity -> DTO 후 반환
                return MemberDTO.toMemberDTO(memberEntity);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for(MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }

        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // optional 객체는 get()을 해줘야 entity 객체가 됨
        }
        else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }
        else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }
}
