package trend.project.web.controller.planController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trend.project.service.planService.PlanByCategoryService;

@RestController
@RequestMapping("/plans/themes")
@RequiredArgsConstructor
@Tag(name = "카테고리 별 게시글 조회 API")
public class PlanByCategoryController {

    private final PlanByCategoryService planByCategoryService;


    // 메인 배너 조회 API
    @Operation(summary = "메인 배너 조회 API")
    @GetMapping("/banner/{categoryName}")
    public void getBanner(@PathVariable String categoryName){

    }



    // 최고 좋아요 조회 API
    @Operation(summary = "최고 좋아요 조회 API")
    @GetMapping("/Ranking/{categoryName}")
    public void getRanking(@PathVariable String categoryName){

    }



    // 전체 게시글 조회 API - 최신별로 조회
    @Operation(summary = "전체 게시글 조회 API - 최신별로 조회")
    @GetMapping("/banner/{categoryName}")
    public void getPlansByUpdateDate(@PathVariable String categoryName){

    }



    // 전체 게시글 조회 API - 좋아요 갯수 순으로 조회
    @Operation(summary = "전체 게시글 조회 API - 좋아요 갯수 순으로 조회")
    @GetMapping("/banner/{categoryName}")
    public void getPlansByLikeCount(@PathVariable String categoryName){

    }



    // 전체 게시글 조회 API - 지역별로 조회
    @Operation(summary = "전체 게시글 조회 API - 지역별로 조회")
    @GetMapping("/banner/{categoryName}")
    public void getPlansByRegion(@PathVariable String categoryName,
                                 @RequestParam String region){

    }

}
