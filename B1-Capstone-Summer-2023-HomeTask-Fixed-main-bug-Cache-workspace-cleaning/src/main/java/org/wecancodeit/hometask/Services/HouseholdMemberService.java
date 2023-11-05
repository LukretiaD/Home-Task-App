package org.wecancodeit.hometask.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wecancodeit.hometask.Models.HouseholdMember;
import org.wecancodeit.hometask.Repositories.HouseholdMemberRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class HouseholdMemberService {
    
    @Autowired
    private final HouseholdMemberRepository memberRepository;

    public HouseholdMemberService(HouseholdMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public HouseholdMember save(HouseholdMember member) {
        return memberRepository.save(member);
    }

    public Iterable<HouseholdMember> retrieveAllMembers() {
        return memberRepository.findAll();
    }

    
    public HouseholdMember retrieveMemberById(Long id) throws Exception {
        try {
            return memberRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception("Member not found.");
        }
    }

    
    public HouseholdMember getMemberByName(String name) throws Exception{
        try{
            HouseholdMember member = memberRepository.findByName(name);
            return member;
        } catch (Exception e){
            throw new Exception ("Member not Found");
        }
    }

     public long getMemberId(HttpServletRequest request){
    long retValue=0;
    Cookie[] cookies = request.getCookies();
    for(int x =0;x<cookies.length;x++){
        if(cookies[x].getName().equals("member-id")){
            retValue = Long.parseLong(cookies[x].getValue());
            break;
        }
    }
        return retValue;
    }

       
    }

    

    



