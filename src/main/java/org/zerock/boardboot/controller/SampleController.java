package org.zerock.boardboot.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.boardboot.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

    @GetMapping("/hello")
    public String[] hello(){

        return new String[]{"Hello", "World"};

    }

    @GetMapping("/ex1")
    public void ex1(){
        log.info("ex1메서드 실행.....");
    }

//    @GetMapping({"/ex2"})
//    public void exModel(Model model){
//
//        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i -> {
//            SampleDTO dto = SampleDTO.builder()
//                    .sno(i)
//                    .first("First.."+i)
//                    .last("Last.."+i)
//                    .regTime(LocalDateTime.now())
//                    .build();
//            return dto;
//        }).collect(Collectors.toList());
//
//        model.addAttribute("list", list);
//    }



    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes){
        // 인라인 속성 활용 (javaScript처리유용)
        // RedirectAttributes를 이용해서 /ex3으로 result(String)와 dto(객체)라는 데이터를 심어 전달
        log.info("exInline..............");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last..100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);


        return "redirect:/sample/ex3";

        //success
        //SampleDTO(sno=100, first=First..100, last=Last..100, regTime=2024-04-02T14:53:03.924160400)
    }

    @GetMapping("/ex3")
    public void ex3(){

        log.info("ex3");

    }

    @GetMapping({"/ex2", "/exLink"})
    public void exModel(Model model){
        //타입리프는 @{}를 이용해서 링크를 처리하도록 해줌
        // <a href="/sample/exView?sno=1">SampleDTO(sno=1, first=First..1, last=Last..1, regTime=2024-04-02T15:11:28.656848200)</a>
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First.."+i)
                    .last("Last.."+i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }
//
//    @GetMapping("/exLayout1")
//    public void exLayout1(){
//        // 타입리프 기능 중에서 특정한 부분을 다른 내용으로 변경할 수 있는 th:insert, th:replace 가 있다.
//        // th:insert (기존내용의 바깥쪽 태그는 그대로 유지하면서 추가
//        // th:replace (기존 내용을 완전히 대체하는 방식
//        log.info("exLayout............");
//    }

    @GetMapping({"/exLayout1","/exLayout2", "/exTemplate","/exSidebar"})
    public void exLayout11(){
        log.info("exLayout............");
    }


}
