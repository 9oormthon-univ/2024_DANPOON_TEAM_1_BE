package trend.project.web.controller.planController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import trend.project.api.ApiResponse;
import trend.project.service.planService.PlanBannerService;

import java.io.IOException;

@RestController
@RequestMapping("/plans/banner")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "기획서 배너 관리 API")
public class PlanBannerController {
    
    private final PlanBannerService planBannerService;
    
    @Operation(summary = "배너 등록 API", description = "Authorization 헤더에 토큰을 넣어주세요")
    @PostMapping(path = "/{planId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadBannerImage(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @PathVariable Long planId,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        
        String dirName="기획서 배너 이미지";
        String url = planBannerService.upload(multipartFile, dirName, planId, userDetails.getUsername());
        log.info("파일 업로드 완료: {}", url);
        
        return ApiResponse.onSuccess(url);
    }
    
    
    
    
    
    @Operation(summary = "배너 조회 API")
    @GetMapping(path = "/{planId}")
    public ResponseEntity<byte[]> getBannerImage(
            @PathVariable Long planId) {
        
        try {
            byte[] fileData = planBannerService.download(planId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename");
            log.info("파일 다운로드 완료: ID {}", planId);
            
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            
            log.error("파일 다운로드 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "배너 삭제 API", description = "Authorization 헤더에 토큰을 넣어주세요")
    @DeleteMapping(path = "/{planId}")
    public ApiResponse<String> deleteBannerImage(
            @PathVariable Long planId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        
        String username = userDetails.getUsername();
        
        
        try {
            
            planBannerService.deleteFile(planId, username);
            
            return ApiResponse.onSuccess("삭제에 성공하였습니다");
            
        } catch (Exception e) {
            log.error("파일 삭제 오류: {}", e.getMessage());
            return ApiResponse.onFailure("404","이미지를 찾을 수 없습니다",null);
        }
    }
    
    
    
    
    
    
    @Operation(summary = "배너 업데이트 API", description = "Authorization 헤더에 토큰을 넣어주세요")
    @PutMapping(path = "/{planId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> updateBannerImage(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @PathVariable Long planId,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        
        
        String username = userDetails.getUsername();
        String dirName = "기획서 배너 이미지";
        
        
        try {
            
            String url = planBannerService.updateBannerImage(multipartFile, dirName, planId, username);
            log.info("배너 업데이트 완료: {}", url);
            
            return ApiResponse.onSuccess(url);
            
        } catch (Exception e) {
            
            log.error("배너 이미지 업데이트 오류: {}", e.getMessage());
            
            return ApiResponse.onFailure("404","이미지를 찾을 수 없습니다",null);
        }
    }
}
