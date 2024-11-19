package trend.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trend.project.domain.Plan;
import trend.project.domain.Ranking;
import trend.project.repository.RankingRepository;
import trend.project.repository.planRepository.PlanRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingService {

    private final PlanRepository planRepository;
    private final RankingRepository rankingRepository;


    public void calculateAndSaveMonthlyRanking() {

        // 한 달간 좋아요 수가 많은 5개의 Plan 가져오기
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<Plan> topPlans = planRepository.findTop5ByStartDateAfterOrderByLikesCountDesc(oneMonthAgo);

        // 기존 랭킹 데이터 삭제
        rankingRepository.deleteAll();

        // 새로운 랭킹 데이터 저장
        for (Plan plan : topPlans) {
            Ranking ranking = Ranking.builder()
                    .planId(plan.getId())
                    .title(plan.getTitle())
                    .name(plan.getMember().getName())
                    .likesCount(plan.getLikesCount())
                    .commentsCount(plan.getCommentCount())
                    .build();

            rankingRepository.save(ranking);
        }
    }
}