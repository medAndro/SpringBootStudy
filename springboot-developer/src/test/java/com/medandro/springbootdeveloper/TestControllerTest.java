package com.medandro.springbootdeveloper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }
    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));

        final ResultActions result = mockMvc.perform(get(url) //요청을 전송하는 역할을하는 perform(), 결과로 ResultActions
                                                            //객체를 받으며 반환값 검증을 위한 andExpect()메서드 제공
                .accept(MediaType.APPLICATION_JSON)); //무슨 타입으로 응답을 받을지 결정하는 accept(), json받기로 명시함

        result.andExpect(status().isOk()) // 응답으로 OK(200)을 반환하는지 검증
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
                //jsonPath("$[0].필드명")은 json 응답값의 값을 가져오는 역할로, 0번째 배열의 id, name값을 가져와 저장된 값과 비교
    }
}